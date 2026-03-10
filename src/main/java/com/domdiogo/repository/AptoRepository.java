package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AptoEntity;
import com.domdiogo.model.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AptoRepository {

    public List<AptoEntity> read() {
        List<AptoEntity> lista = new ArrayList<>();

        String query = "select * from apto order by id";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {

                AptoEntity apto = new AptoEntity(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getBoolean("is_matriculado")
                );

                lista.add(apto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }

        return lista;
    }

    public Status create(AptoEntity apto) {

        String query = "insert into apto (nome, usuario, is_matriculado) values (?, ?, ?)";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, apto.getNome());
            ps.setString(2, apto.getUsuario());
            ps.setBoolean(3, apto.isMatriculado());

            int rows = ps.executeUpdate();

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

    public Status update(AptoEntity apto) {

        String query = "update apto set nome = ?, usuario = ?, is_matriculado = ? where id = ?";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {

            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, apto.getNome());
            ps.setString(2, apto.getUsuario());
            ps.setBoolean(3, apto.isMatriculado());
            ps.setInt(4, apto.getId());

            int rows = ps.executeUpdate();

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

    public Status delete(int id) {

        String query = "delete from apto where id = ?";

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

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

    public AptoEntity findById(int id) {

        String query = "select * from apto where id = ?";

        AptoEntity apto = null;

        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.connect();

        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                apto = new AptoEntity(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("usuario"),
                        rs.getBoolean("is_matriculado")
                );

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connectionFactory.disconnect(connection);
        }

        return apto;
    }

}