package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.ObservacaoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.ColorPalette;

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
                        resultSet.getString("titulo"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao"),
                        ColorPalette.valueOf(resultSet.getString("cor"))
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
        String query = "insert into observacao (titulo, matricula_aluno, id_professor, observacao, cor) values (?, ?, ?, ?, ?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, observacaoEntity.getTitulo());
            preparedStatement.setInt(2, observacaoEntity.getMatriculaAluno());
            preparedStatement.setInt(3, observacaoEntity.getIdProfessor());
            preparedStatement.setString(4, observacaoEntity.getObservacao());
            preparedStatement.setString(5, observacaoEntity.getCor().name());

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
        String query = "update observacao set titulo = ?, observacao = ?, cor = ? where id = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, observacaoEntity.getTitulo());
            preparedStatement.setString(2, observacaoEntity.getObservacao());
            preparedStatement.setString(3, observacaoEntity.getCor().name());
            preparedStatement.setInt(4, observacaoEntity.getId());

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
                        resultSet.getString("titulo"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao"),
                        ColorPalette.valueOf(resultSet.getString("cor"))
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
                        resultSet.getString("titulo"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao"),
                        ColorPalette.valueOf(resultSet.getString("cor"))
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

    public List<ObservacaoEntity> findByProfessor(int idProfessor) {
        String query = "select * from observacao where id_professor = ?";
        List<ObservacaoEntity> listaObservacoes = new ArrayList<>();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idProfessor);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ObservacaoEntity observacaoEntity = new ObservacaoEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("titulo"),
                        resultSet.getInt("matricula_aluno"),
                        resultSet.getInt("id_professor"),
                        resultSet.getString("observacao"),
                        ColorPalette.valueOf(resultSet.getString("cor"))
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