package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AvisoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.repository.AvisoRepository;
import com.domdiogo.repository.AlunoRepository;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.repository.ProfessorRepository;
import com.domdiogo.repository.TurmaRepository;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/aviso")
public class AvisoServlet extends HttpServlet {

    private final AvisoRepository avisoRepository = new AvisoRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private String redirect = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String turmaAluno = request.getParameter("turma"); // turma do aluno logado
        String regex1 = request.getParameter("regex");
        String byProfessorParam = request.getParameter("byProfessor");
        String action = request.getParameter("action");
        List<AvisoEntity> avisos;

        HttpSession session = request.getSession(false);
        Integer matriculaSession = null;
        Integer idProfessorSession = null;
        if (session != null) {
            if (session.getAttribute("matricula") != null) {
                matriculaSession = (Integer) session.getAttribute("matricula");
            } else if (session.getAttribute("idProfessor") != null) {
                idProfessorSession = (Integer) session.getAttribute("idProfessor");
            }
        }

        switch (action) {
            case "regex":
                if (regex1 != null && !regex1.trim().isEmpty()) {
                    String regex = regex1.trim();
                    if (matriculaSession != null) {
                        AlunoEntity aluno = alunoRepository.findByMatricula(matriculaSession);
                        if (aluno != null && aluno.getTurma() != null && !aluno.getTurma().trim().isEmpty()) {
                            avisos = avisoRepository.findByAvisoRegexAndTurma(regex, aluno.getTurma());
                        } else {
                            avisos = new ArrayList<>();
                        }
                    } else if (idProfessorSession != null) {
                        if (turmaAluno != null && !turmaAluno.trim().isEmpty()) {
                            avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaAluno.trim());
                        } else {
                            avisos = avisoRepository.findByProfessor(idProfessorSession);
                        }
                        if (avisos != null && !avisos.isEmpty()) {
                            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex, java.util.regex.Pattern.CASE_INSENSITIVE);
                            List<AvisoEntity> filtered = new ArrayList<>();
                            for (AvisoEntity a : avisos) {
                                if (a.getAviso() != null && p.matcher(a.getAviso()).find()) {
                                    filtered.add(a);
                                }
                            }
                            avisos = filtered;
                        }
                    } else {
                        // admin ou anônimo
                        avisos = avisoRepository.findByAvisoRegex(regex);
                    }
                } else {
                    avisos = avisoRepository.findAll();
                }
                break;

            case "professor":
                redirect = "/WEB-INF/view/sights/notice-board/teacherBoard.jsp";

                if (idProfessorSession != null) {
                    if (turmaAluno != null) {
                        avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaAluno.trim());
                    } else {
                        avisos = avisoRepository.findByProfessor(idProfessorSession);
                    }
                } else {
                    avisos = new ArrayList<>();
                }
                break;

            case "turma":
                redirect = "/WEB-INF/view/sights/notice-board/studentBoard.jsp";    

                if (turmaAluno != null) {
                    avisos = avisoRepository.findByTurma(turmaAluno.trim());
                } else {
                    avisos = avisoRepository.findAll();
                }
                break;

            case "admin":
                redirect = "/WEB-INF/view/sights/notice-board/adminBoard.jsp";

                // Admin pode tudo: regex, turma, professor
                if (regex1 != null && !regex1.trim().isEmpty()) {
                    if (turmaAluno != null && !turmaAluno.trim().isEmpty()) {
                        avisos = avisoRepository.findByAvisoRegexAndTurma(regex1.trim(), turmaAluno.trim());
                    } else {
                        avisos = avisoRepository.findByAvisoRegex(regex1.trim());
                    }
                } else if (turmaAluno != null && !turmaAluno.trim().isEmpty()) {
                    avisos = avisoRepository.findByTurma(turmaAluno.trim());
                } else if (byProfessorParam != null && idProfessorSession != null) {
                    if (turmaAluno != null && !turmaAluno.trim().isEmpty()) {
                        avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaAluno.trim());
                    } else {
                        avisos = avisoRepository.findByProfessor(idProfessorSession);
                    }
                } else {
                    avisos = avisoRepository.findAll();
                }
                break;

            default:
                if (matriculaSession != null) {
                    AlunoEntity aluno = alunoRepository.findByMatricula(matriculaSession);
                    if (aluno != null && aluno.getTurma() != null && !aluno.getTurma().trim().isEmpty()) {
                        avisos = avisoRepository.findByTurma(aluno.getTurma());
                    } else {
                        avisos = new ArrayList<>();
                    }
                } else {
                    avisos = avisoRepository.findAll();
                }
                break;
        }
        request.setAttribute("turmas", new TurmaRepository().read());
        request.setAttribute("professores", new ProfessorRepository().read());
        request.setAttribute("avisos", avisos);

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer idProfessor = (Integer) session.getAttribute("idProfessor");
        if (idProfessor == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "create":
                    String titulo = request.getParameter("titulo");
                    String aviso = request.getParameter("aviso");
                    String prazoStr = request.getParameter("prazo");
                    String cor = request.getParameter("cor");

                    // Agora usamos getParameterValues para turmasFiltro
                    String[] turmasFiltroArray = request.getParameterValues("turmasFiltro");
                    List<String> turmasFiltro = parseTurmasFiltro(turmasFiltroArray);

                    LocalDate prazo = LocalDate.parse(prazoStr);
                    AvisoEntity a = new AvisoEntity(titulo, aviso, prazo, idProfessor, cor);
                    Status s = avisoRepository.create(a, turmasFiltro);
                    if (s == Status.SUCCESS) {
                        ServletHelper.configureStatus(request, "Aviso criado com sucesso.", com.domdiogo.model.StatusColor.GREEN);
                    } else {
                        ServletHelper.configureStatus(request, "Erro ao criar aviso.", com.domdiogo.model.StatusColor.RED);
                    }
                    doGet(request, response);
                    return;

                case "delete":
                    String idStr = request.getParameter("id");
                    int id = Integer.parseInt(idStr);
                    Status s2 = avisoRepository.delete(id);
                    if (s2 == Status.SUCCESS) {
                        ServletHelper.configureStatus(request, "Aviso removido.", com.domdiogo.model.StatusColor.GREEN);
                    } else {
                        ServletHelper.configureStatus(request, "Erro ao remover aviso.", com.domdiogo.model.StatusColor.RED);
                    }
                    doGet(request, response);
                    return;

                default:
                    doGet(request, response);
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletHelper.configureStatus(request, "Erro interno.", com.domdiogo.model.StatusColor.RED);
            doGet(request, response);
        }

        ServletHelper.redirect(request, response, redirect);
    }

    private List<String> parseTurmasFiltro(String[] turmasArray) {
        if (turmasArray == null || turmasArray.length == 0) return null;
        List<String> out = new ArrayList<>();
        for (String t : turmasArray) {
                out.add(t.trim());
        }
        return out;
    }
}