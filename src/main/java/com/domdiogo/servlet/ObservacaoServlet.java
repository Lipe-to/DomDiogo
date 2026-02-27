package com.domdiogo.servlet;

import java.io.IOException;
import java.util.List;

import com.domdiogo.ServletHelper;
import com.domdiogo.model.ObservacaoEntity;
import com.domdiogo.model.Status;
import com.domdiogo.model.StatusColor;
import com.domdiogo.model.ColorPalette;
import com.domdiogo.repository.ObservacaoRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/observacao", "/observacao/*"})
public class ObservacaoServlet extends HttpServlet {
    private String redirect = "";
    private final ObservacaoRepository repository = new ObservacaoRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "readAll":
                List<ObservacaoEntity> listaObservacoes = repository.read();
                request.setAttribute("observacoes", listaObservacoes);
                ServletHelper.configureStatus(request, "Observações carregadas com sucesso!", StatusColor.GREEN);
                redirect = "/WEB-INF/observacoes.jsp";
                break;

            case "findById":
                int id = Integer.parseInt(request.getParameter("id"));
                ObservacaoEntity observacao = repository.findById(id);
                if (observacao != null) {
                    request.setAttribute("observacao", observacao);
                    ServletHelper.configureStatus(request, "Observação encontrada!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Observação não encontrada.", StatusColor.RED);
                }
                redirect = "/WEB-INF/observacaoDetalhe.jsp";
                break;

            case "findByMatriculaAluno":
                int matriculaAluno = Integer.parseInt(request.getParameter("matriculaAluno"));
                List<ObservacaoEntity> obsAluno = repository.findByMatriculaAluno(matriculaAluno);
                request.setAttribute("observacoes", obsAluno);
                ServletHelper.configureStatus(request, "Observações do aluno carregadas.", StatusColor.GREEN);
                redirect = "/WEB-INF/observacoes.jsp";
                break;

            case "findByProfessor":
                int idProfessor = Integer.parseInt(request.getParameter("idProfessor"));
                List<ObservacaoEntity> obsProfessor = repository.findByProfessor(idProfessor);
                request.setAttribute("observacoes", obsProfessor);
                ServletHelper.configureStatus(request, "Observações do professor carregadas.", StatusColor.GREEN);
                redirect = "/WEB-INF/observacoes.jsp";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inexistente.", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                ObservacaoEntity novaObservacao = new ObservacaoEntity(
                        request.getParameter("titulo"),
                        Integer.parseInt(request.getParameter("matriculaAluno")),
                        Integer.parseInt(request.getParameter("idProfessor")),
                        request.getParameter("observacao"),
                        ColorPalette.valueOf(request.getParameter("cor"))
                );
                Status createStatus = repository.create(novaObservacao);
                if (createStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Observação criada com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao criar observação.", StatusColor.RED);
                }
                redirect = "/observacao?action=readAll";
                break;

            case "update":
                ObservacaoEntity observacaoUpdate = new ObservacaoEntity(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("titulo"),
                        Integer.parseInt(request.getParameter("matriculaAluno")),
                        Integer.parseInt(request.getParameter("idProfessor")),
                        request.getParameter("observacao"),
                        ColorPalette.valueOf(request.getParameter("cor"))
                );
                Status updateStatus = repository.update(observacaoUpdate);
                if (updateStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Observação atualizada com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao atualizar observação.", StatusColor.RED);
                }
                redirect = "/observacao?action=readAll";
                break;

            case "delete":
                int idDelete = Integer.parseInt(request.getParameter("id"));
                Status deleteStatus = repository.delete(idDelete);
                if (deleteStatus == Status.SUCCESS) {
                    ServletHelper.configureStatus(request, "Observação excluída com sucesso!", StatusColor.GREEN);
                } else {
                    ServletHelper.configureStatus(request, "Erro ao excluir observação.", StatusColor.RED);
                }
                redirect = "/observacao?action=readAll";
                break;

            default:
                ServletHelper.configureStatus(request, "Ação inválida.", StatusColor.RED);
                redirect = "/WEB-INF/home.jsp";
                break;
        }

        ServletHelper.redirect(request, response, redirect);
    }
}