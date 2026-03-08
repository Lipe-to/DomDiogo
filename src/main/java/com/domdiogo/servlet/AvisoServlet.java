package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AvisoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.repository.AvisoRepository;
import com.domdiogo.repository.AlunoRepository;
import com.domdiogo.model.AlunoEntity;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String turmaParam = request.getParameter("turma");
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
                        if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                            avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaParam.trim());
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
                if (idProfessorSession != null) {
                    if (turmaParam != null) {
                        avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaParam.trim());
                    } else {
                        avisos = avisoRepository.findByProfessor(idProfessorSession);
                    }
                } else {
                    avisos = new ArrayList<>();
                }
                break;

            case "turma":
                if (turmaParam != null) {
                    avisos = avisoRepository.findByTurma(turmaParam.trim());
                } else {
                    avisos = avisoRepository.findAll();
                }
                break;

            case "admin":
                // Admin pode tudo: regex, turma, professor
                if (regex1 != null && !regex1.trim().isEmpty()) {
                    if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                        avisos = avisoRepository.findByAvisoRegexAndTurma(regex1.trim(), turmaParam.trim());
                    } else {
                        avisos = avisoRepository.findByAvisoRegex(regex1.trim());
                    }
                } else if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                    avisos = avisoRepository.findByTurma(turmaParam.trim());
                } else if (byProfessorParam != null && idProfessorSession != null) {
                    if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                        avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaParam.trim());
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

        request.setAttribute("avisos", avisos);
        ServletHelper.redirect(request, response, "/WEB-INF/view/aviso/list.jsp");
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
                    String turmasParam = request.getParameter("turmas");
                    LocalDate prazo = (prazoStr == null || prazoStr.trim().isEmpty()) ? null : LocalDate.parse(prazoStr);
                    List<String> turmas = parseTurmasParam(turmasParam);
                    AvisoEntity a = new AvisoEntity(titulo, aviso, prazo, idProfessor, cor);
                    Status s = avisoRepository.create(a, turmas);
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
    }

    private List<String> parseTurmasParam(String turmasParam) {
        if (turmasParam == null) return null;
        turmasParam = turmasParam.trim();
        if (turmasParam.isEmpty()) return null;
        String[] arr = turmasParam.split(",");
        List<String> out = new ArrayList<>();
        for (String t : arr) {
            t = t.trim();
            if (!t.isEmpty()) out.add(t);
        }
        return out;
    }
}