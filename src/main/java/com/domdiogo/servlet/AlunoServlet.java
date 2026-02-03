package com.domdiogo.servlet;

import java.io.*;

import com.domdiogo.model.AlunoEntity;
import com.domdiogo.repository.AlunoRepository;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("aluno")
public class AlunoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AlunoRepository repository = new AlunoRepository();
        switch (request.getParameter("action")){
            case "create":
                AlunoEntity alunoEntity = new AlunoEntity(
                        Integer.parseInt(request.getParameter("id")),
                        request.getParameter("nome"),
                        request.getParameter("matriucla"),
                        request.getParameter("email"),
                        request.getParameter("senha")
                );

                repository.create(alunoEntity);
        };
    }
}