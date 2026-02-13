package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.AlunoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {
    private String redirect = "";
    private final AlunoRepository repository = new AlunoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "read":
                request.setAttribute("listaAlunos", repository.read());
                redirect = "";
                break;

            case "findByMatricula":
                int matricula = Integer.parseInt(request.getParameter("matricula"));
                AlunoEntity aluno = repository.findByMatricula(matricula);
                if (aluno != null) {
                    request.setAttribute("aluno", aluno);
                    ServletHelper.configureStatus(request, "Aluno encontrado com sucesso", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Aluno não encontrado", StatusColor.RED);
                }
                redirect = "";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inexistente, erro interno", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                String usuario = request.getParameter("usuario");
                if (repository.isApto(usuario)) {
                    AlunoEntity alunoEntity = new AlunoEntity(
                            request.getParameter("nome"),
                            usuario,
                            request.getParameter("senha"),
                            request.getParameter("palavra")
                    );
                    Status status = repository.create(alunoEntity);
                    if (status == Status.SUCCESS) {
                        repository.toggleMatriculado(usuario);
                        ServletHelper.configureStatus(request, "Aluno(a) " + alunoEntity.getNome() + " criado com sucesso!", StatusColor.GREEN);
                    } else if (status == Status.NOT_FOUND) {
                        ServletHelper.configureStatus(request, "Já existe um aluno com essas informações, faça login.", StatusColor.RED);
                    } else {
                        ServletHelper.configureStatus(request, "Erro interno, tente novamente.", StatusColor.RED);
                    }
                } else {
                    ServletHelper.configureStatus(request, "Você não está apto a se cadastrar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;

            case "update":
                AlunoEntity alunoUpdate = new AlunoEntity(
                        Integer.parseInt(request.getParameter("matricula")),
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        request.getParameter("senha"),
                        request.getParameter("palavra"),
                        request.getParameter("turma")
                );
                Status updateStatus = repository.update(alunoUpdate);
                if (updateStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Atualizado com sucesso!", StatusColor.GREEN);
                } else if (updateStatus == Status.NOT_FOUND) {
                    ServletHelper.configureStatus(request, "Erro ao atualizar: aluno não encontrado.", StatusColor.RED);
                } else {
                    ServletHelper.configureStatus(request, "Erro interno ao atualizar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;

            case "delete":
                int id = Integer.parseInt(request.getParameter("matricula"));
                Status deleteStatus = repository.delete(id);
                if (deleteStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Deletado com sucesso!", StatusColor.GREEN);
                } else if (deleteStatus == Status.NOT_FOUND) {
                    ServletHelper.configureStatus(request, "Erro ao deletar: aluno não encontrado.", StatusColor.RED);
                } else {
                    ServletHelper.configureStatus(request, "Erro interno ao deletar.", StatusColor.RED);
                }
                redirect = "/WEB-INF/home.jsp";
                break;

            case "login":
                AlunoEntity aluno = repository.login(
                        request.getParameter("usuario"),
                        request.getParameter("senha")
                );

                if (aluno != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("nome", aluno.getNome());
                    session.setAttribute("matricula", aluno.getMatricula());

                    ServletHelper.configureStatus(request, "Login realizado com sucesso!", StatusColor.GREEN);
                    redirect = "/WEB-INF/home.jsp";
                } else {
                    ServletHelper.configureStatus(request, "Usuário ou senha inválidos.", StatusColor.RED);
                    redirect = "/WEB-INF/login.jsp";
                }
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}