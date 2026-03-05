package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AlunoNotaDTO;
import com.domdiogo.model.NotaEntity;
import com.domdiogo.model.Status;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {

    public List<NotaEntity> findByMatricula(int matricula) {
        List<NotaEntity> listaNotas = new ArrayList<>();
        String query = "select n.*, d.nome as disciplina_nome " +
                "from nota n join disciplina d on n.id_disciplina = d.id " +
                "where n.matricula_aluno = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, matricula);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                NotaEntity notaEntity = new NotaEntity(
                        resultSet.getInt("id"),
                        resultSet.getDouble("n1"),
                        resultSet.getDouble("n2"),
                        resultSet.getDouble("media"),
                        resultSet.getInt("id_disciplina"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getString("disciplina_nome")
                );
                listaNotas.add(notaEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return listaNotas;
    }

    public List<NotaEntity> readAll() {
        List<NotaEntity> listaNotas = new ArrayList<>();
        String query = "select n.*, d.nome as disciplina_nome " +
                "from nota n join disciplina d on n.id_disciplina = d.id";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NotaEntity nota = new NotaEntity(
                        rs.getInt("id"),
                        rs.getDouble("n1"),
                        rs.getDouble("n2"),
                        rs.getDouble("media"),
                        rs.getInt("id_disciplina"),
                        rs.getInt("matricula_aluno"),
                        rs.getString("disciplina_nome")
                );
                listaNotas.add(nota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }

        return listaNotas;
    }

    public List<AlunoNotaDTO> findAlunosByProfessor(int idProfessor) {
        List<AlunoNotaDTO> lista = new ArrayList<>();
        String query = """
                    SELECT
                        a.matricula,
                        a.nome AS aluno_nome,
                        a.turma,
                        n.id AS nota_id,
                        n.n1,
                        n.n2,
                        n.media,
                        d.id AS id_disciplina,
                        d.nome AS disciplina_nome
                    FROM aluno a
                    LEFT JOIN disciplina d
                        ON d.id_professor = ?
                    LEFT JOIN nota n
                        ON n.matricula_aluno = a.matricula
                        AND n.id_disciplina = d.id
                    ORDER BY a.nome
                       """;

        try (Connection connection = new ConnectionFactory().connect();
            PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProfessor);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BigDecimal bdN1 = rs.getBigDecimal("n1");
                    BigDecimal bdN2 = rs.getBigDecimal("n2");
                    BigDecimal bdMedia = rs.getBigDecimal("media");

                    Double n1 = bdN1 != null ? bdN1.doubleValue() : null;
                    Double n2 = bdN2 != null ? bdN2.doubleValue() : null;

                    Double media = bdMedia != null ? bdMedia.doubleValue() : null;
                    AlunoNotaDTO dto = new AlunoNotaDTO(
                            rs.getInt("matricula"),
                            rs.getString("aluno_nome"),
                            rs.getString("turma"),
                            rs.getObject("nota_id", Integer.class),
                            n1,
                            n2,
                            media,
                            rs.getObject("id_disciplina", Integer.class),
                            rs.getString("disciplina_nome")
                    );

                    lista.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public Status update(NotaEntity nota) {
        String query = "update nota set n1 = ?, n2 = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement ps = connection.prepareStatement(query);

            // n1
            if (nota.getN1() != null) {
                ps.setDouble(1, nota.getN1());
            } else {
                ps.setNull(1, java.sql.Types.DOUBLE);
            }

            // n2
            if (nota.getN2() != null) {
                ps.setDouble(2, nota.getN2());
            } else {
                ps.setNull(2, java.sql.Types.DOUBLE);
            }

            ps.setInt(3, nota.getId());

            int rows = ps.executeUpdate();
            return rows > 0 ? Status.SUCCESS : Status.NOT_FOUND;

        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public Status zerarNota(int id) {
        String query = "update nota set n1 = 0, n2 = 0 where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                return Status.SUCCESS;
            }
            return Status.NOT_FOUND;
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }
}