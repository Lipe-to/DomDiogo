package com.domdiogo.servlet;

import java.io.*;

import com.domdiogo.model.AlunoEntity;
import com.domdiogo.repository.AlunoRepository;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("aluno")
public class AlunoServlet extends HttpServlet {
    private String statusMessage = null;
    private String statusColor = "red";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AlunoRepository repository = new AlunoRepository();

        switch (request.getParameter("action")){
            case "create":
                if (repository.isApto(request.getParameter("usuario"))){
                AlunoEntity alunoEntity = new AlunoEntity(
                        Integer.parseInt("matricula"),
                        request.getParameter("nome"),
                        request.getParameter("usuario"),
                        request.getParameter("senha"),
                        request.getParameter("palavra")
                );
                switch (repository.create(alunoEntity)) {
                    case -1:
                        statusMessage = "Erro interno, tente denovo ou entre em contato com a instituição.";
                        break;
                    case 0:
                        statusMessage = "Já existe um aluno com essas informações, faça login!";
                    default:
                        configureStatus("Aluno(a) " + request.getParameter("nome") + "criado com sucesso!", "green");
                        break;
                }
                }else{
                    configureStatus("Você não está apto a se cadastrar.", "red");
                }
                break;
        };
    }
    public void configureStatus(String statusMessage, String statusColor){
        this.statusMessage = statusMessage;
        this.statusColor = statusColor;
    }
}