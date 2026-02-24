package com.domdiogo.servlet;

import java.io.IOException;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.ProfessorEntity;
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

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        if (usuario != null && usuario.contains("@")) {

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

                redirect = "/WEB-INF/homeProfessor.jsp";
            } else {
                ServletHelper.configureStatus(request,
                        "Usuário ou senha inválidos.",
                        StatusColor.RED);

                redirect = "/index.jsp";
            }
        }

        ServletHelper.redirect(request, response, redirect);
    }
}