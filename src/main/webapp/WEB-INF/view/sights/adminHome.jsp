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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/both.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/teacher.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<body id="grey-theme" class="white">
<li id="menu-icon-container">
        <label id="menu-icon" for="menu-checkbox">
            <img class="sidebar-icon expand white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/menu-burger.svg">
            <img class="sidebar-icon reduce white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/reduce-menu.svg">

            <img class="sidebar-icon expand black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/menu-burger.svg">
            <img class="sidebar-icon reduce black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/reduce-menu.svg">
        </label>
        <input name="menu-checkbox" id="menu-checkbox" type="checkbox" hidden>
    </li>
    <aside id="sidebar">
        <div>
            <div class="logo">
                <img src="${pageContext.request.contextPath}/img/branding/icone.png" draggable="false">
                <img class="white" src="${pageContext.request.contextPath}/img/branding/white.png" draggable="false">
                <img style="opacity: 80%;" class="black" src="${pageContext.request.contextPath}/img/branding/black.png" draggable="false">
            </div>
            <ul>
                <li class="emphasis">
                    <a href="adminHome">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/home.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/home.svg">
                        <span>Tela Inicial</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/dashboard.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/dashboard.svg">
                        <span>Dashboard</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/address-book.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/address-book.svg">
                        <span>Professores</span>
                    </a>
                </li>
                <li class="divide">
                    <a href="#">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/user.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/user.svg">
                        <span>Meu perfil</span>
                    </a>
                </li>
                <li id="sign-out">
                    <button onclick="window.location.href='/index.jsp'">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/sign-out.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/sign-out.svg">
                        <span>Sair</span>
                    </button>
                </li>
            </ul>
        </div>
    </aside>

<div id="major-container">
    <div id="wrap">
        <header>
            <a href="#"><img class="logo" src="${pageContext.request.contextPath}/img/branding/teste.png" alt="Logo"></a>
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
                    <div>
                        <h2>Olá, administrador!</h2>
                        <p>Bem vindo de volta!</p>
                    </div>
                </div>
                <div class="general-statistic">
                    <a href="" class="h2">Visão geral <img class="redirect" src="${pageContext.request.contextPath}/img/svg/redirect-blue.svg"></a>
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

            <div class="grid-layout">
                <h1>Alunos</h1>
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
                                    <td><%= aluno.getSenha() %></td>
                                    <td>
                                        <div class="td-actions">
                                            <button class="show"><img src="${pageContext.request.contextPath}/img/svg/crud/eye.svg"></button>
                                            <button class="edit"><img src="${pageContext.request.contextPath}/img/svg/crud/pencil.svg"></button>
                                            <button class="delete"><img src="${pageContext.request.contextPath}/img/svg/crud/trash.svg"></button>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                        }
                                    }
                                %>
                                </tbody>
                            </table>
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
                    <%
                            }
                        }
                    %>
                </div>
            </div>
        </main>
    </div>
</div>
</body>
</html>