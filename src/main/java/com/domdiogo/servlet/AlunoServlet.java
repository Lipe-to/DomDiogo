package com.domdiogo.servlet;

import java.io.IOException;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.AlunoRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
    private String redirect = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AlunoRepository repository = new AlunoRepository();
        String action = request.getParameter("action");

        switch (action) {
            case "read":
                request.setAttribute("listaAlunos", repository.read());
                redirect = "";
                break;

            case "findByMatricula":
                AlunoEntity aluno = repository.findByMatricula(Integer.parseInt(request.getParameter("matricula")));
                if (aluno != null) {
                    request.setAttribute("aluno", aluno);
                }
                redirect = "";
                break;

            default:
                configureStatus(request, "Ação inexistente, erro interno", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(redirect);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        AlunoRepository repository = new AlunoRepository();
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                String usuario = request.getParameter("usuario");
                if (repository.isApto(usuario)) {
                    AlunoEntity alunoEntity = new AlunoEntity(
                            Integer.parseInt(request.getParameter("matricula")),
                            request.getParameter("nome"),
                            usuario,
                            request.getParameter("senha"),
                            request.getParameter("palavra")
                    );
                    int status = repository.create(alunoEntity);
                    if (status > 0) {
                        repository.toggleMatriculado(usuario);
                        configureStatus(request, "Aluno(a) " + alunoEntity.getNome() + " criado com sucesso!", StatusColor.GREEN);
                    } else if (status == 0) {
                        configureStatus(request, "Já existe um aluno com essas informações, faça login!", StatusColor.RED);
                    } else {
                        configureStatus(request, "Erro interno, tente novamente.", StatusColor.RED);
                    }
                } else {
                    configureStatus(request, "Você não está apto a se cadastrar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;

            case "update":
                AlunoEntity alunoUpdate = new AlunoEntity(
                        Integer.parseInt(request.getParameter("matricula")),
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        request.getParameter("senha"),
                        request.getParameter("palavra")
                );
                int updateStatus = repository.update(alunoUpdate);
                if (updateStatus > 0) {
                    configureStatus(request, "Atualizado com sucesso!", StatusColor.GREEN);
                } else {
                    configureStatus(request, "Erro ao atualizar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;
            case "delete":
                int id = Integer.parseInt(request.getParameter("matricula"));
                int deleteStatus = repository.delete(id);
                if (deleteStatus > 0) {
                    configureStatus(request, "Deletado com sucesso!", StatusColor.GREEN);
                } else {
                    configureStatus(request, "Erro ao deletar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;

            default:
                redirect = "/WEB-INF/home.jsp";
                break;
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(redirect);
        dispatcher.forward(request, response);
    }

    private void configureStatus(HttpServletRequest request, String statusMessage, StatusColor statusColor) {
        request.setAttribute("statusMessage", statusMessage);
        request.setAttribute("statusColor", statusColor.getValue());
    }
}