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
    private final ProfessorRepository professorRepository = new ProfessorRepository();
    private final AptoRepository aptoRepository = new AptoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verificar se o admin está logado
        if (session.getAttribute("role") == null || !session.getAttribute("role").equals("ADMIN")) {
            response.sendRedirect(request.getContextPath() + "/pages/login/admin.jsp");
            return;
        }

        String action = request.getParameter("action");

        List<AlunoEntity> listAlunos = alunoRepository.read();
        if (listAlunos == null) {
            listAlunos = new ArrayList<>();
        }
        request.setAttribute("listAlunos", listAlunos);

        List<AptoEntity> listAptos = aptoRepository.read();
        if (listAptos == null) {
            listAptos = new ArrayList<>();
        }
        request.setAttribute("listAptos", listAptos);

        List<AlunoNotaDTO> todosAlunosNotas = notaRepository.findAllAlunosNotas();
        if (todosAlunosNotas == null) {
            todosAlunosNotas = new ArrayList<>();
        }

        int totalAlunos = alunoRepository.read().size();
        int countTurmas = new TurmaRepository().read().size();
        int totalNotas = todosAlunosNotas.size();

        request.setAttribute("totalAlunos", totalAlunos);
        request.setAttribute("totalTurmas", countTurmas);

        List<AlunoNotaDTO> alunosNotas = new ArrayList<>(todosAlunosNotas);

        if ("buscarAluno".equals(action)) {
            String matriculaStr = request.getParameter("matriculaAluno");
            if (matriculaStr != null && !matriculaStr.trim().isEmpty()) {
                try {
                    int matricula = Integer.parseInt(matriculaStr);
                    List<AlunoNotaDTO> filtrados = new ArrayList<>();
                    for (AlunoNotaDTO dto : alunosNotas) {
                        if (dto.getMatricula() == matricula) {
                            filtrados.add(dto);
                        }
                    }
                    alunosNotas = filtrados;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        request.setAttribute("alunosNotas", alunosNotas);

        List<ObservacaoEntity> observacoes = observacaoRepository.read();
        if (observacoes == null) {
            observacoes = new ArrayList<>();
        }
        request.setAttribute("observacoes", observacoes);
        request.setAttribute("alunoRepository", alunoRepository);
        request.setAttribute("professorRepository", professorRepository);

        ServletHelper.redirect(request, response, "/WEB-INF/view/sights/adminHome.jsp");
    }
}