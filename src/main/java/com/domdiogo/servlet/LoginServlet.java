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
                Status validarStatus = professorRepository.validarPalavra(
                        request.getParameter("usuario"),
                        request.getParameter("palavra")
                );
                if (validarStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Palavra validada com sucesso!", StatusColor.GREEN);
                } else if (validarStatus == Status.NOT_FOUND) {
                    ServletHelper.configureStatus(request, "Palavra inválida.", StatusColor.RED);
                } else {
                    ServletHelper.configureStatus(request, "Erro interno ao validar palavra.", StatusColor.RED);
                }
                redirect = "/WEB-INF/view/sights/teacherHome.jsp";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/index.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}