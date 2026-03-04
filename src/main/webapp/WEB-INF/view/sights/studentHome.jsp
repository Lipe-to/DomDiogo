<%@ page import="com.domdiogo.repository.AlunoRepository" %>
<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.repository.NotaRepository" %>
<%@ page import="com.domdiogo.model.NotaEntity" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.repository.ObservacaoRepository" %>
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
    int matricula = (int) session.getAttribute("matricula");

    NotaRepository notaRepository = new NotaRepository();
    List<NotaEntity> notas = notaRepository.findByMatricula(matricula);
%>

<body>
    <aside id="sidebar"> <!-- Position fixed ! -->
        <ul>
            <div>
                <li id="menu-icon-container">
                    <label id="menu-icon" for="menu-checkbox">
                        <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/menu-burger.svg">
                    </label>
                    <input name="menu-checkbox" id="menu-checkbox" type="checkbox">
                </li>
                <p id="menu-text">Menu</p>
                <li class="emphasis">
                    <a href="">
                        <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/home-emphasis.svg" alt="">
                        <span>Tela Inicial</span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/address-book.svg" alt="">
                        <span>Professores</span>
                    </a>
                </li>
            </div>
            <li id="sign-out">
                <button>
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/sign-out.svg" alt="">
                    <span>Sair</span>
                </button>
            </li>
        </ul>
    </aside>

    <div id="major-container">
        <div id="wrap">
            <header>
                <a href="#"><img class="logo" src="${pageContext.request.contextPath}/img/branding/teste.png" alt="Logo"></a>
                <div class="personal-info">
                    <img src="${pageContext.request.contextPath}/img/neymar.png" alt=""> <!-- Condicional em JSP se não houver bd de perfil -->
                    <div>
                        <h3>Neymar Santos</h3>
                        <p>Aluno</p>
                    </div>
                </div>
            </header>

            <main>
                <div id="front-desk">
                    <div class="castle" id="welcome">
                        <h2>Olá <%=nome%>!</h2>
                        <p>Bem vindo de volta!</p>
                    </div>
                    <div class="general-statistic">
                        <h2>Visão geral</h2>
                        <div>
                            <div>
                                <h3><span>32</span></h3>
                                <span>Total de alunos</span>
                            </div>
                            <div>
                                <h3><span>8,1</span></h3>
                                <span>Total de turmas</span>
                            </div>
                            <div>
                                <h3><span>12</span> %</h3>
                                <span>Alunos reprovados</span>
                            </div>
                            <div>
                                <h3><span>12</span> %</h3>
                                <span>Alunos reprovados</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="grades">
                    <h1>Boletim</h1>
                    <select class="select-box">
                        <option value="">Todas as matérias</option>
                        <option value="">6º Ano</option>
                        <option value="">7º Ano</option>
                        <option value="">8º Ano</option>
                        <option value="">9º Ano</option>
                    </select>

                    <div class="table-container">
                        <div class="table-info"> <!-- Contenção da turma -->
                            <div>
                                <h3>2° Série G Tech</h3>
                                <sub>Alunos</sub>
                            </div>
                            <div class="table-actions">
                                <input checked style="display: none;" type="checkbox" id="search-submit">
                                <label for="search-submit"><img src="${pageContext.request.contextPath}/img/svg/search.svg" alt=""></label>
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
                                        for (NotaEntity nota : notas) {
                                    %>
                                    <tr>
                                        <td><%=nota.getNomeDisciplina()%></td>
                                        <td><%=nota.getN1()%></td>
                                        <td><%=nota.getN2()%></td>
                                        <td><%=nota.getMedia()%></td>
                                        <td class="situation"><span class="<%=nota.getMedia() >= 7 ? "approved" : "repproved"%>"><%=nota.getMedia() >= 7 ? "Aprovado" : "Reprovado"%></span></td>
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
                    <button popovertarget="popup-obs" type="button">Adicionar Observação</button>
                    <select class="select-box">
                        <option value="">Todas as turmas</option>
                    </select>

                    <div class="card-container">
                        <%
                            ObservacaoRepository observacaoRepository = new ObservacaoRepository();
                            ProfessorRepository professorRepository = new ProfessorRepository();
                            int idPopoverObs = 0;

                            for (ObservacaoEntity obs : observacaoRepository.findByMatriculaAluno(matricula)) {
                                idPopoverObs++;
                        %>
                        <div style="background-color: <%=obs.getCor().getHex()%>" class="card">
                            <div>
                                <h2><%=obs.getTitulo()%>
                                </h2>
                                <p>realizada por <%=professorRepository.findById(obs.getIdProfessor()).getNome()%>
                                </p>
                            </div>
                            <button popovertarget="<%="popover-id-"+idPopoverObs%>" class="button">Ver detalhes</button>
                        </div>
                        <div id="<%="popover-id-"+idPopoverObs%>" class="popup" popover="auto">
                            <h1><%=obs.getTitulo()%>
                            </h1>
                            <div>
                                <div class="input-major">
                                    <div class="input-container">
                                        <p>Professor</p>
                                        <input class="text-box" type="text"
                                            value="<%=professorRepository.findById(obs.getIdProfessor()).getNome()%>"
                                            readonly>
                                    </div>

                                    <div class="input-container">
                                        <p class="required">Observação</p>
                                        <input class="text-box" type="text" value="<%=obs.getObservacao()%>">
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

<script src="${pageContext.request.contextPath}/js/generate-newsletter.js"></script>

</html>