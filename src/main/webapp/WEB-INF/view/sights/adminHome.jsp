<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="com.domdiogo.model.NotaEntity" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.model.ColorPalette" %>

<%
    Integer totalAlunos = (Integer) request.getAttribute("totalAlunos");
    Integer totalTurmas = (Integer) request.getAttribute("totalTurmas");
    String percApp = (String) request.getAttribute("percAprovados");
    String percRep = (String) request.getAttribute("percReprovados");

    List<AlunoEntity> listaAlunos = (List<AlunoEntity>) request.getAttribute("listaAlunos");
    List<NotaEntity> listaNotas = (List<NotaEntity>) request.getAttribute("listaNotas");
    List<ObservacaoEntity> listaObservacoes = (List<ObservacaoEntity>) request.getAttribute("listaObservacoes");

    String context = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dom Diogo - Admin</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" />

    <link rel="stylesheet" href="<%= context %>/css/sights/both.css">
    <link rel="stylesheet" href="<%= context %>/css/sights/teacher.css">
    <link rel="stylesheet" href="<%= context %>/css/popup.css">

    <link rel="stylesheet" href="<%= context %>/css/style.css">
    <link rel="stylesheet" href="<%= context %>/css/variables.css">
    <link rel="stylesheet" href="<%= context %>/css/font.css">
</head>

<body>
<aside id="sidebar">
    <ul>
        <div>
            <li id="menu-icon-container">
                <label id="menu-icon" for="menu-checkbox">
                    <img class="sidebar-icon" src="<%= context %>/img/svg/sidebar/menu-burger.svg">
                </label>
                <input name="menu-checkbox" id="menu-checkbox" type="checkbox">
            </li>
            <p id="menu-text">Menu</p>
            <li class="emphasis">
                <a href="<%= context %>/adminHome">
                    <img class="sidebar-icon" src="<%= context %>/img/svg/sidebar/home-emphasis.svg" alt="">
                    <span>Tela Inicial</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img class="sidebar-icon" src="<%= context %>/img/svg/sidebar/dashboard.svg" alt="">
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="#">
                    <img class="sidebar-icon" src="<%= context %>/img/svg/sidebar/address-book.svg" alt="">
                    <span>Professores</span>
                </a>
            </li>
        </div>
        <li id="sign-out">
            <button onclick="window.location.href='<%= context %>/index.jsp'">
                <img class="sidebar-icon" src="<%= context %>/img/svg/sidebar/sign-out.svg" alt="">
                <span>Sair</span>
            </button>
        </li>
    </ul>
</aside>

<div id="major-container">
    <div id="wrap">
        <header>
            <a href="#"><img class="logo" src="<%= context %>/img/branding/teste.png" alt="Logo"></a>
            <div class="personal-info">
                <div class="profile-image"></div>
                <div>
                    <h3>Administrador</h3>
                    <p>Gestão Escolar</p>
                </div>
            </div>
        </header>

        <main>
            <div id="front-desk">
                <div class="castle" id="welcome">
                    <h2>Olá, administrador!</h2>
                    <p>Bem-vindo(a) de volta!</p>
                </div>
                <div class="general-statistic">
                    <a href="" class="h2">Visão geral <img class="redirect" src="<%= context %>/img/svg/redirect-blue.svg" alt=""></a>
                    <div>
                        <div>
                            <h3><span><%= totalAlunos != null ? totalAlunos : 0 %></span></h3>
                            <span>Total de alunos</span>
                        </div>
                        <div>
                            <h3><span><%= totalTurmas != null ? totalTurmas : 0 %></span></h3>
                            <span>Total de turmas</span>
                        </div>
                        <div>
                            <h3><span class="appr"><%= percApp != null ? percApp : "0.0" %></span> %</h3>
                            <span>Alunos aprovados</span>
                        </div>
                        <div>
                            <h3><span class="repr"><%= percRep != null ? percRep : "0.0" %></span> %</h3>
                            <span>Alunos reprovados</span>
                        </div>
                    </div>
                </div>
            </div>

            <div id="grades">
                <h1>Alunos</h1>
                <div class="wrap-for-scroll">
                    <div class="table-container">
                        <div class="table-info">
                            <div>
                                <h3>Informações pessoais</h3>
                                <sub>Dados sensíveis</sub>
                            </div>
                        </div>
                        <div class="table-wrap">
                            <table>
                                <thead class="blue">
                                <tr>
                                    <th>Estudante</th>
                                    <th>Usuário</th>
                                    <th>Senha</th>
                                    <th>Ações</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% if (listaAlunos != null) {
                                    for (AlunoEntity aluno : listaAlunos) { %>
                                <tr>
                                    <td><%= aluno.getNome() %></td>
                                    <td><%= aluno.getUsuario() %></td>
                                    <td>●●●●●●●●</td>
                                    <td>
                                        <div class="td-actions">
                                            <button class="show"><img src="<%= context %>/img/svg/crud/eye.svg"></button>
                                            <button class="edit"><img src="<%= context %>/img/svg/crud/pencil.svg"></button>
                                            <button class="delete"><img src="<%= context %>/img/svg/crud/trash.svg"></button>
                                        </div>
                                    </td>
                                </tr>
                                <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <div class="table-container">
                        <div class="table-info">
                            <div>
                                <h3>Informações acadêmicas</h3>
                                <sub>Geral</sub>
                            </div>
                            <div class="table-actions">
                                <form action="<%= context %>/adminHome" method="get" style="display: flex;">
                                    <input class="search-box" type="text" name="materia" placeholder="Filtrar disciplina">
                                    <button type="submit" style="display: none;"></button>
                                </form>
                            </div>
                        </div>
                        <div class="table-wrap">
                            <table>
                                <thead class="green">
                                <tr>
                                    <th>Disciplina</th>
                                    <th>Matrícula</th>
                                    <th>N1'</th>
                                    <th>N2'</th>
                                    <th>Média Final</th>
                                    <th>Situação</th>
                                </tr>
                                </thead>
                                <tbody>
                                <% if (listaNotas != null) {
                                    for (NotaEntity nota : listaNotas) {
                                        double media = (nota.getN1() + nota.getN2()) / 2.0; %>
                                <tr>
                                    <td><%= nota.getNomeDisciplina() %></td>
                                    <td><%= nota.getMatriculaAluno() %></td>
                                    <td><%= String.format("%.1f", nota.getN1()) %></td>
                                    <td><%= String.format("%.1f", nota.getN2()) %></td>
                                    <td><%= String.format("%.1f", media) %></td>
                                    <td class="situation">
                                                    <span class="<%= media >= 7.0 ? "approved" : "failed" %>">
                                                        <%= media >= 7.0 ? "Aprovado" : "Reprovado" %>
                                                    </span>
                                    </td>
                                </tr>
                                <% } } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div id="observations">
                <h1>Observações</h1>
                <div class="card-container">
                    <% if (listaObservacoes != null) {
                        for (ObservacaoEntity obs : listaObservacoes) {
                            String corClass = "";
                            if (obs.getCor() != null) {
                                corClass = obs.getCor().name().toLowerCase().replace("_", "-");
                            }
                    %>
                    <div class="card <%= corClass %>">
                        <div>
                            <h2><%= obs.getTitulo() %></h2>
                            <p>Professor ID: <%= obs.getIdProfessor() %></p>
                        </div>
                        <button class="button">Ver detalhes</button>
                    </div>
                    <% } } %>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>