package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.ProfessorEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.ProfessorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.util.List;

@WebServlet({"/professor", "/professor/*"})
public class ProfessorServlet extends HttpServlet {
    private String redirect = "";
    private final ProfessorRepository repository = new ProfessorRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "findById":
                int id = Integer.parseInt(request.getParameter("id"));
                ProfessorEntity professor = repository.findById(id);
                if (professor != null) {
                    request.setAttribute("professor", professor);
                    ServletHelper.configureStatus(request, "Professor encontrado com sucesso", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Professor não encontrado", StatusColor.RED);
                }
                redirect = "";
                break;

            case "readAll":
                List<ProfessorEntity> todosProfessores = repository.read();
                if (todosProfessores.isEmpty()) {
                    ServletHelper.configureStatus(request, "Nenhum professor encontrado", StatusColor.RED);
                } else {
                    request.setAttribute("professores", todosProfessores);
                    ServletHelper.configureStatus(request, "Todos os professores carregados com sucesso", StatusColor.GREEN);
                }
                redirect = "";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inexistente, erro interno", StatusColor.RED);
                redirect = "";
        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "update":
                ProfessorEntity professorUpdate = new ProfessorEntity(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        request.getParameter("senha"),
                        request.getParameter("palavra")
                );
                Status updateStatus = repository.update(professorUpdate);
                if (updateStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Atualizado com sucesso!", StatusColor.GREEN);
                    redirect = "";
                } else if (updateStatus == Status.NOT_FOUND) {
                    ServletHelper.configureStatus(request, "Erro ao atualizar: professor não encontrado.", StatusColor.RED);
                    redirect = "";
                } else {
                    ServletHelper.configureStatus(request, "Erro interno ao atualizar.", StatusColor.RED);
                    redirect = "";
                }
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/WEB-INF/teacherHome.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}