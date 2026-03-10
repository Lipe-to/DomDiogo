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
            preparedStatement.setString(2, administradorEntity.getUsuario());
            preparedStatement.setString(3, administradorEntity.getSenha());
            preparedStatement.setString(4, administradorEntity.getPalavra_chave());
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

    public AdministradorEntity findByUsuario(String usuario) {
        String query = "select * from administrador where usuario = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        AdministradorEntity admin = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                admin = new AdministradorEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return admin;
    }

    public Status validarPalavra(String usuario, String palavra) {
        String query = "select palavra from administrador where usuario = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
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