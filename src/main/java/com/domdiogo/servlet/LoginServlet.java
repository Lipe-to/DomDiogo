package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.PasswordUtil;
import com.domdiogo.ServletHelper;
import com.domdiogo.model.*;
import com.domdiogo.repository.AdministradorRepository;
import com.domdiogo.repository.AlunoRepository;
import com.domdiogo.repository.ProfessorRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final ProfessorRepository professorRepository = new ProfessorRepository();
    private final AdministradorRepository administradorRepository = new AdministradorRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String redirect;

        switch (action) {
            case "login":
                if (usuario.contains("@")) {
                    // LOGIN ALUNO — busca por usuario, verifica senha com BCrypt
                    AlunoEntity aluno = alunoRepository.login(usuario);

                    if (aluno != null && PasswordUtil.check(senha, aluno.getSenha())) {
                        // Migração transparente: se a senha estava em plain-text, re-hash
                        if (!PasswordUtil.isHashed(aluno.getSenha())) {
                            aluno.setSenha(PasswordUtil.hash(senha));
                            alunoRepository.update(aluno);
                        }

                        HttpSession session = request.getSession();
                        session.setAttribute("nome", aluno.getNome());
                        session.setAttribute("matricula", aluno.getMatricula());

                        ServletHelper.configureStatus(request,
                                "Login realizado com sucesso!",
                                StatusColor.GREEN);

                        redirect = "/studentHome";
                    } else {
                        ServletHelper.configureStatus(request,
                                "Usuário ou senha inválidos.",
                                StatusColor.RED);

                        redirect = "/index.jsp";
                    }
                } else {
                    // LOGIN PROFESSOR — busca por usuario, verifica senha com BCrypt
                    ProfessorEntity professor = professorRepository.login(usuario);

                    if (professor != null && PasswordUtil.check(senha, professor.getSenha())) {
                        // Migração transparente
                        if (!PasswordUtil.isHashed(professor.getSenha())) {
                            professor.setSenha(PasswordUtil.hash(senha));
                            professorRepository.update(professor);
                        }

                        HttpSession session = request.getSession();
                        session.setAttribute("usuario", professor.getUsuario());
                        session.setAttribute("nome", professor.getNome());
                        session.setAttribute("idProfessor", professor.getId());

                        ServletHelper.configureStatus(request,
                                "Login realizado com sucesso!",
                                StatusColor.GREEN);

                        redirect = "/teacherHome";
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
                    // Hash da nova senha com BCrypt
                    String senhaHash = PasswordUtil.hash(novaSenh);
                    int userId = Integer.parseInt(userIdStr);

                    if (usuarioReset.contains("@")) {
                        AlunoEntity aluno = alunoRepository.findByMatricula(userId);

                        if (aluno != null) {
                            aluno.setSenha(senhaHash);
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
                        } else {
                            ServletHelper.configureStatus(request, "Aluno não encontrado.", StatusColor.RED);
                            redirect = "/pages/login/forgot-password.jsp";
                        }
                    } else {
                        ProfessorEntity professor = professorRepository.findById(userId);

                        if (professor != null) {
                            professor.setSenha(senhaHash);
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

            case "loginAdmin":
                // LOGIN ADMINISTRADOR — busca por usuario, verifica senha com BCrypt
                AdministradorEntity admin = administradorRepository.findByUsuario(usuario);

                if (admin != null && PasswordUtil.check(senha, admin.getSenha())) {
                    // Migração transparente
                    if (!PasswordUtil.isHashed(admin.getSenha())) {
                        admin.setSenha(PasswordUtil.hash(senha));
                        administradorRepository.update(admin);
                    }

                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("role", "ADMIN");

                    ServletHelper.configureStatus(request,
                            "Login de administrador realizado com sucesso!",
                            StatusColor.GREEN);

                    redirect = "/adminHome";
                } else {
                    ServletHelper.configureStatus(request,
                            "Usuário ou senha de administrador inválidos.",
                            StatusColor.RED);

                    redirect = "/pages/login/admin.jsp";
                }
                break;

            case "loginAdmin":
                // LOGIN ADMINISTRADOR
                Status statusAdmin = administradorRepository.login(usuario, senha);

                if (statusAdmin == Status.SUCCESS) {
                    HttpSession session = request.getSession();
                    session.setAttribute("usuario", usuario);
                    session.setAttribute("role", "ADMIN");

                    ServletHelper.configureStatus(request,
                            "Login de administrador realizado com sucesso!",
                            StatusColor.GREEN);

                    redirect = "/adminHome";
                } else {
                    ServletHelper.configureStatus(request,
                            "Usuário ou senha de administrador inválidos.",
                            StatusColor.RED);

                    redirect = "/pages/login/admin.jsp";
                }
                break;

            case "logout":
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                ServletHelper.configureStatus(request, "Logout realizado com sucesso!", StatusColor.GREEN);
                redirect = "/index.jsp";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/index.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}