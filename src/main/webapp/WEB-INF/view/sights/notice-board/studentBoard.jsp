<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AvisoEntity" %>
<%@ page import="com.domdiogo.model.ProfessorEntity" %>
<%@ page import="com.domdiogo.model.ColorPalette" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Quadro de Avisos - Aluno</title>
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/branding/favicon.png" type="image/x-icon">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sights/both.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sights/notice-board.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/variables.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font.css">
</head>

<%
    String nome = (String) session.getAttribute("nome");
    String fotoPerfil = (String) session.getAttribute("fotoPerfil");
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
                <li>
                    <a href="adminHome">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/home.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/home.svg">
                        <span>Tela Inicial</span>
                    </a>
                </li>
                <li class="emphasis">
                    <form style="display: flex" action="${pageContext.request.contextPath}/observacao?action=findByMatriculaAluno">
                        <button type="submit">
                            <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/address-book.svg">
                            <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/address-book.svg">
                            <span>Quadro de Avisos</span>
                        </button>
                    </form>
                </li>
                <li class="divide">
                    <button popovertarget="popup-profile" type="button">
                        <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/user.svg">
                        <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/user.svg">
                        <span>Meu perfil</span>
                    </button>
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
                <div class="profile-image <%=fotoPerfil%>"></div>
                <div>
                    <h3><%=nome%></h3>
                    <p>Aluno</p>
                </div>
            </div>
        </header>

        <main>

            <div id="filter-container" class="student-board-filters">
                <h1>Filtros</h1>
                <div class="filters">
                    <form action="<%=request.getContextPath()%>/aviso" method="get">
                        <input type="hidden" name="action" value="aluno">
                            <label style="cursor:pointer;">
                                <button type="submit">
                                    <img src="${pageContext.request.contextPath}/img/svg/search.svg">
                                </button>
                            </label>
                            <input class="search-box"
                                type="text"
                                name="regex"
                                placeholder="Digite parte do aviso">

                            <select name="professor" class="search-box text-box">
                                <option disabled selected>Selecione o professor</option>
                                <%
                                    List<ProfessorEntity> professores =
                                            (List<ProfessorEntity>) request.getAttribute("professores");

                                    if (professores != null) {
                                        for (ProfessorEntity p : professores) {
                                %>
                                <option value="<%=p.getId()%>">
                                    <%=p.getNome()%>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                    </form>
                </div>
            </div>

            <div id="front-desk">

                <div class="notice-board">

                    <h2>Quadro de avisos</h2>

                    <br><br>

                    <img style="rotate: 15deg;" class="screw right-top"
                         src="<%=request.getContextPath()%>/img/flat/screw.png">

                    <img style="rotate: 84deg;" class="screw left-top"
                         src="<%=request.getContextPath()%>/img/flat/screw.png">

                    <div id="postit-container">

                        <%

                            List<AvisoEntity> avisos =
                                    (List<AvisoEntity>) request.getAttribute("avisos");

                            if (avisos != null && !avisos.isEmpty()) {
                                ProfessorRepository professorRepository = new ProfessorRepository();
                                for (AvisoEntity aviso : avisos) {

                        %>

                        <div
                                class="postit"
                                style="background-color: <%=ColorPalette.valueOf(aviso.getCor()).getHex()%>;"
                        >

                            <div>

                                <img
                                        class="tape"
                                        src="<%=request.getContextPath()%>/img/shape/tape.svg"
                                >

                                <div>
                                    <sub>
                                        <%="Por "+professorRepository.findById(aviso.getIdProfessor()).getNome()+":"%>
                                    </sub>

                                    <h3>

                                        <%=aviso.getTitulo()%>

                                        <span>

                                            <%=aviso.getPrazo()==null ? "" : "Prazo: "+aviso.getPrazo()%>

                                        </span>

                                    </h3>

                                    <sub>

                                        Aviso #<%=aviso.getId()%>

                                    </sub>

                                </div>

                                <p>

                                    <%=aviso.getAviso()%>

                                </p>

                            </div>

                        </div>

                        <%

                            }

                        } else {

                        %>

                        <div class="no-obs" class="any-card">
                            <h3 style="opacity: 60%; width: 100%;">Nenhum aviso disponível</h3>
                        </div>

                        <%

                            }

                        %>

                    </div>

                </div>

            </div>
        </main>
    </div>

</div>

<div id="popup-profile" class="popup profile" popover="auto">
        <button class="popup-cross" popovertarget="popup-profile" popovertargetaction="hide" type="button">
            <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
        </button>
        <div class="personal-profile">
            <div class="<%=fotoPerfil%>"></div>
            <div>
                <h1><%=nome%></h1>
                <p>Aluno</p>
            </div>
        </div>
        <div class="input-major">
            <form style="display: flex;" action="${pageContext.request.contextPath}/login?action=alterarFoto" method="post">
                <div class="input-container">
                    <p>Foto de perfil</p>
                    <div class="avatar">
                        <div>
                            <input class="text-box dino" type="radio" name="avatar"
                                value="">
                            <img class="check-circle" src="${pageContext.request.contextPath}/img/svg/check.svg">
                         </div>
                        <div>
                            <input class="text-box diver" type="radio" name="avatar"
                                value="">
                            <img class="check-circle" src="${pageContext.request.contextPath}/img/svg/check.svg">
                        </div>
                        <div>
                            <input class="text-box diver" type="radio" name="avatar"
                                value="">
                            <img class="check-circle" src="${pageContext.request.contextPath}/img/svg/check.svg">
                        </div>
                    </div>
                </div>
                <%-- <div class="input-container">
                    <p>Tema</p>
                    <select class="text-box" name="">
                        <option value="">Azul</option>
                        <option value="">Verde</option>
                        <option value="">Vermelho</option>
                    </select>
                </div> --%>
                <div>
                    <button class="button fat" type="submit"><img class="icon-inner-button" src="${pageContext.request.contextPath}/img/svg/refresh.svg"><span>Atualizar informações</span></button>
                </div>
            </form>
            
            <form style="display:flex">
                <button class="button" type="submit">
                    <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/sign-out.svg">
                    <span>Logout</span>
                </button>
            </form>
        </div>
    </div>

</body>

</html>

