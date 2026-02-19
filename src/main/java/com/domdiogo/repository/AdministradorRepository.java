package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AdministradorEntity;
import com.domdiogo.model.Status;

import java.sql.*;

public class AdministradorRepository {

    public Status update(AdministradorEntity administradorEntity) {
        String query = "update administrador set nome = ?, usuario = ?, senha = ?, palavra = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, administradorEntity.getNome());
            preparedStatement.setString(2, administradorEntity.getEmail());
            preparedStatement.setString(3, administradorEntity.getSenha());
            preparedStatement.setString(4, administradorEntity.getPalavra());
            preparedStatement.setInt(5, administradorEntity.getId());

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

    public Status delete(int id) {
        String query = "delete from administrador where id = ?";
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

    public Status login(String email, String senha) {
        String query = "select * from administrador where email = ? and senha = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
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

    public Status validarPalavra(String email, String palavra) {
        String query = "select palavra from administrador where email = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("palavra").equals(palavra)) {
                    return Status.SUCCESS;
                } else {
                    return Status.NOT_FOUND;
                }
            } else {
                return Status.INTERNAL_ERROR;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.INTERNAL_ERROR;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }
}