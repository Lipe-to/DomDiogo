package com.domdiogo;

import com.domdiogo.model.StatusColor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServletHelper {

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String path)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    public static void configureStatus(HttpServletRequest request, String statusMessage, StatusColor statusColor) {
        request.setAttribute("statusMessage", statusMessage);
        request.setAttribute("statusColor", statusColor.getValue());
    }

    public static String formatarUltimoLogin(ResultSet rs) throws SQLException {
        Timestamp ts = rs.getTimestamp("ultimo_login");

        if (ts == null) {
            return "Sem registro de login";
        }

        LocalDateTime dataHora = ts.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm");
        return dataHora.format(formatter);
    }

}