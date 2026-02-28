package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.NotaEntity;
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
        String selectQuery = "SELECT nome FROM apto WHERE usuario = ?";
        String insertQuery = "INSERT INTO aluno (nome, usuario, senha, palavra, turma) VALUES (?, ?, ?, ?, ?)";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            PreparedStatement selectStmt = connection.prepareStatement(selectQuery);
            selectStmt.setString(1, alunoEntity.getUsuario());
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");

                PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                insertStmt.setString(1, nome);
                insertStmt.setString(2, alunoEntity.getUsuario());
                insertStmt.setString(3, alunoEntity.getSenha());
                insertStmt.setString(4, alunoEntity.getPalavra());
                insertStmt.setString(5, alunoEntity.getTurma());

                int rows = insertStmt.executeUpdate();
                if (rows > 0) {
                    return Status.SUCCESS;
                }
                return Status.NOT_FOUND;
            } else {
                return Status.NOT_FOUND;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Status.NOT_FOUND;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public Status createNotas(int matricula) {
        String query = "insert into nota (matricula_aluno, id_disciplina) values (?, 1), (?, 2), (?, 3), (?, 4), (?, 5)";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            for (int i = 1; i <= 5; i++) {
                ps.setInt(i, matricula);
            }
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return Status.SUCCESS;
            }
            return Status.INTERNAL_ERROR;
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

    public AlunoEntity findByUsuario(String usuario) {
        String query = "select * from aluno where usuario = ?";
        AlunoEntity alunoEntity = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
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

    public boolean isApto(String usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        String query = "select usuario from aptos where usuario = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public boolean toggleMatriculado(String usuario) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        String query = "update aptos set is_matriculado = not is_matriculado where usuario = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            connectionFactory.disconnect(connection);
        }
    }

    public AlunoEntity login(String usuario, String senha) {
        String query = "select * from aluno where usuario = ? and senha = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        AlunoEntity alunoEntity = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
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

    public Status validarPalavra(String usuario, String palavra) {
        String query = "select palavra from aluno where usuario = ?";
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, usuario);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String palavraBD = resultSet.getString("palavra");
                if (palavraBD != null && palavraBD.equalsIgnoreCase(palavra)) {
                    return Status.SUCCESS;
                } else {
                    return Status.NOT_FOUND;
                }
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