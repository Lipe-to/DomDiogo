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

<body id="blue-theme">
    <aside id="sidebar">
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
                        <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/home-emphasis.svg">
                        <span>Tela Inicial</span>
                    </a>
                </li>
                <li>
                    <a href="">
                        <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/address-book.svg">
                        <span>Professores</span>
                    </a>
                </li>
            </div>
            <li id="sign-out">
                <button>
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/sign-out.svg">
                    <span>Sair</span>
                </button>
            </li>
        </ul>
    </aside>

    <div id="major-container">
        <div id="wrap">
            <header>
                <a href="#">
                    <div class="logo">
                        <img src="${pageContext.request.contextPath}/img/branding/icone.png">
                        <img src="${pageContext.request.contextPath}/img/branding/white.png">
                    </div>
                </a>
                <div class="personal-info">
                    <img src="${pageContext.request.contextPath}/img/neymar.png">
                    <div>
                        <h3>Neymar Santos</h3>
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
                        <a class="button">Ir ao quadro</a>
                    </div>
                    <div class="notice-board">
                        <h2>Quadro de avisos</h2>
                        <a class="button">Ir ao quadro</a>

                        <img style="rotate: 15deg;" class="screw right-top" src="img/flat/screw.png">
                        <img style="rotate: 84deg;" class="screw left-top" src="img/flat/screw.png">
                    </div>
                </div>

                <div id="observations">
                    <h1>Observações</h1>
                    <div class="actions-section-container">
                        <select class="select-box">
                            <option value="">Todos os professores</option>
                        </select>
                    </div>

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
                        <div id="<%="popover-id-"+idPopoverObs%>" class="popup" popover="auto">
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

</html>