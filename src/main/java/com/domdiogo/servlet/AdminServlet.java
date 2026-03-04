package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.*;
import com.domdiogo.repository.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/adminHome")
public class AdminServlet extends HttpServlet {

    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final NotaRepository notaRepository = new NotaRepository();
    private final ObservacaoRepository observacaoRepository = new ObservacaoRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/pages/login/admin.jsp");
            return;
        }

        List<AlunoEntity> alunos = alunoRepository.read();
        List<NotaEntity> todasNotas = notaRepository.readAll();
        List<ObservacaoEntity> observacoes = observacaoRepository.read();

        int totalAlunos = alunos.size();
        Set<String> turmas = new HashSet<>();
        for (AlunoEntity a : alunos) { if (a.getTurma() != null) turmas.add(a.getTurma()); }

        int reprovados = 0;
        for (NotaEntity n : todasNotas) {
            if (((n.getN1() + n.getN2()) / 2.0) < 7.0) reprovados++;
        }

        String percRep = "0.0";
        String percApp = "0.0";
        if (!todasNotas.isEmpty()) {
            double pRep = ((double) reprovados / todasNotas.size()) * 100;
            percRep = String.format("%.1f", pRep);
            percApp = String.format("%.1f", 100.0 - pRep);
        }

        String busca = request.getParameter("materia");
        List<NotaEntity> notasFiltradas = new ArrayList<>();
        if (busca != null && !busca.trim().isEmpty()) {
            for (NotaEntity n : todasNotas) {
                if (n.getNomeDisciplina().toLowerCase().contains(busca.toLowerCase())) {
                    notasFiltradas.add(n);
                }
            }
        } else { notasFiltradas = todasNotas; }

        request.setAttribute("totalAlunos", totalAlunos);
        request.setAttribute("totalTurmas", turmas.size());
        request.setAttribute("percAprovados", percApp);
        request.setAttribute("percReprovados", percRep);
        request.setAttribute("listaAlunos", alunos);
        request.setAttribute("listaNotas", notasFiltradas);
        request.setAttribute("listaObservacoes", observacoes);

        ServletHelper.redirect(request, response, "/WEB-INF/view/sights/adminHome.jsp");
    }
}