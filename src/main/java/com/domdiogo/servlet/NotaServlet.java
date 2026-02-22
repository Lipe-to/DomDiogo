package com.domdiogo.servlet;

import java.io.IOException;
import java.util.List;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.NotaEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.NotaRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/nota", "/nota/*"})
public class NotaServlet extends HttpServlet {
    private String redirect = "";
    private final NotaRepository repository = new NotaRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("readAll".equals(action)) {
            List<NotaEntity> listaNotas = repository.readAll();
            request.setAttribute("listaNotas", listaNotas);
            redirect = "/WEB-INF/notas.jsp";
        } else {
            ServletHelper.configureStatus(request, "Ação inexistente.", StatusColor.RED);
            redirect = "/WEB-INF/home.jsp";
        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "update":
                NotaEntity notaUpdate = new NotaEntity(
                        Integer.parseInt(request.getParameter("id")),
                        Double.parseDouble(request.getParameter("n1")),
                        Double.parseDouble(request.getParameter("n2"))
                );
                Status updateStatus = repository.update(notaUpdate);
                if (updateStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Nota atualizada com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao atualizar a nota.", StatusColor.RED);
                }
                redirect = "/nota?action=readAll";
                break;

            case "zerar":
                int idZerar = Integer.parseInt(request.getParameter("id"));
                Status zerarStatus = repository.zerarNota(idZerar);
                if (zerarStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Nota zerada com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao zerar nota.", StatusColor.RED);
                }
                redirect = "/nota?action=readAll";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}