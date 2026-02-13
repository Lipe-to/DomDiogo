package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.ObservacaoEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ObservacaoRepository {

    public List<ObservacaoEntity> read() {
        List<ObservacaoEntity> listaObservacoes = new ArrayList<>();
        String query = "select * from observacao";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                ObservacaoEntity observacaoEntity = new ObservacaoEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao")
                );
                listaObservacoes.add(observacaoEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return listaObservacoes;
    }

    public Status create(ObservacaoEntity observacaoEntity) {
        String query = "insert into observacao (matricula_aluno, id_professor, observacao) values (?, ?, ?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, observacaoEntity.getMatriculaAluno());
            preparedStatement.setInt(2, observacaoEntity.getIdProfessor());
            preparedStatement.setString(3, observacaoEntity.getObservacao());

            int rows = preparedStatement.executeUpdate();
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

    public Status update(ObservacaoEntity observacaoEntity) {
        String query = "update observacao set observacao = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, observacaoEntity.getObservacao());
            preparedStatement.setInt(2, observacaoEntity.getId());

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
        String query = "delete from observacao where id = ?";
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

    public ObservacaoEntity findById(int id) {
        String query = "select * from observacao where id = ?";
        ObservacaoEntity observacaoEntity = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                observacaoEntity = new ObservacaoEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return observacaoEntity;
    }

    public List<ObservacaoEntity> findByMatriculaAluno(int matriculaAluno) {
        String query = "select * from observacao where matricula_aluno = ?";
        List<ObservacaoEntity> listaObservacoes = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, matriculaAluno);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ObservacaoEntity observacaoEntity = new ObservacaoEntity(
                        resultSet.getInt("id"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao")
                );
                listaObservacoes.add(observacaoEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return listaObservacoes;
    }
}