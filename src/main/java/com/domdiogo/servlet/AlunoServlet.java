package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.PasswordUtil;
import com.domdiogo.ServletHelper;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.AlunoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet({"/aluno", "/aluno/*"})
public class AlunoServlet extends HttpServlet {
    private final AlunoRepository repository = new AlunoRepository();

    private String getRedirectPath(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && "ADMIN".equals(session.getAttribute("role"))) {
            return "/adminHome";
        }
        return "/teacherHome";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String redirect;

        switch (action) {
            case "read":
                request.setAttribute("listaAlunos", repository.read());
                redirect = getRedirectPath(request);
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
                redirect = getRedirectPath(request);
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inexistente, erro interno", StatusColor.RED);
                redirect = getRedirectPath(request);
        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String redirect;

        switch (action) {
            case "create":
                String usuario = request.getParameter("usuario");
                if (repository.isApto(usuario)) {
                    AlunoEntity alunoEntity = new AlunoEntity(
                            usuario,
                            PasswordUtil.hash(request.getParameter("senha")),
                            request.getParameter("palavra")
                    );
                    Status status = repository.create(alunoEntity);
                    if (status == Status.SUCCESS) {
                        //cria as notas para o aluno criado apenas com sua matricula
                        repository.createNotas(repository.findByUsuario(usuario).getMatricula());
                        repository.toggleMatriculado(usuario);
                        ServletHelper.configureStatus(request, "Aluno(a) criado com sucesso!", StatusColor.GREEN);
                        redirect = "/index.jsp";
                    } else if (status == Status.NOT_FOUND) {
                        ServletHelper.configureStatus(request, "Já existe um aluno com essas informações, faça login.", StatusColor.RED);
                        redirect = "/pages/login/register.jsp";
                    } else {
                        ServletHelper.configureStatus(request, "Erro interno, tente novamente.", StatusColor.RED);
                        redirect = "/pages/login/register.jsp";
                    }
                } else {
                    ServletHelper.configureStatus(request, "Você não está apto a se cadastrar.", StatusColor.RED);
                    redirect = "/pages/login/register.jsp";
                }
                break;

            case "update":
                String senhaUpdate = request.getParameter("senha");
                int matriculaUpdate = Integer.parseInt(request.getParameter("matricula"));

                // Se a senha veio vazia, manter a atual do banco
                if (senhaUpdate == null || senhaUpdate.trim().isEmpty()) {
                    AlunoEntity atual = repository.findByMatricula(matriculaUpdate);
                    senhaUpdate = (atual != null) ? atual.getSenha() : "";
                } else if (!PasswordUtil.isHashed(senhaUpdate)) {
                    // Senha nova em plain-text: fazer hash
                    senhaUpdate = PasswordUtil.hash(senhaUpdate);
                }

                AlunoEntity alunoUpdate = new AlunoEntity(
                        matriculaUpdate,
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        senhaUpdate,
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
                redirect = getRedirectPath(request);
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
                redirect = getRedirectPath(request);
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = getRedirectPath(request);
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}