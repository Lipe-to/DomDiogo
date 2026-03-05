package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.NotaEntity;
import com.domdiogo.model.ObservacaoEntity;
import com.domdiogo.repository.NotaRepository;
import com.domdiogo.repository.ObservacaoRepository;
import com.domdiogo.repository.ProfessorRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/studentHome")
public class StudentHomeServlet extends HttpServlet {

    private final NotaRepository notaRepository = new NotaRepository();
    private final ObservacaoRepository observacaoRepository = new ObservacaoRepository();
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verificar se o aluno está logado
        if (session.getAttribute("matricula") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
            return;
        }

        int matricula = (int) session.getAttribute("matricula");
        String nome = (String) session.getAttribute("nome");

        // Carregando notas do aluno
        List<NotaEntity> notas = notaRepository.findByMatricula(matricula);
        if (notas == null) {
            notas = new ArrayList<>();
        }
        request.setAttribute("notas", notas);

        // Carregando observações do aluno
        List<ObservacaoEntity> observacoes = observacaoRepository.findByMatriculaAluno(matricula);
        if (observacoes == null) {
            observacoes = new ArrayList<>();
        }
        request.setAttribute("observacoes", observacoes);

        // Passando o repositório de professor para o JSP poder obter os nomes
        request.setAttribute("professorRepository", professorRepository);

        ServletHelper.redirect(request, response, "/WEB-INF/view/sights/studentHome.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

