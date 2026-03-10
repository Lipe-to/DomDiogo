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

        // ===== LISTA DE ALUNOS =====
        List<AlunoEntity> listAlunos = alunoRepository.read();
        if (listAlunos == null) {
            listAlunos = new ArrayList<>();
        }
        request.setAttribute("listAlunos", listAlunos);

        // ===== LISTA DE APTOS =====
        List<AptoEntity> listAptos = aptoRepository.read();
        if (listAptos == null) {
            listAptos = new ArrayList<>();
        }
        request.setAttribute("listAptos", listAptos);

        // ===== ALUNOS + NOTAS (AlunoNotaDTO) — consulta única =====
        List<AlunoNotaDTO> todosAlunosNotas = notaRepository.findAllAlunosNotas();
        if (todosAlunosNotas == null) {
            todosAlunosNotas = new ArrayList<>();
        }

        // ===== ESTATÍSTICAS (calculadas ANTES dos filtros) =====
        int totalAlunos = listAlunos.size();
        Set<String> turmas = new HashSet<>();
        for (AlunoEntity a : listAlunos) {
            if (a.getTurma() != null) turmas.add(a.getTurma());
        }

        int aprovados = 0, reprovados = 0;
        for (AlunoNotaDTO dto : todosAlunosNotas) {
            if (dto.getMedia() != null) {
                if (dto.getMedia() >= 7.0) {
                    aprovados++;
                } else {
                    reprovados++;
                }
            }
        }
        int totalNotas = todosAlunosNotas.size();
        String percApp = "0.0";
        String percRep = "0.0";
        if (totalNotas > 0) {
            percApp = String.format("%.1f", ((double) aprovados / totalNotas) * 100);
            percRep = String.format("%.1f", ((double) reprovados / totalNotas) * 100);
        }

        request.setAttribute("totalAlunos", totalAlunos);
        request.setAttribute("totalTurmas", turmas.size());
        request.setAttribute("percAprovados", percApp);
        request.setAttribute("percReprovados", percRep);

        // ===== FILTROS (aplicados sobre cópia da lista) =====
        List<AlunoNotaDTO> alunosNotas = new ArrayList<>(todosAlunosNotas);

        // Filtro por busca de aluno (matrícula)
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

        // Filtro por disciplina
        String buscaDisciplina = request.getParameter("disciplina");
        if (buscaDisciplina != null && !buscaDisciplina.trim().isEmpty()) {
            List<AlunoNotaDTO> filtrados = new ArrayList<>();
            for (AlunoNotaDTO dto : alunosNotas) {
                if (dto.getDisciplinaNome() != null &&
                        dto.getDisciplinaNome().toLowerCase().contains(buscaDisciplina.toLowerCase())) {
                    filtrados.add(dto);
                }
            }
            alunosNotas = filtrados;
        }

        request.setAttribute("alunosNotas", alunosNotas);

        // ===== OBSERVAÇÕES (TODAS) =====
        List<ObservacaoEntity> observacoes = observacaoRepository.read();
        if (observacoes == null) {
            observacoes = new ArrayList<>();
        }
        request.setAttribute("observacoes", observacoes);


        // ===== REPOSITORIES para uso no JSP =====
        request.setAttribute("alunoRepository", alunoRepository);
        request.setAttribute("professorRepository", professorRepository);

        ServletHelper.redirect(request, response, "/WEB-INF/view/sights/adminHome.jsp");
    }
}