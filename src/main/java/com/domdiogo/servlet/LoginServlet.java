package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.ProfessorEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.repository.AlunoRepository;
import com.domdiogo.repository.ProfessorRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private String redirect = "";
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        switch (action) {
            case "login":
                if (usuario.contains("@")) {
                    // LOGIN ALUNO
                    AlunoEntity aluno = alunoRepository.login(usuario, senha);

                    if (aluno != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("nome", aluno.getNome());
                        session.setAttribute("matricula", aluno.getMatricula());

                        ServletHelper.configureStatus(request,
                                "Login realizado com sucesso!",
                                StatusColor.GREEN);

                        redirect = "/WEB-INF/view/sights/studentHome.jsp";
                    } else {
                        ServletHelper.configureStatus(request,
                                "Usuário ou senha inválidos.",
                                StatusColor.RED);

                        redirect = "/index.jsp";
                    }
                } else {
                    // LOGIN PROFESSOR
                    ProfessorEntity professor = professorRepository.login(usuario, senha);

                    if (professor != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("usuario", professor.getUsuario());
                        session.setAttribute("nome", professor.getNome());
                        session.setAttribute("idProfessor", professor.getId());

                        ServletHelper.configureStatus(request,
                                "Login realizado com sucesso!",
                                StatusColor.GREEN);

                        redirect = "/WEB-INF/view/sights/teacherHome.jsp";
                    } else {
                        ServletHelper.configureStatus(request,
                                "Usuário ou senha inválidos.",
                                StatusColor.RED);

                        redirect = "/index.jsp";
                    }
                }
                break;

            case "validarPalavra":
                String usuarioValidacao = request.getParameter("usuario");
                String palavraValidacao = request.getParameter("palavra");
                request.setAttribute("usuario", usuarioValidacao);

                if (usuarioValidacao.contains("@")) {
                    // VALIDAR PALAVRA - ALUNO
                    Status validarStatusAluno = alunoRepository.validarPalavra(usuarioValidacao, palavraValidacao);

                    if (validarStatusAluno == Status.SUCCESS) {
                        AlunoEntity alunoValidacao = alunoRepository.findByUsuario(usuarioValidacao);
                        request.setAttribute("userId", alunoValidacao.getMatricula());
                        ServletHelper.configureStatus(request, "Palavra validada com sucesso!", StatusColor.GREEN);
                        redirect = "/WEB-INF/view/login/reset-password.jsp";
                    } else if (validarStatusAluno == Status.NOT_FOUND) {
                        ServletHelper.configureStatus(request, "Palavra ou usuário inválidos.", StatusColor.RED);
                        redirect = "/pages/login/forgot-password.jsp";
                    } else {
                        ServletHelper.configureStatus(request, "Erro interno ao validar palavra.", StatusColor.RED);
                        redirect = "/pages/login/forgot-password.jsp";
                    }
                } else {
                    // VALIDAR PALAVRA - PROFESSOR
                    Status validarStatusProfessor = professorRepository.validarPalavra(usuarioValidacao, palavraValidacao);

                    if (validarStatusProfessor == Status.SUCCESS) {
                        ProfessorEntity professorValidacao = professorRepository.findByUsuario(usuarioValidacao);
                        request.setAttribute("userId", professorValidacao.getId());
                        ServletHelper.configureStatus(request, "Palavra validada com sucesso!", StatusColor.GREEN);
                        redirect = "/WEB-INF/view/login/reset-password.jsp";
                    } else if (validarStatusProfessor == Status.NOT_FOUND) {
                        ServletHelper.configureStatus(request, "Palavra ou usuário inválidos.", StatusColor.RED);
                        redirect = "/pages/login/forgot-password.jsp";
                    } else {
                        ServletHelper.configureStatus(request, "Erro interno ao validar palavra.", StatusColor.RED);
                        redirect = "/pages/login/forgot-password.jsp";
                    }
                }
                break;

            case "resetPassword":
                String novaSenh = request.getParameter("senha");
                String confirmarSenh = request.getParameter("confirmarSenha");
                String userIdStr = request.getParameter("userId");
                String novaPalavra = request.getParameter("palavra");
                String usuarioReset = request.getParameter("usuario");

                if (!novaSenh.equals(confirmarSenh)) {
                    ServletHelper.configureStatus(request, "As senhas não correspondem.", StatusColor.RED);
                    redirect = "/WEB-INF/view/login/reset-password.jsp";
                    request.setAttribute("userId", userIdStr);
                } else {
                        int userId = Integer.parseInt(userIdStr);
                        if (usuarioReset.contains("@")) {
                            AlunoEntity aluno = alunoRepository.findByMatricula(userId);

                            if (aluno != null) {
                                aluno.setSenha(novaSenh);
                                // Atualizar palavra-chave apenas se foi fornecida
                                if (novaPalavra != null && !novaPalavra.trim().isEmpty()) {
                                    aluno.setPalavra(novaPalavra);
                                }
                                Status statusAluno = alunoRepository.update(aluno);

                                if (statusAluno == Status.SUCCESS) {
                                    ServletHelper.configureStatus(request, "Senha atualizada com sucesso!", StatusColor.GREEN);
                                    redirect = "/index.jsp";
                                } else {
                                    ServletHelper.configureStatus(request, "Erro ao atualizar senha.", StatusColor.RED);
                                    redirect = "/WEB-INF/view/login/reset-password.jsp";
                                    request.setAttribute("userId", userIdStr);
                                }
                        }


                        } else {
                            // Tentar como professor
                            ProfessorEntity professor = professorRepository.findById(userId);

                            if (professor != null) {
                                // É professor
                                professor.setSenha(novaSenh);
                                // Atualizar palavra-chave apenas se foi fornecida
                                if (novaPalavra != null && !novaPalavra.trim().isEmpty()) {
                                    professor.setPalavra(novaPalavra);
                                }
                                Status statusProfessor = professorRepository.update(professor);

                                if (statusProfessor == Status.SUCCESS) {
                                    ServletHelper.configureStatus(request, "Senha atualizada com sucesso!", StatusColor.GREEN);
                                    redirect = "/index.jsp";
                                } else {
                                    ServletHelper.configureStatus(request, "Erro ao atualizar senha.", StatusColor.RED);
                                    redirect = "/WEB-INF/view/login/reset-password.jsp";
                                    request.setAttribute("userId", userIdStr);
                                }
                            } else {
                                ServletHelper.configureStatus(request, "Usuário não encontrado.", StatusColor.RED);
                                redirect = "/pages/login/index.jsp";
                            }
                        }
                }
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/index.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}