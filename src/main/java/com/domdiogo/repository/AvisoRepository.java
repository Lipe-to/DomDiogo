package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AvisoEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AvisoRepository {

    public List<AvisoEntity> findAll() {
        String query = "select * from aviso order by id_aviso desc";
        List<AvisoEntity> lista = new ArrayList<>();
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisoEntity a = mapResultSet(rs);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return lista;
    }

    public AvisoEntity findById(int id) {
        String query = "select * from aviso where id_aviso = ?";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        AvisoEntity aviso = null;
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                aviso = mapResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return aviso;
    }

    public List<AvisoEntity> findByTurma(String turma) {
        String query = "select a.* from aviso a join aviso_turma at on a.id_aviso = at.id_aviso where at.id_turma = ? order by a.id_aviso desc";
        List<AvisoEntity> lista = new ArrayList<>();
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, turma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisoEntity a = mapResultSet(rs);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return lista;
    }

    // Busca por regex mas limitada a uma turma específica (join com aviso_turma)
    public List<AvisoEntity> findByAvisoRegexAndTurma(String regex, String turma) {
        String query = "select a.* from aviso a join aviso_turma at on a.id_aviso = at.id_aviso where at.id_turma = ? and a.aviso ~* ? order by a.id_aviso desc";
        List<AvisoEntity> lista = new ArrayList<>();
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, turma);
            ps.setString(2, regex);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisoEntity a = mapResultSet(rs);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return lista;
    }

    // Busca por professor (todos os avisos criados por esse professor)
    public List<AvisoEntity> findByProfessor(int idProfessor) {
        String query = "select * from aviso where id_professor = ? order by id_aviso desc";
        List<AvisoEntity> lista = new ArrayList<>();
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idProfessor);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisoEntity a = mapResultSet(rs);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return lista;
    }

    // Busca por professor e turma (apenas avisos do professor para a turma especificada)
    public List<AvisoEntity> findByProfessorAndTurma(int idProfessor, String turma) {
        String query = "select a.* from aviso a join aviso_turma at on a.id_aviso = at.id_aviso where a.id_professor = ? and at.id_turma = ? order by a.id_aviso desc";
        List<AvisoEntity> lista = new ArrayList<>();
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, idProfessor);
            ps.setString(2, turma);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AvisoEntity a = mapResultSet(rs);
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }
        return lista;
    }

    public Status create(AvisoEntity avisoEntity, List<String> turmas) {
        String insertAviso = "insert into aviso (titulo, aviso, prazo, id_professor, cor) values (?, ?, ?, ?, ?)";
        String insertAvisoTurma = "insert into aviso_turma (id_aviso, id_turma) values (?, ?)";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        if (conn == null) return Status.INTERNAL_ERROR;
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(insertAviso, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, avisoEntity.getTitulo());
            ps.setString(2, avisoEntity.getAviso());
            if (avisoEntity.getPrazo() != null) {
                ps.setDate(3, Date.valueOf(avisoEntity.getPrazo()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setInt(4, avisoEntity.getIdProfessor());
            ps.setString(5, avisoEntity.getCor());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                conn.rollback();
                return Status.INTERNAL_ERROR;
            }
            ResultSet keys = ps.getGeneratedKeys();
            int avisoId = -1;
            if (keys.next()) {
                avisoId = keys.getInt(1);
            }
            if (turmas != null) {
                PreparedStatement psTurma = conn.prepareStatement(insertAvisoTurma);
                for (String turma : turmas) {
                    psTurma.setInt(1, avisoId);
                    psTurma.setString(2, turma);
                    try {
                        psTurma.executeUpdate();
                    } catch (SQLException ex) {
                    }
                }
            }
            conn.commit();
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return Status.INTERNAL_ERROR;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            cf.disconnect(conn);
        }
    }

    public Status update(AvisoEntity avisoEntity, List<String> turmas) {
        String updateAviso = "update aviso set titulo = ?, aviso = ?, prazo = ?, cor = ? where id_aviso = ?";
        String deleteTurmas = "delete from aviso_turma where id_aviso = ?";
        String insertAvisoTurma = "insert into aviso_turma (id_aviso, id_turma) values (?, ?)";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        if (conn == null) return Status.INTERNAL_ERROR;
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(updateAviso);
            ps.setString(1, avisoEntity.getTitulo());
            ps.setString(2, avisoEntity.getAviso());
            if (avisoEntity.getPrazo() != null) {
                ps.setDate(3, Date.valueOf(avisoEntity.getPrazo()));
            } else {
                ps.setNull(3, Types.DATE);
            }
            ps.setString(4, avisoEntity.getCor());
            ps.setInt(5, avisoEntity.getId());
            int rows = ps.executeUpdate();
            if (rows == 0) {
                conn.rollback();
                return Status.NOT_FOUND;
            }
            // replace turma links
            PreparedStatement psDel = conn.prepareStatement(deleteTurmas);
            psDel.setInt(1, avisoEntity.getId());
            psDel.executeUpdate();
            if (turmas != null) {
                PreparedStatement psTurma = conn.prepareStatement(insertAvisoTurma);
                for (String turma : turmas) {
                    psTurma.setInt(1, avisoEntity.getId());
                    psTurma.setString(2, turma);
                    try { psTurma.executeUpdate(); } catch (SQLException ignored) {}
                }
            }
            conn.commit();
            return Status.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            return Status.INTERNAL_ERROR;
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
            cf.disconnect(conn);
        }
    }

    public Status delete(int idAviso) {
        String deleteAviso = "delete from aviso where id_aviso = ?";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(deleteAviso);
            ps.setInt(1, idAviso);
            int rows = ps.executeUpdate();
            if (rows > 0) return Status.SUCCESS;
            return Status.NOT_FOUND;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            cf.disconnect(conn);
        }
    }

    public Status addAvisoToTurma(int avisoId, String turma) {
        String insertAvisoTurma = "insert into aviso_turma (id_aviso, id_turma) values (?, ?)";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(insertAvisoTurma);
            ps.setInt(1, avisoId);
            ps.setString(2, turma);
            int rows = ps.executeUpdate();
            if (rows > 0) return Status.SUCCESS;
            return Status.NOT_FOUND;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            cf.disconnect(conn);
        }
    }

    public Status removeAvisoFromTurma(int avisoId, String turma) {
        String delete = "delete from aviso_turma where id_aviso = ? and id_turma = ?";
        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();
        try {
            PreparedStatement ps = conn.prepareStatement(delete);
            ps.setInt(1, avisoId);
            ps.setString(2, turma);
            int rows = ps.executeUpdate();
            if (rows > 0) return Status.SUCCESS;
            return Status.NOT_FOUND;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            cf.disconnect(conn);
        }
    }

    private AvisoEntity mapResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id_aviso");
        String titulo = rs.getString("titulo");
        String aviso = rs.getString("aviso");
        Date prazoDate = rs.getDate("prazo");
        LocalDate prazo = prazoDate != null ? prazoDate.toLocalDate() : null;
        int idProfessor = rs.getInt("id_professor");
        String cor = rs.getString("cor");
        return new AvisoEntity(id, titulo, aviso, prazo, idProfessor, cor);
    }
}

