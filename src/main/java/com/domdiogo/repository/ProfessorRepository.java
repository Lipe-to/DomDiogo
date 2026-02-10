package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.ProfessorEntity;
import com.domdiogo.model.Status;

import java.sql.*;

public class ProfessorRepository {

    public Status delete(int id) {
        String query = "delete from professor where id = ?";
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

    public ProfessorEntity findById(int id) {
        String query = "select * from professor where id = ?";
        ProfessorEntity professorEntity = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                professorEntity = new ProfessorEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("email"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return professorEntity;
    }

    public Status update(ProfessorEntity professorEntity) {
        String query = "update professor set nome = ?, email = ?, senha = ?, palavra = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, professorEntity.getNome());
            preparedStatement.setString(2, professorEntity.getEmail());
            preparedStatement.setString(3, professorEntity.getSenha());
            preparedStatement.setString(4, professorEntity.getPalavra());
            preparedStatement.setInt(5, professorEntity.getId());

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
        String query = "select * from professor where email = ? and senha = ?";
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
        String query = "select palavra from aluno where email = ?";
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