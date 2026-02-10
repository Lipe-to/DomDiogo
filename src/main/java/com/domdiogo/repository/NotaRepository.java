package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.NotaEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaRepository {
    public List<NotaEntity> read() {
        List<NotaEntity> listaNotas = new ArrayList<>();
        String query = "select * from nota";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                NotaEntity notaEntity = new NotaEntity(
                        resultSet.getInt("id"),
                        resultSet.getDouble("n1"),
                        resultSet.getDouble("n2"),
                        resultSet.getDouble("media"),
                        resultSet.getInt("idDisciplina"),
                        resultSet.getInt("matriculaAluno")
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

    public Status delete(int id) {
        String query = "delete from nota where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                return Status.SUCCESS;
            } else {
                return Status.NOT_FOUND;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }
}
