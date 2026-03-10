<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AvisoEntity" %>
<%@ page import="com.domdiogo.model.ColorPalette" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Quadro de Avisos - Professor</title>

    <link rel="shortcut icon" href="<%=request.getContextPath()%>/img/branding/favicon.png">

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sights/both.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sights/notice-board.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/variables.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/font.css">
</head>

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
        <main>

            <div id="filter-container">

                <h1>Filtros</h1>

                <div class="filters">

                    <form action="<%=request.getContextPath()%>/aviso" method="get">

                        <input type="hidden" name="action" value="professor"/>

                        <div>
                            <p>Pesquisa por texto:</p>

                            <input class="search-box"
                                   type="text"
                                   name="search"
                                   placeholder="Digite parte do aviso">

                        </div>

                        <div>

                            <p>Prazo:</p>

                            <input type="date" name="prazo" class="search-box">

                        </div>

                        <button type="submit">

                            <img src="<%=request.getContextPath()%>/img/svg/search.svg">

                        </button>

                    </form>

                </div>

            </div>


            <div id="front-desk">

                <div class="notice-board">

                    <h2>Quadro de avisos <span>Professor</span></h2>

                    <br><br>

                    <img style="rotate:15deg;" class="screw right-top"
                         src="<%=request.getContextPath()%>/img/flat/screw.png">

                    <img style="rotate:84deg;" class="screw left-top"
                         src="<%=request.getContextPath()%>/img/flat/screw.png">

                    <div id="postit-container">

                        <%

                            List<AvisoEntity> avisos =
                                    (List<AvisoEntity>) request.getAttribute("avisos");

                            if(avisos != null && !avisos.isEmpty()){

                                ProfessorRepository professorRepository = new ProfessorRepository();

                                for(AvisoEntity aviso : avisos){

                        %>

                        <div class="postit"
                             style="background-color:<%=ColorPalette.valueOf(aviso.getCor()).getHex()%>;">

                            <div>

                                <img class="tape"
                                     src="<%=request.getContextPath()%>/img/shape/tape.svg">

                                <div>

                                    <sub>
                                        <%="Por "+professorRepository.findById(aviso.getIdProfessor())+":"%>
                                    </sub>

                                    <h3>

                                        <%=aviso.getTitulo()%>

                                        <span>
<%=aviso.getPrazo()==null?"":"Prazo: "+aviso.getPrazo()%>
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

                        }else{

                        %>

                        <p style="margin:20px;font-size:18px;">
                            Nenhum aviso encontrado.
                        </p>

                        <%

                            }

                        %>

                    </div>

                </div>

            </div>

        </main>
    </div>
</div>

</body>
</html>