<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.NotaEntity" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dom Diogo</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/both.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/teacher.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<%
    String nome = (String) session.getAttribute("nome");
    @SuppressWarnings("unchecked")
    List<NotaEntity> notas = (List<NotaEntity>) request.getAttribute("notas");
    @SuppressWarnings("unchecked")
    List<ObservacaoEntity> observacoes = (List<ObservacaoEntity>) request.getAttribute("observacoes");
    ProfessorRepository professorRepository = (ProfessorRepository) request.getAttribute("professorRepository");
%>

<body id="blue-theme" class="black">
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
                    <form style="display: flex" action="${pageContext.request.contextPath}/observacao?action=findByMatriculaAluno">
                        <button type="submit">
                            <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/address-book.svg">
                            <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/address-book.svg">
                            <span>Observações</span>
                        </button>
                    </form>
                </li>
                <li class="divide">
                    <a href="#">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/user.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/user.svg">
                        <span>Meu perfil</span>
                    </a>
                </li>
                <li id="sign-out">
                    <form style="display: flex" action="${pageContext.request.contextPath}/login?action=logout" method="post">
                        <button type="submit">
                            <img class="sidebar-icon white"
                                src="${pageContext.request.contextPath}/img/svg/sidebar/white/sign-out.svg">
                            <img class="sidebar-icon black"
                                src="${pageContext.request.contextPath}/img/svg/sidebar/black/sign-out.svg">
                            <span>Sair</span>
                        </button>
                    </form>
                </li>
            </ul>
        </div>
    </aside>

    <div id="major-container">
        <div id="wrap">
            <header>
                <a href="#">
                    <div class="logo">
                        <img src="${pageContext.request.contextPath}/img/branding/icone.png" draggable="false">
                        <img src="${pageContext.request.contextPath}/img/branding/white.png" draggable="false">
                    </div>
                </a>
                <div class="personal-info">
                    <img src="${pageContext.request.contextPath}/img/neymar.png">
                    <div>
                        <h3><%=nome%></h3>
                        <p>Aluno</p>
                    </div>
                </div>
            </header>

            <main>
                <div id="front-desk">
                    <div class="castle" id="welcome">
                        <div>
                            <h2>Olá, <%=nome%></h2>
                            <p>Bem vindo de volta!</p>
                        </div>
                        <a href="#observations" class="button">Ver observações</a>
                    </div>
                    <div class="notice-board">
                        <h2>Quadro de avisos</h2>
                        <a class="button" href="${pageContext.request.contextPath}/aviso?action=aluno">Ir ao quadro</a>

                        <img style="rotate: 15deg;" class="screw right-top" src="${pageContext.request.contextPath}/img/flat/screw.png">
                        <img style="rotate: 84deg;" class="screw left-top" src="${pageContext.request.contextPath}/img/flat/screw.png">
                    </div>
                </div>

                <div id="grades">
                    <h1>Boletim</h1>
                    <div class="table-container">
                        <div class="table-info">
                            <div>
                                <h3>2° Série G Tech</h3>
                                <sub>Alunos</sub>
                            </div>
                            <div class="table-actions">
                                <input checked style="display: none;" type="checkbox" id="search-submit">
                                <label for="search-submit"><img src="${pageContext.request.contextPath}/img/svg/search.svg"></label>
                                <input class="search-box" type="text" placeholder="Pesquisar por matéria">
                                <button title="Filtrar"><img src="${pageContext.request.contextPath}/img/svg/filter.svg" alt="Filtrar"></button>
                                <button onclick="gerarBoletim()" title="Gerenciar notas" popovertarget="popup-grades">
                                    <div><img src="${pageContext.request.contextPath}/img/svg/document.svg"><span>Extrair boletim</span></div>
                                </button>
                            </div>
                        </div>
                        <div class="table-wrap">
                            <table id="report-card">
                                <thead>
                                    <tr>
                                        <th>Matéria</th>
                                        <th>N1'</th>
                                        <th>N2'</th>
                                        <th>Média Final<img class="info" title="(N1' + N2') / 2" src="${pageContext.request.contextPath}/img/svg/info-white.svg"></img></th>
                                        <th>Situação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        String situationClass = "";
                                        String situationMessage = "";
                                        for (NotaEntity nota : notas) {
                                            if (nota.getN1() == null && nota.getN2() == null) {
                                                situationClass = "";
                                                situationMessage = "Em análise";
                                            }
                                            else {
                                                situationClass = (nota.getMedia() != null && nota.getMedia() >= 7) ? "approved" : "failed";
                                                situationMessage = (nota.getMedia() != null && nota.getMedia() >= 7) ? "Aprovado" : "Reprovado";
                                            }
                                    %>
                                    <tr>
                                        <td><%= nota.getNomeDisciplina() %></td>
                                        <td><%= nota.getN1() == null ? "-" : nota.getN1() %></td>
                                        <td><%= nota.getN2() == null ? "-" : nota.getN2() %></td>
                                        <td><%= nota.getMediaCalculada()%></td>
                                        <td class="situation">
                                        <span class="<%=situationClass%>">
                                        <%=situationMessage%>
                                        </span>
                                        </td>
                                    </tr>

                                    <%
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
                        <%
                            int idPopoverObs = 0;

                            for (ObservacaoEntity obs : observacoes) {
                                idPopoverObs++;
                        %>
                        <div style="background-color: <%=obs.getCor().getHex()%>" class="card">
                            <div>
                                <h2><%=obs.getTitulo()%>
                                </h2>
                                <%
                                    String professorNome = "";
                                    if (professorRepository.findById(obs.getIdProfessor()) != null) {
                                        professorNome = professorRepository.findById(obs.getIdProfessor()).getNome();
                                %>
                                <p>realizada por <%=professorNome%></p>
                                <%
                                    }
                                %>
                            </div>
                            <button popovertarget="<%="popover-id-"+idPopoverObs%>" class="button">Ver detalhes</button>
                        </div>
                        <div id="<%="popover-id-"+idPopoverObs%>" class="popup obs" popover="auto">
                            <h1><%=obs.getTitulo()%>
                            </h1>
                            <div>
                                <div class="input-major">
                                    <div class="input-container">
                                        <p>Professor</p>
                                        <input class="text-box" type="text"
                                            value="<%=professorNome%>"
                                            readonly>
                                    </div>

                                    <div class="input-container">
                                        <p>Observação</p>
                                        <p class="content"><%=obs.getObservacao()%></p>
                                    </div>
                                </div>
                                <button class="button fat close-popover" type="button">Fechar</button>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </main>
        </div>
    </div>
</body>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.8.2/jspdf.plugin.autotable.min.js"></script>
<script src="${pageContext.request.contextPath}/js/generate-newsletter.js"></script>

</html>