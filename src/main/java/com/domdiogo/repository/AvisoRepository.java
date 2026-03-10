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

        String query = "SELECT * FROM aviso ORDER BY id_aviso DESC";
        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public AvisoEntity findById(int id) {

        String query = "SELECT * FROM aviso WHERE id_aviso = ?";

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        AvisoEntity aviso = null;

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    aviso = mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return aviso;
    }

    public List<AvisoEntity> findByTurma(String turma) {

        String query = """
                SELECT DISTINCT a.*
                FROM aviso a
                JOIN aviso_turma at ON a.id_aviso = at.id_aviso
                WHERE at.turma = ?
                ORDER BY a.id_aviso DESC
                """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, turma);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public List<AvisoEntity> findByAvisoRegexAndTurma(String regex, String turma) {

        String query = """
                SELECT DISTINCT a.*
                FROM aviso a
                JOIN aviso_turma at ON a.id_aviso = at.id_aviso
                WHERE at.turma = ?
                AND (
                    a.titulo ~* ?
                    OR a.aviso ~* ?
                    OR a.cor ~* ?
                    OR CAST(a.prazo AS TEXT) ~* ?
                )
                ORDER BY a.id_aviso DESC
                """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, turma);
            ps.setString(2, regex);
            ps.setString(3, regex);
            ps.setString(4, regex);
            ps.setString(5, regex);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public List<AvisoEntity> findByAvisoRegex(String regex) {

        String query = """
                SELECT *
                FROM aviso
                WHERE
                    titulo ~* ?
                    OR aviso ~* ?
                    OR cor ~* ?
                    OR CAST(prazo AS TEXT) ~* ?
                ORDER BY id_aviso DESC
                """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, regex);
            ps.setString(2, regex);
            ps.setString(3, regex);
            ps.setString(4, regex);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public List<AvisoEntity> findByProfessor(int idProfessor) {

        String query = """
                SELECT *
                FROM aviso
                WHERE id_professor = ?
                ORDER BY id_aviso DESC
                """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idProfessor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public List<AvisoEntity> findByAvisoRegexAndProfessor(String regex, int idProfessor) {

        String query = """
            SELECT *
            FROM aviso
            WHERE id_professor = ?
            AND (
                titulo ~* ?
                OR aviso ~* ?
                OR cor ~* ?
                OR CAST(prazo AS TEXT) ~* ?
            )
            ORDER BY id_aviso DESC
            """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idProfessor);
            ps.setString(2, regex);
            ps.setString(3, regex);
            ps.setString(4, regex);
            ps.setString(5, regex);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public List<AvisoEntity> findByProfessorAndTurma(int idProfessor, String turma) {

        String query = """
                SELECT DISTINCT a.*
                FROM aviso a
                JOIN aviso_turma at ON a.id_aviso = at.id_aviso
                WHERE a.id_professor = ?
                AND at.turma = ?
                ORDER BY a.id_aviso DESC
                """;

        List<AvisoEntity> lista = new ArrayList<>();

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, idProfessor);
            ps.setString(2, turma);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cf.disconnect(conn);
        }

        return lista;
    }

    public Status create(AvisoEntity avisoEntity, List<String> turmas) {

        String insertAviso =
                "INSERT INTO aviso (titulo, aviso, prazo, id_professor, cor) VALUES (?, ?, ?, ?, ?)";

        String insertAvisoTurma =
                "INSERT INTO aviso_turma (id_aviso, turma) VALUES (?, ?)";

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        if (conn == null) return Status.INTERNAL_ERROR;

        try {

            conn.setAutoCommit(false);

            int avisoId;

            try (PreparedStatement ps = conn.prepareStatement(insertAviso, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, avisoEntity.getTitulo());
                ps.setString(2, avisoEntity.getAviso());

                if (avisoEntity.getPrazo() != null)
                    ps.setDate(3, Date.valueOf(avisoEntity.getPrazo()));
                else
                    ps.setNull(3, Types.DATE);

                ps.setInt(4, avisoEntity.getIdProfessor());
                ps.setString(5, avisoEntity.getCor());

                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        avisoId = keys.getInt(1);
                    } else {
                        throw new SQLException("Falha ao obter id_aviso gerado.");
                    }
                }
            }

            if (turmas != null && !turmas.isEmpty()) {

                try (PreparedStatement psTurma = conn.prepareStatement(insertAvisoTurma)) {

                    for (String turma : turmas) {
                        psTurma.setInt(1, avisoId);
                        psTurma.setString(2, turma);
                        psTurma.executeUpdate();
                    }
                }
            }

            conn.commit();
            return Status.SUCCESS;

        } catch (SQLException e) {

            try {
                conn.rollback();
            } catch (SQLException ignored) {}

            e.printStackTrace();
            return Status.INTERNAL_ERROR;

        } finally {

            try {
                conn.setAutoCommit(true);
            } catch (SQLException ignored) {}

            cf.disconnect(conn);
        }
    }

    public Status delete(int idAviso) {

        String query = "DELETE FROM aviso WHERE id_aviso = ?";

        ConnectionFactory cf = new ConnectionFactory();
        Connection conn = cf.connect();

        try (PreparedStatement ps = conn.prepareStatement(query)) {

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