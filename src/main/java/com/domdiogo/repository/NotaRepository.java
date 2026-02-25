package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.NotaEntity;
import com.domdiogo.model.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {

    public List<NotaEntity> findByMatricula(int matricula) {
        List<NotaEntity> listaNotas = new ArrayList<>();
        String query = "select * from nota where matricula_aluno = ?";
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
                        resultSet.getInt("matricula_aluno")
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
        String query = "SELECT * FROM nota";
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
                        rs.getInt("matricula_aluno")
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

    public List<NotaEntity> findByProfessor(int idProfessor) {
        List<NotaEntity> listaNotas = new ArrayList<>();
        String query = "select n.* from nota n inner join disciplina d on n.id_disciplina = d.id where d.id_professor = ?";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, idProfessor);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                NotaEntity nota = new NotaEntity(
                        rs.getInt("id"),
                        rs.getDouble("n1"),
                        rs.getDouble("n2"),
                        rs.getDouble("media"),
                        rs.getInt("id_disciplina"),
                        rs.getInt("matricula_aluno")
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

    public Status update(NotaEntity nota) {
        String query = "UPDATE nota SET n1 = ?, n2 = ? WHERE id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setDouble(1, nota.getN1());
            ps.setDouble(2, nota.getN2());
            ps.setInt(3, nota.getId());

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

    public Status zerarNota(int id) {
        String query = "UPDATE nota SET n1 = 0, n2 = 0 WHERE id = ?";
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