package com.domdiogo;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            Dotenv dotenv = Dotenv.load();
            String url = dotenv.get("DB_URL");
            String user = dotenv.get("DB_USER");
            String pwd  = dotenv.get("DB_PWD");
            conn = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco:");
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.out.println("Não achou o driver");
        }
        return conn;
    }
    public void disconnect(Connection conn){
        try{
            if(conn!=null && !conn.isClosed()){
                conn.close();
            }
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }
    }
}