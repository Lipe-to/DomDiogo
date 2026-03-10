package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AvisoEntity;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.repository.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
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

                    if (regex != null && !regex.isBlank()) {

                        avisos = avisoRepository.findByAvisoRegexAndProfessor(regex, idProfessorSession);

                    } else {

                        avisos = avisoRepository.findByProfessor(idProfessorSession);

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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        HttpSession session = request.getSession(false);
        Integer idProfessorSession = null;

        if (session != null) {
            idProfessorSession = (Integer) session.getAttribute("idProfessor");
        }

        if (idProfessorSession == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        switch (action) {

            // =========================
            // CRIAR AVISO
            // =========================
            case "create":

                String titulo = request.getParameter("titulo");
                String avisoTexto = request.getParameter("aviso");
                String prazoParam = request.getParameter("prazo");
                String cor = request.getParameter("cor");

                String[] turmasSelecionadas = request.getParameterValues("turmas");

                LocalDate prazo = null;

                if (prazoParam != null && !prazoParam.isBlank()) {
                    prazo = LocalDate.parse(prazoParam);
                }

                List<String> turmas = new ArrayList<>();

                if (turmasSelecionadas != null) {
                    for (String t : turmasSelecionadas) {
                        turmas.add(t);
                    }
                }

                AvisoEntity aviso = new AvisoEntity(
                        0,
                        titulo,
                        avisoTexto,
                        prazo,
                        idProfessorSession,
                        cor
                );

                avisoRepository.create(aviso, turmas);

                break;


            // =========================
            // DELETAR AVISO
            // =========================
            case "delete":

                String idAvisoParam = request.getParameter("id");

                if (idAvisoParam != null) {

                    int idAviso = Integer.parseInt(idAvisoParam);

                    AvisoEntity avisoExistente = avisoRepository.findById(idAviso);

                    // segurança: professor só apaga aviso dele
                    if (avisoExistente != null &&
                            avisoExistente.getIdProfessor() == idProfessorSession) {

                        avisoRepository.delete(idAviso);
                    }
                }

                break;
        }

        response.sendRedirect("aviso?action=professor");
    }
}