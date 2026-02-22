package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.ProfessorEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorRepository {

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
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra"),
                        resultSet.getBoolean("ativo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return professorEntity;
    }

    public List<ProfessorEntity> read(boolean apenasAtivos) {
        String query = apenasAtivos ? "select * from professor where ativo = true" : "select * from professor";
        List<ProfessorEntity> professores = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProfessorEntity professor = new ProfessorEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra"),
                        resultSet.getBoolean("ativo")
                );
                professores.add(professor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return professores;
    }

    public Status update(ProfessorEntity professorEntity) {
        String query = "update professor set nome = ?, usuario = ?, senha = ?, palavra = ?, ativo = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, professorEntity.getNome());
            preparedStatement.setString(2, professorEntity.getUsuario());
            preparedStatement.setString(3, professorEntity.getSenha());
            preparedStatement.setString(4, professorEntity.getPalavra());
            preparedStatement.setBoolean(5, professorEntity.getAtivo());
            preparedStatement.setInt(6, professorEntity.getId());

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

    public ProfessorEntity login(String usuario, String senha) {
        String query = "select * from professor where usuario = ? and senha = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        ProfessorEntity professorEntity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                professorEntity = new ProfessorEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra"),
                        resultSet.getBoolean("ativo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return professorEntity;
    }

    public Status validarPalavra(String usuario, String palavra) {
        String query = "select palavra from professor where usuario = ?";
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

    public Status toggleAtivo(int id) {
        String query = "update professor set ativo = not ativo where id = ?";
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