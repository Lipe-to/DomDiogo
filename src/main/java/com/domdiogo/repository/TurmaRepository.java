package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TurmaRepository {

    public List<String> read() {
        List<String> listaTurmas = new ArrayList<>();
        String query = "SELECT * FROM turma";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                listaTurmas.add(resultSet.getString("turma"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return listaTurmas;
    }

    public boolean create(String turma) {
        String query = "INSERT INTO turma (turma) VALUES (?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, turma);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public boolean delete(String turma) {
        String query = "DELETE FROM turma WHERE turma = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, turma);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }
}