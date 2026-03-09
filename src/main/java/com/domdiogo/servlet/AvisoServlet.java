package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AvisoEntity;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.repository.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aviso")
public class AvisoServlet extends HttpServlet {

    private final AvisoRepository avisoRepository = new AvisoRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final TurmaRepository turmaRepository = new TurmaRepository();
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String regex = request.getParameter("regex");
        String turma = request.getParameter("turma");
        String professorParam = request.getParameter("professor");

        if (action == null) action = "aluno";

        List<AvisoEntity> avisos = new ArrayList<>();
        String redirect;

        HttpSession session = request.getSession(false);

        Integer matriculaSession = null;
        Integer idProfessorSession = null;

        if (session != null) {
            matriculaSession = (Integer) session.getAttribute("matricula");
            idProfessorSession = (Integer) session.getAttribute("idProfessor");
        }

        switch (action) {

            // =========================
            // ALUNO
            // =========================
            case "aluno":

                redirect = "/WEB-INF/view/sights/notice-board/studentBoard.jsp";

                if (matriculaSession != null) {

                    AlunoEntity aluno = alunoRepository.findByMatricula(matriculaSession);

                    if (aluno != null) {

                        String turmaAluno = aluno.getTurma();

                        if (regex != null && !regex.isBlank()) {

                            avisos = avisoRepository.findByAvisoRegexAndTurma(regex, turmaAluno);

                        } else if (professorParam != null && !professorParam.isBlank()) {

                            int professorId = Integer.parseInt(professorParam);
                            avisos = avisoRepository.findByProfessorAndTurma(professorId, turmaAluno);

                        } else {

                            avisos = avisoRepository.findByTurma(turmaAluno);

                        }
                    }
                }

                break;

            // =========================
            // PROFESSOR
            // =========================
            case "professor":

                redirect = "/WEB-INF/view/sights/notice-board/teacherBoard.jsp";

                if (idProfessorSession != null) {

                    if (turma != null && !turma.isBlank()) {

                        avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turma);

                    } else {

                        avisos = avisoRepository.findByProfessor(idProfessorSession);

                    }

                    if (regex != null && !regex.isBlank()) {

                        String r = regex.toLowerCase();

                        avisos.removeIf(a ->
                                a.getAviso() == null ||
                                        !a.getAviso().toLowerCase().contains(r)
                        );
                    }
                }

                break;

            // =========================
            // ADMIN
            // =========================
            case "admin":

                redirect = "/WEB-INF/view/sights/notice-board/adminBoard.jsp";

                if (regex != null && !regex.isBlank()) {

                    if (turma != null && !turma.isBlank()) {

                        avisos = avisoRepository.findByAvisoRegexAndTurma(regex, turma);

                    } else {

                        avisos = avisoRepository.findByAvisoRegex(regex);

                    }

                } else if (professorParam != null && !professorParam.isBlank()) {

                    int professorId = Integer.parseInt(professorParam);

                    if (turma != null && !turma.isBlank()) {

                        avisos = avisoRepository.findByProfessorAndTurma(professorId, turma);

                    } else {

                        avisos = avisoRepository.findByProfessor(professorId);

                    }

                } else if (turma != null && !turma.isBlank()) {

                    avisos = avisoRepository.findByTurma(turma);

                } else {

                    avisos = avisoRepository.findAll();

                }

                break;

            default:

                redirect = "/WEB-INF/view/sights/notice-board/studentBoard.jsp";
                avisos = avisoRepository.findAll();

        }

        request.setAttribute("turmas", turmaRepository.read());
        request.setAttribute("professores", professorRepository.read());
        request.setAttribute("avisos", avisos);

        ServletHelper.redirect(request, response, redirect);
    }
}