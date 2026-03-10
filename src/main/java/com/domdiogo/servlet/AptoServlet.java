package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AptoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.AptoRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet({"/apto", "/apto/*"})
public class AptoServlet extends HttpServlet {

    private final AptoRepository repository = new AptoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action");
        String redirect = "/adminHome";

        if ("read".equals(action)) {

            request.setAttribute("listaAptos", repository.read());

        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String action = request.getParameter("action");
        String redirect = "/adminHome";

        switch (action) {

            case "create":

                AptoEntity novo = new AptoEntity(
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        Boolean.parseBoolean(request.getParameter("is_matriculado"))
                );

                Status createStatus = repository.create(novo);

                if (createStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Apto criado com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao criar apto.", StatusColor.RED);
                }

                break;

            case "update":

                AptoEntity update = new AptoEntity(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        Boolean.parseBoolean(request.getParameter("is_matriculado"))
                );

                Status updateStatus = repository.update(update);

                if (updateStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Atualizado com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao atualizar.", StatusColor.RED);
                }

                break;

            case "delete":

                int id = Integer.parseInt(request.getParameter("id"));

                Status deleteStatus = repository.delete(id);

                if (deleteStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Deletado com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao deletar.", StatusColor.RED);
                }

                break;

            default:

                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}