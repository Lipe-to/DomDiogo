package com.domdiogo;

import com.domdiogo.model.StatusColor;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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
}