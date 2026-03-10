<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AlunoNotaDTO" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.repository.AlunoRepository" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>
<%@ page import="com.domdiogo.model.AptoEntity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dom Diogo - Administrador</title>

    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/both.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/teacher.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">

</head>

<%

    Integer totalAlunos = (Integer) request.getAttribute("totalAlunos");
    Integer totalTurmas = (Integer) request.getAttribute("totalTurmas");

    List<AlunoEntity> listAlunos = (List<AlunoEntity>) request.getAttribute("listAlunos");
    List<AptoEntity> listAptos = (List<AptoEntity>) request.getAttribute("listAptos");
    List<AlunoNotaDTO> alunosNotas = (List<AlunoNotaDTO>) request.getAttribute("alunosNotas");
    List<ObservacaoEntity> observacoes = (List<ObservacaoEntity>) request.getAttribute("observacoes");

    AlunoRepository alunoRepository = (AlunoRepository) request.getAttribute("alunoRepository");
    ProfessorRepository professorRepository = (ProfessorRepository) request.getAttribute("professorRepository");

%>

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
                    <form style="display: flex" action="${pageContext.request.contextPath}/aviso?action=admin" method="get">
                        <input type="hidden" name="action" value="admin">
                        <button type="submit">
                            <img class="sidebar-icon white" src="${pageContext.request.contextPath}/img/svg/sidebar/white/address-book.svg">
                            <img class="sidebar-icon black" src="${pageContext.request.contextPath}/img/svg/sidebar/black/address-book.svg">
                            <span>Quadro de Avisos</span>
                        </button>
                    </form>
                </li>
                <li style="margin-top: auto;" id="sign-out">
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
                    <img src="${pageContext.request.contextPath}/img/branding/icone.png">
                    <img src="${pageContext.request.contextPath}/img/branding/white.png">
                </div>
            </a>

        </header>

        <main>
            <div id="front-desk">
                <div class="admin" id="welcome">
                    <div>
                        <h2>Olá administrador!</h2>
                        <p>Bem vindo de volta!</p>
                    </div>
                </div>
                <div class="general-statistic">
                    <h2>Visão geral</h2>
                    <div>
                        <div>
                            <h3><span><%= totalAlunos != null ? totalAlunos : 0 %></span></h3>
                            <span>Total de alunos</span>
                        </div>

                        <div>
                            <h3><span><%= totalTurmas != null ? totalTurmas : 0 %></span></h3>
                            <span>Total de turmas</span>
                        </div>

                    </div>
                </div>
            </div>
            <div id="grades">
                <h1>Painel Acadêmico</h1>
                <div class="table-container">

                    <div class="table-info">
                        <div>
                            <h3>Notas</h3>
                            <sub>Todas as disciplinas</sub>
                        </div>


                        <form class="table-actions" id="formBuscaAluno" method="post"
                              action="${pageContext.request.contextPath}/adminHome">

                            <input checked style="display: none;" type="checkbox" id="search-submit">

                            <label for="search-submit">
                                <img src="${pageContext.request.contextPath}/img/svg/search.svg">
                            </label>

                            <input class="search-box"
                                   id="alunoSearch"
                                   name="matriculaAluno"
                                   list="students-datalist-search"
                                   type="text"
                                   placeholder="Pesquisar aluno">

                            <datalist id="students-datalist-search">

                                <option disabled selected>Selecione um aluno</option>

                                <%
                                    if (listAlunos != null) {
                                        for (AlunoEntity aluno : listAlunos) {
                                %>

                                <option value="<%=aluno.getMatricula()%>">
                                    <%=aluno.getNome()%>
                                    (Matrícula: <%=aluno.getMatricula()%>)
                                </option>

                                <%
                                        }
                                    }
                                %>

                            </datalist>

                            <button title="Filtrar" name="action" value="buscarAluno" type="submit">
                                <img src="${pageContext.request.contextPath}/img/svg/filter.svg" alt="Filtrar">
                            </button>

                            <button style="width: auto; min-width: 0;" title="Remover filtros" name="action" value="listarTodos" type="submit">
                                <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg"
                                     alt="Remover filtros">
                            </button>

                        </form>


                    </div>


                    <div class="table-wrap">

                        <table id="report-card">

                            <thead class="blue">

                            <tr>
                                <th>Matrícula</th>
                                <th>Estudante</th>
                                <th>Turma</th>
                                <th>Disciplina</th>
                                <th>N1'</th>
                                <th>N2'</th>
                                <th>Média</th>
                                <th>Situação</th>
                                <th>Ações</th>
                            </tr>

                            </thead>

                            <tbody>

                            <%

                                int idPopoverGrades = 0;
                                String situationClass = "";

                                if (alunosNotas != null) {

                                    for (AlunoNotaDTO item : alunosNotas) {

                                        idPopoverGrades++;

                                        boolean semNota = item.getSituacao().equals("Sem Nota");

                                        if (item.getMedia() == null) {

                                            situationClass = "";

                                        } else if (item.getMedia() >= 7) {

                                            situationClass = "approved";

                                        } else {

                                            situationClass = "failed";

                                        }

                            %>

                            <tr>

                                <td><%=item.getMatricula()%>
                                </td>

                                <td><%=item.getNomeAluno()%>
                                </td>

                                <td><%=item.getTurma() == null ? "Não alocado" : item.getTurma()%>
                                </td>

                                <td><%=item.getDisciplinaNome()%>
                                </td>

                                <td><%=item.getN1() == null ? "-" : item.getN1()%>
                                </td>

                                <td><%=item.getN2() == null ? "-" : item.getN2()%>
                                </td>

                                <td class="<%=item.getSituacaoCss()%>">
                                    <%=item.getMedia() == null ? "-" : item.getMedia()%>
                                </td>

                                <td class="situation">
                                    <span class="<%=situationClass%>"><%=item.getSituacao()%></span>
                                </td>

                                <td>

                                    <div class="td-actions">

                                        <button popovertarget="popup-grades-<%=idPopoverGrades%>">

                                            <% if (!semNota) { %>

                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">

                                            <% } else { %>

                                            <img src="${pageContext.request.contextPath}/img/svg/plus.svg">

                                            <% } %>

                                        </button>

                                    </div>

                                </td>

                            </tr>

                            <%
                                    }
                                }
                            %>

                            </tbody>
                        </table>


                        <%

                            int idPopoverGradesPopup = 0;

                            if (alunosNotas != null) {

                                for (AlunoNotaDTO item : alunosNotas) {

                                    idPopoverGradesPopup++;

                        %>

                        <div id="popup-grades-<%=idPopoverGradesPopup%>" class="popup" popover="auto">
                            <button class="popup-cross" popovertarget="popup-grades-<%=idPopoverGradesPopup%>" popovertargetaction="hide" type="button">
                                <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
                            </button>
                            <h1>Editar Nota</h1>

                            <form action="${pageContext.request.contextPath}/nota?action=update" method="post">

                                <div class="input-major">

                                    <div class="input-container">
                                        <p>Aluno</p>
                                        <input class="text-box" type="text" value="<%=item.getNomeAluno()%>" readonly>
                                    </div>

                                    <div class="input-container">
                                        <p>Disciplina</p>
                                        <input class="text-box" type="text" value="<%=item.getDisciplinaNome()%>"
                                               readonly>
                                    </div>

                                    <div class="input-container">
                                        <p>N1</p>
                                        <input class="text-box" name="n1" type="number" value="<%=item.getN1()%>"
                                               min="0" max="10">
                                    </div>

                                    <div class="input-container">
                                        <p>N2</p>
                                        <input class="text-box" name="n2" type="number" value="<%=item.getN2()%>"
                                               min="0" max="10">
                                    </div>

                                    <input type="hidden" name="idNota" value="<%=item.getNotaId()%>">
                                    <input type="hidden" name="id" value="<%=item.getMatricula()%>">

                                </div>

                                <button class="button fat" type="submit">Salvar</button>

                            </form>

                        </div>

                        <%
                                }
                            }
                        %>

                    </div>
                </div>
            </div>

            <div id="students">

                <h1>Dados dos Alunos</h1>

                <div class="table-container">

                    <div class="table-info">
                        <div>
                            <h3>Informações pessoais</h3>
                            <sub>Área administrativa</sub>
                        </div>
                    </div>


                    <div class="table-wrap">

                        <table id="students-info">

                            <thead class="green">

                            <tr>
                                <th>Matrícula</th>
                                <th>Nome</th>
                                <th>Usuário</th>
                                <th>Turma</th>
                                <th>Ações</th>
                            </tr>

                            </thead>

                            <tbody>

                            <%

                                int idPopoverAluno = 0;

                                if (listAlunos != null) {

                                    for (AlunoEntity aluno : listAlunos) {

                                        idPopoverAluno++;

                            %>

                            <tr>

                                <td><%=aluno.getMatricula()%>
                                </td>

                                <td><%=aluno.getNome()%>
                                </td>

                                <td><%=aluno.getUsuario()%>
                                </td>

                                <td><%=aluno.getTurma() == null ? "Não alocado" : aluno.getTurma()%>
                                </td>

                                <td>

                                    <div class="td-actions">

                                        <button popovertarget="popup-aluno-<%=idPopoverAluno%>">
                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">
                                        </button>

                                        <div>
                                            <button popovertarget="popup-exclude-conf-<%=idPopoverAluno%>" class="delete">
                                                <img src="${pageContext.request.contextPath}/img/svg/crud/trash.svg">
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <div id="popup-exclude-conf-<%=idPopoverAluno%>" class="popup exclude" popover="auto">
                                <button class="popup-cross" popovertarget="popup-exclude-conf-<%=idPopoverAluno%>" popovertargetaction="hide" type="button">
                                    <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
                                </button>
                                <h1>Excluir Aluno</h1>
                                <p>Você tem certeza que deseja excluir <%=aluno.getNome()%> do sistema? Essa ação não pode ser desfeita.</p>
                                <div>
                                    <button class="button fat" popovertarget="popup-exclude-conf-<%=idPopoverAluno%>" popovertargetaction="hide" type="button">Cancelar</button>
                                    <form style="display: flex;" action="${pageContext.request.contextPath}/aluno?action=delete" method="post">
                                        <input type="hidden" name="matricula" value="<%=aluno.getMatricula()%>">
                                        <button class="button fat confirm" type="submit">Excluir</button>
                                    </form>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>

                            </tbody>
                        </table>


                        <%

                            int idPopupAluno = 0;

                            if (listAlunos != null) {

                                for (AlunoEntity aluno : listAlunos) {

                                    idPopupAluno++;

                        %>

                        <div id="popup-aluno-<%=idPopupAluno%>" class="popup" popover="auto">
                            <button class="popup-cross" popovertarget="popup-aluno-<%=idPopupAluno%>" popovertargetaction="hide" type="button">
                                <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
                            </button>
                            <h1>Editar Aluno</h1>
                            <form action="${pageContext.request.contextPath}/aluno?action=update" method="post">

                                <div class="input-major">

                                    <div class="input-container">
                                        <p>Nome</p>
                                        <input class="text-box" name="nome" value="<%=aluno.getNome()%>">
                                    </div>

                                    <div class="input-container">
                                        <p>Usuário</p>
                                        <input class="text-box" name="usuario" value="<%=aluno.getUsuario()%>">
                                    </div>

                                    <div class="input-container">
                                        <p>Turma</p>
                                        <input class="text-box" name="turma" value="<%=aluno.getTurma()%>">
                                    </div>

                                    <input type="hidden" name="matricula" value="<%=aluno.getMatricula()%>">

                                </div>

                                <button class="button fat">Salvar</button>

                            </form>

                        </div>

                        <%
                                }
                            }
                        %>

                    </div>
                </div>
            </div>

            <div id="fit-in">

                <h1>Aptos à Registro</h1>

                <div class="table-container">

                    <div class="table-info">
                        <div>
                            <h3>Informações pessoais</h3>
                            <sub>Área administrativa</sub>
                        </div>
                    </div>


                    <div class="table-wrap">

                        <table id="fit-in-info">

                            <thead class="orange">

                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Usuário</th>
                                <th>É matriculado?</th>
                                <th>Ações</th>
                            </tr>

                            </thead>

                            <tbody>

                            <%

                                int idPopoverApto = 0;
                                if (listAptos != null) {
                                    for (AptoEntity apto : listAptos) {
                                        idPopoverApto++;
                            %>

                            <tr>

                                <td><%=apto.getId()%>
                                </td>

                                <td><%=apto.getNome()%>
                                </td>

                                <td><%=apto.getUsuario()%>
                                </td>

                                <td><%=apto.isMatriculado() ? "Sim" : "Não"%>
                                <td>

                                    <div class="td-actions">

                                        <button popovertarget="popup-apto-<%=idPopoverApto%>">
                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">
                                        </button>

                                        <div>
                                            <button popovertarget="popup-exclude-apto-<%=idPopoverApto%>" class="delete">
                                                <img src="${pageContext.request.contextPath}/img/svg/crud/trash.svg">
                                            </button>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <div id="popup-exclude-apto-<%=idPopoverApto%>" class="popup exclude" popover="auto">
                                <button class="popup-cross" popovertarget="popup-exclude-apto-<%=idPopoverApto%>" popovertargetaction="hide" type="button">
                                    <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
                                </button>
                                <h1>Excluir Apto</h1>
                                <p>Você tem certeza que deseja excluir <%=apto.getNome()%> do sistema? Essa ação não pode ser desfeita.</p>
                                <div>
                                    <button class="button fat" popovertarget="popup-exclude-apto-<%=idPopoverApto%>" popovertargetaction="hide" type="button">Cancelar</button>
                                    <form style="display: flex;" action="${pageContext.request.contextPath}/apto?action=delete" method="post">
                                        <input type="hidden" name="id" value="<%=apto.getId()%>">
                                        <button class="button fat confirm" type="submit">Excluir</button>
                                    </form>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>

                            </tbody>
                        </table>


                        <%

                            int idPopupApto = 0;

                            if (listAptos != null) {

                                for (AptoEntity apto : listAptos) {

                                    idPopupApto++;

                        %>

                        <div id="popup-apto-<%=idPopupApto%>" class="popup" popover="auto">
                            <button class="popup-cross" popovertarget="popup-apto-<%=idPopupApto%>" popovertargetaction="hide" type="button">
                                <img src="${pageContext.request.contextPath}/img/svg/cross-small.svg">
                            </button>
                            <h1>Editar Apto</h1>
                            <form action="${pageContext.request.contextPath}/apto?action=update" method="post">

                                <div class="input-major">

                                    <div class="input-container">
                                        <p>Nome</p>
                                        <input class="text-box" name="nome" value="<%=apto.getNome()%>">
                                    </div>

                                    <div class="input-container">
                                        <p>Usuário</p>
                                        <input class="text-box" name="usuario" value="<%=apto.getUsuario()%>">
                                    </div>

                                    <input type="hidden" name="id" value="<%=apto.getId()%>">
                                </div>

                                <button class="button fat">Salvar</button>

                            </form>

                        </div>

                        <%
                                }
                            }
                        %>

                    </div>
                </div>
            </div>


            <!-- OBSERVAÇÕES -->

            <div id="observations">

                <h1>Observações</h1>

                <div class="actions-section-container">
                </div>

                <div class="card-container">

                    <%

                        int idPopoverObs = 0;

                        if (observacoes != null) {

                            for (ObservacaoEntity obs : observacoes) {

                                idPopoverObs++;

                                AlunoEntity alunoObs = alunoRepository.findByMatricula(obs.getMatriculaAluno());

                                String nomeAluno = alunoObs != null ? alunoObs.getNome() : "Aluno";

                                String nomeProfessor = professorRepository.findById(obs.getIdProfessor()).getNome();
                    %>

                    <div style="background-color:<%=obs.getCor().getHex()%>" class="card">
                        <div>
                            <h2><%=obs.getTitulo()%></h2>
                            <p>Para <%=nomeAluno%></p>
                        </div>

                        <button popovertarget="popover-obs-<%=idPopoverObs%>" class="button">Ver detalhes</button>
                    </div>

                    <div id="popover-obs-<%=idPopoverObs%>" style="--color-obs-card:<%=obs.getCor().getHex()%>;" class="popup obs" popover="auto">
                        <h1><%=obs.getTitulo()%>
                        </h1>
                        <div>
                            <div class="input-major">
                                <div class="input-container">
                                    <p>Professor</p>
                                    <input class="text-box" type="text"
                                           value="<%=nomeProfessor%>"
                                           readonly>
                                </div>

                                <div class="input-container">
                                    <p>Observação:</p>
                                    <p class="content"><%=obs.getObservacao()%></p>
                                </div>
                            </div>
                            <div>
                                <button class="button fat" popovertarget="popover-obs-<%=idPopoverObs%>" popovertargetaction="hide" type="button">Fechar</button>
                                <form style="display: flex;" action="${pageContext.request.contextPath}/observacao?action=delete" method="post">
                                    <input type="hidden" name="id" value="<%=obs.getId()%>">
                                    <button class="button" type="submit">
                                        <img src="${pageContext.request.contextPath}/img/svg/trash.svg">
                                    </button>
                                </form>
                            </div>
                        </div>
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


<script src="${pageContext.request.contextPath}/js/popover-close.js"></script>

</body>

</html>