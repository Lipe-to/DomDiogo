package com.domdiogo.servlet;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.AlunoEntity;
import com.domdiogo.model.AlunoNotaDTO;
import com.domdiogo.model.ObservacaoEntity;
import com.domdiogo.repository.AlunoRepository;
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

@WebServlet("/teacherHome")
public class TeacherHomeServlet extends HttpServlet {

    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final NotaRepository notaRepository = new NotaRepository();
    private final ObservacaoRepository observacaoRepository = new ObservacaoRepository();
    private final ProfessorRepository professorRepository = new ProfessorRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verificar se o professor está logado
        if (session.getAttribute("idProfessor") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login/login.jsp");
            return;
        }

        int idProfessor = (int) session.getAttribute("idProfessor");
        String nome = (String) session.getAttribute("nome");

        // Carregando todos os alunos
        List<AlunoEntity> listAlunos = alunoRepository.read();
        if (listAlunos == null) {
            listAlunos = new ArrayList<>();
        }
        request.setAttribute("listAlunos", listAlunos);

        // Carregando alunos e notas para o professor
        List<AlunoNotaDTO> alunosNotas = notaRepository.findAlunosByProfessor(idProfessor);
        if (alunosNotas == null) {
            alunosNotas = new ArrayList<>();
        }
        request.setAttribute("alunosNotas", alunosNotas);

        // Carregando observações do professor
        List<ObservacaoEntity> observacoes = observacaoRepository.findByProfessor(idProfessor);
        if (observacoes == null) {
            observacoes = new ArrayList<>();
        }
        request.setAttribute("observacoes", observacoes);

        // Passando repositórios que serão usados no JSP
        request.setAttribute("alunoRepository", alunoRepository);
        request.setAttribute("professorRepository", professorRepository);

        ServletHelper.redirect(request, response, "/WEB-INF/view/sights/teacherHome.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

