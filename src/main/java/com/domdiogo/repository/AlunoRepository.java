package com.domdiogo.repository;

import com.domdiogo.ConnectionFactory;
import com.domdiogo.model.AlunoEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository implements Repository {
    @Override
    public List<Object> read() {
        ArrayList<AlunoEntity> listaAlertas = new ArrayList<>();
        String query = "select from aluno where id = ?";
    }

    @Override
    public int delete(int id) {
        String query = "DELETE FROM aluno WHERE id = ?";
        int status = 0;
        try {
            Connection connection = new ConnectionFactory().conectar();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
            preparedStatement.setInt(1, id);
            status = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            status = -1;
        }
        return status;
    }
}
