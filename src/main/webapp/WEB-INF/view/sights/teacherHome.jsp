<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AlunoNotaDTO" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.repository.AlunoRepository" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>
<%@ page import="com.domdiogo.model.TipoCount" %>
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
    int idProfessor = (int) session.getAttribute("idProfessor");
    @SuppressWarnings("unchecked")
    List<AlunoEntity> listAlunos = (List<AlunoEntity>) request.getAttribute("listAlunos");
    @SuppressWarnings("unchecked")
    List<AlunoNotaDTO> alunosNotas = (List<AlunoNotaDTO>) request.getAttribute("alunosNotas");
    @SuppressWarnings("unchecked")
    List<ObservacaoEntity> observacoes = (List<ObservacaoEntity>) request.getAttribute("observacoes");
    AlunoRepository alunoRepository = (AlunoRepository) request.getAttribute("alunoRepository");
    ProfessorRepository professorRepository = (ProfessorRepository) request.getAttribute("professorRepository");
%>

<body id="red-theme">
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
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/home-emphasis.svg"
                        >
                    <span>Tela Inicial</span>
                </a>
            </li>
            <li>
                <a href="">
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/dashboard.svg"
                        >
                    <span>Dashboard</span>
                </a>
            </li>
            <li>
                <a href="">
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/address-book.svg"
                        >
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
                <div class="profile-image"></div>
                <div>
                    <h3><%=nome%></h3>
                    <p>Professor</p>
                </div>
            </div>
        </header>

        <main>
            <div id="front-desk">
                <div class="castle" id="welcome">
                    <div>
                        <h2>Olá <%=nome%>!</h2>
                        <p>Bem vindo de volta!</p>
                    </div>
                    <a class="button">Gerenciar observações</a>
                </div>
                <div class="general-statistic">
                    <a href="" class="h2">Visão geral<img class="redirect" src="${pageContext.request.contextPath}/img/svg/redirect-blue.svg"></a>
                    <div>
                        <%
                            String apprClass = "";
                            String reprClass = "";

                            if (alunoRepository.porcentagemAlunos(TipoCount.APROVADO, idProfessor) != 0) {
                                apprClass = "appr";
                            }
                            if (alunoRepository.porcentagemAlunos(TipoCount.REPROVADO, idProfessor) != 0 ) {
                                reprClass = "repr";
                            }
                        %>
                        <div>
                            <h3><span><%=alunoRepository.countAlunos()%></span></h3>
                            <span>Total de alunos</span>
                        </div>
                        <div>
                            <h3><span><%=alunoRepository.porcentagemAlunos(TipoCount.SEM_NOTA, idProfessor)%></span> %</h3>
                            <span>Alunos sem notas</span>
                        </div>
                        <div>
                            <h3><span class="<%=apprClass%>"><%=alunoRepository.porcentagemAlunos(TipoCount.APROVADO, idProfessor)%></span> %</h3>
                            <span>Alunos aprovados</span>
                        </div>
                        <div>
                            <h3><span class="<%=reprClass%>"><%=alunoRepository.porcentagemAlunos(TipoCount.REPROVADO, idProfessor)%></span> %</h3>
                            <span>Alunos reprovados</span>
                        </div>
                    </div>
                </div>
            </div>

            <div id="grades">
                <h1>Matemática</h1>
                <div class="actions-section-container">
                    <select class="select-box">
                        <option value="">Todas as turmas</option>
                    </select>
                </div>

                <div class="table-container">
                    <div class="table-info">
                        <div>
                            <h3>Alunos</h3>
                            <sub>Informações e notas</sub>
                        </div>
                        <form class="table-actions" id="formBuscaAluno" method="post" action="${pageContext.request.contextPath}/teacherHome">
                            <input checked style="display: none;" type="checkbox" id="search-submit">
                            <label for="search-submit"><img src="${pageContext.request.contextPath}/img/svg/search.svg"></label>
                            <input class="search-box" id="alunoSearch" name="matriculaAluno" list="students-datalist-search" type="text" placeholder="Pesquisar por matrícula ou nome">
                            <datalist id="students-datalist-search">
                                <option disabled selected>Selecione um aluno</option>
                                <%
                                    if (listAlunos != null && !listAlunos.isEmpty()) {
                                        for (AlunoEntity aluno : listAlunos) {
                                %>
                                    <option value="<%=aluno.getMatricula()%>"><%=aluno.getNome()%> (Matrícula: <%=aluno.getMatricula()%>, Turma: <%=aluno.getTurma()%>)</option>
                                <%
                                        }
                                    }
                                %>
                            </datalist>

                            <button title="Filtrar" name="action" value="buscarAluno" type="submit"><img src="${pageContext.request.contextPath}/img/svg/filter.svg" alt="Filtrar"></button>
                            <button title="Filtrar" name="action" value="listarTodos" type="submit"><img src="${pageContext.request.contextPath}/img/svg/cross-small.svg" alt="Remover filtros"></button>
                            <button title="Atualizar notas" popovertarget="popup-grades"><div><img src="${pageContext.request.contextPath}/img/svg/document.svg"><span>Atualizar notas</span></div>
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
                                <th>N1'</th>
                                <th>N2'</th>
                                <th>Média Final<img class="info" title="(N1' + N2') / 2" src="${pageContext.request.contextPath}/img/svg/info-white.svg"></img></th>
                                <th>Situação</th>
                                <th>Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                int idPopoverGrades = 0;
                                String situationClass = "";
                                Boolean temNota = false;

                                for (AlunoNotaDTO item : alunosNotas) {
                                    idPopoverGrades++;
                                    temNota = item.getSituacao().equals("Sem Nota");

                                    if (item.getMedia() == null) {
                                        situationClass = "";
                                    }
                                    else {
                                        if (item.getMedia() >= 7) {
                                            situationClass = "approved";
                                        }
                                        else {
                                            situationClass = "failed";
                                        }
                                    }
                            %>
                            <tr>
                                <td><%= item.getMatricula() %></td>
                                <td><%= item.getNomeAluno() %></td>
                                <td><%= item.getTurma() == null ? "Não alocado" : item.getTurma() %></td>
                                <td><%= item.getN1() == null ? "-" : item.getN1() %></td>
                                <td><%= item.getN2() == null ? "-" : item.getN2() %></td>
                                <td class="<%= item.getSituacaoCss() %>"><%= item.getMedia() == null ? "-" : item.getMedia() %></td>
                                <td class="situation"><span class="<%=situationClass%>"><%= item.getSituacao() %></span></td>
                                <td>
                                    <div class="td-actions">
                                        <button popovertarget="popup-grades-<%=idPopoverGrades%>">
                                            <% if (!temNota) {%>
                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">
                                            <%} else {%>
                                            <img src="${pageContext.request.contextPath}/img/svg/plus.svg">
                                            <%}%>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <div id="popup-grades-<%=idPopoverGrades%>" class="popup" popover="auto">
                                <h1>Gerenciar notas</h1>
                                <form action="${pageContext.request.contextPath}/nota?action=update" method="post">
                                    <div class="input-major">
                                        <div class="input-container">
                                            <p class="required">Aluno</p>
                                            <input class="text-box" name="nomeAluno" type="text" value="<%=item.getNomeAluno()%>" readonly>
                                        </div>

                                        <div class="input-container">
                                            <p class="required">N1'</p>
                                            <input class="text-box" name=n1 type="number" value="<%=item.getN1()%>" min="0" max="10" step="0.1">
                                        </div>

                                        <div class="input-container">
                                            <p class="required">N2'</p>
                                            <input class="text-box" name="n2" type="number" value="<%=item.getN2()%>" min="0" max="10" step="0.1">
                                        </div>

                                        <div class="input-container">
                                            <p class="required">Média final</p>
                                            <input class="text-box" type="text" readonly>
                                        </div>
                                            <input class="text-box" name="id" type="text" value="<%=item.getMatricula()%>" hidden>
                                            <input class="text-box" name="idNota" type="text" value="<%=item.getNotaId()%>" hidden>
                                    </div>
                                    <button class="button fat" type="submit">Atualizar notas</button>
                                </form>
                            </div>
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
                <div class="actions-section-container">
                    <button popovertarget="popup-obs" type="button">Adicionar Observação</button>
                    <select class="select-box">
                        <option value="">Todas as turmas</option>
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
                            <p>direcionada para <%=alunoRepository.findByMatricula(obs.getMatriculaAluno()).getNome()%>
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

<div id="popup-obs" class="popup" popover="auto">
    <h1>Adicionar Observação</h1>
    <form action="${pageContext.request.contextPath}/observacao?action=create" method="post">
        <div class="input-major">
            <div class="email input-container">
                <p class="required">Aluno</p>
                <input class="text-box" name="matriculaAluno" list="students-datalist" required>
                <datalist class="text-box" id="students-datalist">
                    <option disabled selected>Selecione um aluno</option>
                    <%
                        for (AlunoEntity aluno : listAlunos) {
                    %>
                    <option value="<%=aluno.getMatricula()%>"><%=aluno.getNome()%> (<%=aluno.getTurma()%>)</option>
                    <%
                        }
                    %>
                </datalist>
            </div>

            <div class="input-container">
                <p class="required">Título</p>
                <input class="text-box" name="titulo" type="text" value="">
            </div>

            <div class="input-container">
                <p class="required">Conteúdo</p>
                <textarea class="text-box" name="observacao"></textarea>
            </div>

            <div class="input-container">
                <p class="required">Cores</p>
                <select class="text-box" name="cor">
                    <option value="BLUE">Azul</option>
                    <option value="RED">Vermelho</option>
                    <option value="TEA_BLUE">Azul-Chá</option>
                    <option value="PURPLE">Roxo</option>
                    <option value="GREEN">Verde</option>
                    <option value="LIME_GREEN">Verde-Lima</option>
                    <option value="SMOOTH_RED">Vermelho-Suave</option>
                    <option value="ORANGE">Laranja</option>
                </select>
            </div>

            <input type="hidden" name="idProfessor" value="<%=idProfessor%>">
        </div>

        <button class="button" type="submit">Registrar</button>
    </form>
</div>
</body>

<script src="${pageContext.request.contextPath}/js/popover-close.js"></script>
<script src="${pageContext.request.contextPath}/js/calculate-sum.js"></script>

</html>