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
        String q = request.getParameter("q"); // regex query
        String byProfessorParam = request.getParameter("byProfessor"); // when present and user is professor, list only that professor's avisos
        List<AvisoEntity> avisos;

        HttpSession session = request.getSession(false);
        Integer matriculaSession = null;
        Integer idProfessorSession = null;
        if (session != null) {
            Object m = session.getAttribute("matricula");
            if (m instanceof Integer) matriculaSession = (Integer) m;
            Object p = session.getAttribute("idProfessor");
            if (p instanceof Integer) idProfessorSession = (Integer) p;
        }

        boolean wantProfessorOnly = byProfessorParam != null;

        if (q != null && !q.trim().isEmpty()) {
            String regex = q.trim();
            if (matriculaSession != null) {
                // student — restrict to student's turma and use DB regex search
                AlunoEntity aluno = alunoRepository.findByMatricula(matriculaSession);
                if (aluno != null && aluno.getTurma() != null && !aluno.getTurma().trim().isEmpty()) {
                    avisos = avisoRepository.findByAvisoRegexAndTurma(regex, aluno.getTurma());
                } else {
                    // student without turma — return empty list
                    avisos = new ArrayList<>();
                }
            } else if (idProfessorSession != null && wantProfessorOnly) {
                // professor requested own avisos, fetch professor's avisos and optionally filter by turma
                if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                    avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaParam.trim());
                } else {
                    avisos = avisoRepository.findByProfessor(idProfessorSession);
                }
                // apply regex filter in memory (case-insensitive)
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
                // default to global search
                avisos = avisoRepository.findByProfessor(idProfessorSession);
            }
        } else if (wantProfessorOnly && idProfessorSession != null) {
            // professor wants to see only their avisos (optionally filtered by turma)
            if (turmaParam != null && !turmaParam.trim().isEmpty()) {
                avisos = avisoRepository.findByProfessorAndTurma(idProfessorSession, turmaParam.trim());
            } else {
                avisos = avisoRepository.findByProfessor(idProfessorSession);
            }
        } else if (turmaParam != null && !turmaParam.trim().isEmpty()) {
            avisos = avisoRepository.findByTurma(turmaParam.trim());
        } else {
            // if professor session present, return all; else if student session present, return student's turma; otherwise all
            if (matriculaSession != null) {
                AlunoEntity aluno = alunoRepository.findByMatricula(matriculaSession);
                if (aluno != null && aluno.getTurma() != null && !aluno.getTurma().trim().isEmpty()) {
                    avisos = avisoRepository.findByTurma(aluno.getTurma());
                } else {
                    avisos = new ArrayList<>();
                }
            } else {
                // professor or anonymous -> all
                avisos = avisoRepository.findAll();
            }
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
            if (action.equals("create")) {
                String titulo = request.getParameter("titulo");
                String aviso = request.getParameter("aviso");
                String prazoStr = request.getParameter("prazo");
                String cor = request.getParameter("cor");
                String turmasParam = request.getParameter("turmas"); // comma separated
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
            } else if (action.equals("update")) {
                String idStr = request.getParameter("id");
                int id = Integer.parseInt(idStr);
                String titulo = request.getParameter("titulo");
                String aviso = request.getParameter("aviso");
                String prazoStr = request.getParameter("prazo");
                String cor = request.getParameter("cor");
                String turmasParam = request.getParameter("turmas");
                LocalDate prazo = (prazoStr == null || prazoStr.trim().isEmpty()) ? null : LocalDate.parse(prazoStr);
                List<String> turmas = parseTurmasParam(turmasParam);
                AvisoEntity a = new AvisoEntity(id, titulo, aviso, prazo, idProfessor, cor);
                Status s = avisoRepository.update(a, turmas);
                if (s == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Aviso atualizado.", com.domdiogo.model.StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao atualizar aviso.", com.domdiogo.model.StatusColor.RED);
                }
                doGet(request, response);
                return;
            } else if (action.equals("delete")) {
                String idStr = request.getParameter("id");
                int id = Integer.parseInt(idStr);
                Status s = avisoRepository.delete(id);
                if (s == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Aviso removido.", com.domdiogo.model.StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao remover aviso.", com.domdiogo.model.StatusColor.RED);
                }
                doGet(request, response);
                return;
            } else if (action.equals("addTurma")) {
                int avisoId = Integer.parseInt(request.getParameter("id"));
                String turma = request.getParameter("turma");
                Status s = avisoRepository.addAvisoToTurma(avisoId, turma);
                ServletHelper.configureStatus(request, s == Status.SUCCESS ? "Turma adicionada." : "Erro ao adicionar turma.", s == Status.SUCCESS ? com.domdiogo.model.StatusColor.GREEN : com.domdiogo.model.StatusColor.RED);
                doGet(request, response);
                return;
            } else if (action.equals("removeTurma")) {
                int avisoId = Integer.parseInt(request.getParameter("id"));
                String turma = request.getParameter("turma");
                Status s = avisoRepository.removeAvisoFromTurma(avisoId, turma);
                ServletHelper.configureStatus(request, s == Status.SUCCESS ? "Turma removida." : "Erro ao remover turma.", s == Status.SUCCESS ? com.domdiogo.model.StatusColor.GREEN : com.domdiogo.model.StatusColor.RED);
                doGet(request, response);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ServletHelper.configureStatus(request, "Erro interno.", com.domdiogo.model.StatusColor.RED);
            doGet(request, response);
            return;
        }

        doGet(request, response);
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
