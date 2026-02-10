package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository {

    public List<AlunoEntity> read() {
        List<AlunoEntity> listaAlunos = new ArrayList<>();
        String query = "select * from aluno";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                AlunoEntity alunoEntity = new AlunoEntity(
                        resultSet.getInt("matricula"),
                        resultSet.getString("nome"),
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra"),
                        resultSet.getString("turma")
                );
                listaAlunos.add(alunoEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return listaAlunos;
    }

    public Status delete(int matricula) {
        String query = "delete from aluno where matricula = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, matricula);
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

    public Status create(AlunoEntity alunoEntity) {
        String query = "insert into aluno (nome, usuario, senha, palavra) values (?, ?, ?, ?, ?)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, alunoEntity.getNome());
            preparedStatement.setString(2, alunoEntity.getUsuario());
            preparedStatement.setString(3, alunoEntity.getSenha());
            preparedStatement.setString(4, alunoEntity.getPalavra());

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

    public AlunoEntity findByMatricula(int matricula) {
        String query = "select * from aluno where matricula = ?";
        AlunoEntity alunoEntity = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, matricula);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                alunoEntity = new AlunoEntity(
                        resultSet.getInt("matricula"),
                        resultSet.getString("nome"),
                        resultSet.getString("usuario"),
                        resultSet.getString("senha"),
                        resultSet.getString("palavra"),
                        resultSet.getString("turma")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }
        return alunoEntity;
    }

    public Status update(AlunoEntity alunoEntity) {
        String query = "update aluno set nome = ?, usuario = ?, senha = ?, palavra = ?, turma = ? where matricula = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, alunoEntity.getNome());
            preparedStatement.setString(2, alunoEntity.getUsuario());
            preparedStatement.setString(3, alunoEntity.getSenha());
            preparedStatement.setString(4, alunoEntity.getPalavra());
            preparedStatement.setString(5, alunoEntity.getTurma());
            preparedStatement.setInt(6, alunoEntity.getMatricula());

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

    public boolean isApto(String email) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        String query = "select email from aptos where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public boolean toggleMatriculado(String email) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        String query = "update aptos set is_matriculado = not is_matriculado where email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public Status login(String usuario, String senha) {
        String query = "select * from aluno where usuario = ? and senha = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
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
        String query = "select palavra from professor where email = ?";
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