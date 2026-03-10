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

<body id="blue-theme">

<div id="major-container">

    <div id="wrap">

        <main>

            <div id="filter-container">

                <h1>Filtros</h1>

                <div class="filters">

                    <form action="<%=request.getContextPath()%>/aviso" method="get">

                        <input type="hidden" name="action" value="aluno"/>

                        <div>
                            <p>Pesquisa por texto:</p>

                            <input
                                    class="search-box"
                                    type="text"
                                    name="search"
                                    placeholder="Digite parte do aviso"
                            >

                        </div>

                        <br><br><br><br>

                        <div>

                            <p>Professor:</p>

                            <select name="professor" class="search-box">

                                <option value="">Selecione o professor</option>

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

                        </div>

                        <button type="submit">

                            <img src="<%=request.getContextPath()%>/img/svg/search.svg">

                        </button>

                    </form>

                </div>

            </div>

            <div id="front-desk">

                <div class="notice-board">

                    <h2>Quadro de avisos <span>Aluno</span></h2>

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
                                        <%="Por "+professorRepository.findById(aviso.getIdProfessor())+":"%>
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

</body>

</html>

