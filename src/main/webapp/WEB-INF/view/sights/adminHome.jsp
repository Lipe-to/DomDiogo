<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.model.AlunoNotaDTO" %>
<%@ page import="com.domdiogo.model.ObservacaoEntity" %>
<%@ page import="com.domdiogo.repository.AlunoRepository" %>
<%@ page import="com.domdiogo.repository.ProfessorRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dom Diogo - Administrador</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/branding/favicon.png" type="image/x-icon">

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
    String percApp = (String) request.getAttribute("percAprovados");
    String percRep = (String) request.getAttribute("percReprovados");

    @SuppressWarnings("unchecked")
    List<AlunoEntity> listAlunos = (List<AlunoEntity>) request.getAttribute("listAlunos");
    @SuppressWarnings("unchecked")
    List<AlunoNotaDTO> alunosNotas = (List<AlunoNotaDTO>) request.getAttribute("alunosNotas");
    @SuppressWarnings("unchecked")
    List<ObservacaoEntity> observacoes = (List<ObservacaoEntity>) request.getAttribute("observacoes");
    AlunoRepository alunoRepository = (AlunoRepository) request.getAttribute("alunoRepository");
    ProfessorRepository professorRepository = (ProfessorRepository) request.getAttribute("professorRepository");
%>

<body>
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
                <a href="${pageContext.request.contextPath}/adminHome">
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/home-emphasis.svg" alt="">
                    <span>Tela Inicial</span>
                </a>
            </li>
            <li>
                <a href="">
                    <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/dashboard.svg" alt="">
                    <span>Dashboard</span>
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
            <button onclick="window.location.href='${pageContext.request.contextPath}/index.jsp'">
                <img class="sidebar-icon" src="${pageContext.request.contextPath}/img/svg/sidebar/sign-out.svg" alt="">
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
                    <h3>Administrador</h3>
                    <p>Gestão Escolar</p>
                </div>
            </div>
        </header>

        <main>
            <!-- ===== FRONT DESK - Boas vindas + Estatísticas ===== -->
            <div id="front-desk">
                <div class="castle" id="welcome">
                    <div>
                        <h2>Olá, administrador!</h2>
                        <p>Bem vindo de volta!</p>
                    </div>
                </div>
                <div class="general-statistic">
                    <a href="" class="h2">Visão geral<img class="redirect" src="${pageContext.request.contextPath}/img/svg/redirect-blue.svg" alt=""></a>
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

            <!-- ===== SEÇÃO DE NOTAS - Tabela com AlunoNotaDTO ===== -->
            <div id="grades">
                <h1>Painel Acadêmico</h1>
                <div class="actions-section-container">
                    <form id="formBuscaAluno" method="POST" action="${pageContext.request.contextPath}/adminHome">
                        <div style="display: flex; gap: 10px; align-items: flex-end;">
                            <div style="flex: 1;">
                                <label for="alunoSearch">Buscar Aluno:</label>
                                <input class="text-box" id="alunoSearch" name="matriculaAluno" list="students-datalist-search" placeholder="Digite matrícula ou nome...">
                                <datalist id="students-datalist-search">
                                    <option disabled selected>Selecione um aluno</option>
                                    <%
                                        if (listAlunos != null && !listAlunos.isEmpty()) {
                                            for (AlunoEntity aluno : listAlunos) {
                                    %>
                                    <option value="<%= aluno.getMatricula() %>"><%= aluno.getNome() %> (Matrícula: <%= aluno.getMatricula() %>, Turma: <%= aluno.getTurma() %>)</option>
                                    <%
                                            }
                                        }
                                    %>
                                </datalist>
                            </div>
                            <button class="button" type="submit" name="action" value="buscarAluno">Buscar</button>
                            <button class="button" type="submit" name="action" value="listarTodos">Listar Todos</button>
                        </div>
                    </form>
                    <form method="POST" action="${pageContext.request.contextPath}/adminHome" style="display: flex; gap: 10px; align-items: flex-end;">
                        <input class="text-box" type="text" name="disciplina" placeholder="Filtrar disciplina...">
                        <button class="button" type="submit">Filtrar</button>
                    </form>
                </div>
            </div>

                <div class="table-container">
                    <div class="table-info">
                        <div>
                            <h3>Alunos</h3>
                            <sub>Informações e notas — Todas as disciplinas</sub>
                        </div>
                        <div class="table-actions">
                            <input checked style="display: none;" type="checkbox" id="search-submit">
                            <label for="search-submit"><img src="${pageContext.request.contextPath}/img/svg/search.svg" alt=""></label>
                            <input class="search-box" type="text" id="searchTable" placeholder="Pesquisar na tabela..." oninput="filtrarTabela()">
                            <button title="Filtrar"><img src="${pageContext.request.contextPath}/img/svg/filter.svg" alt="Filtrar"></button>
                        </div>
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
                                <th>Média Final<img class="info" title="(N1' + N2') / 2" src="${pageContext.request.contextPath}/img/svg/info-white.svg"></th>
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
                                <td><%= item.getMatricula() %></td>
                                <td><%= item.getNomeAluno() %></td>
                                <td><%= item.getTurma() == null ? "Não alocado" : item.getTurma() %></td>
                                <td><%= item.getDisciplinaNome() == null ? "-" : item.getDisciplinaNome() %></td>
                                <td><%= item.getN1() == null ? "-" : item.getN1() %></td>
                                <td><%= item.getN2() == null ? "-" : item.getN2() %></td>
                                <td class="<%= item.getSituacaoCss() %>"><%= item.getMedia() == null ? "-" : item.getMedia() %></td>
                                <td class="situation"><span class="<%= situationClass %>"><%= item.getSituacao() %></span></td>
                                <td>
                                    <div class="td-actions">
                                        <button popovertarget="popup-grades-<%= idPopoverGrades %>">
                                            <% if (!semNota) { %>
                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">
                                            <% } else { %>
                                            <img src="${pageContext.request.contextPath}/img/svg/plus.svg">
                                            <% } %>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                            <div id="popup-grades-<%= idPopoverGrades %>" class="popup" popover="auto">
                                <h1>Gerenciar notas</h1>
                                <form action="${pageContext.request.contextPath}/nota?action=update" method="post">
                                    <div class="input-major">
                                        <div class="input-container">
                                            <p class="required">Aluno</p>
                                            <input class="text-box" name="nomeAluno" type="text" value="<%= item.getNomeAluno() %>" readonly>
                                        </div>

                                        <div class="input-container">
                                            <p>Disciplina</p>
                                            <input class="text-box" type="text" value="<%= item.getDisciplinaNome() == null ? "-" : item.getDisciplinaNome() %>" readonly>
                                        </div>

                                        <div class="input-container">
                                            <p class="required">N1'</p>
                                            <input class="text-box" name="n1" type="number" value="<%= item.getN1() %>" min="0" max="10" step="0.1">
                                        </div>

                                        <div class="input-container">
                                            <p class="required">N2'</p>
                                            <input class="text-box" name="n2" type="number" value="<%= item.getN2() %>" min="0" max="10" step="0.1">
                                        </div>

                                        <div class="input-container">
                                            <p class="required">Média final</p>
                                            <input class="text-box" type="text" value="<%= item.getMedia() == null ? "-" : item.getMedia() %>" readonly>
                                        </div>
                                        <input class="text-box" name="id" type="text" value="<%= item.getMatricula() %>" hidden>
                                        <input class="text-box" name="idNota" type="text" value="<%= item.getNotaId() %>" hidden>
                                    </div>
                                    <button class="button fat" type="submit">Atualizar notas</button>
                                </form>
                            </div>
                            <%
                                    }
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- ===== SEÇÃO DE INFORMAÇÕES PESSOAIS DOS ALUNOS ===== -->
            <div id="grades">
                <h1>Dados dos Alunos</h1>
                <div class="table-container">
                    <div class="table-info">
                        <div>
                            <h3>Informações pessoais</h3>
                            <sub>Dados sensíveis — Somente administradores</sub>
                        </div>
                    </div>
                    <div class="table-wrap">
                        <table>
                            <thead class="green">
                            <tr>
                                <th>Matrícula</th>
                                <th>Estudante</th>
                                <th>Usuário</th>
                                <th>Turma</th>
                                <th>Senha</th>
                                <th>Ações</th>
                            </tr>
                            </thead>
                            <tbody>
                            <%
                                if (listAlunos != null) {
                                    int idPopoverAluno = 0;
                                    for (AlunoEntity aluno : listAlunos) {
                                        idPopoverAluno++;
                            %>
                            <tr>
                                <td><%= aluno.getMatricula() %></td>
                                <td><%= aluno.getNome() %></td>
                                <td><%= aluno.getUsuario() %></td>
                                <td><%= aluno.getTurma() == null ? "Não alocado" : aluno.getTurma() %></td>
                                <td>●●●●●●●●</td>
                                <td>
                                    <div class="td-actions">
                                        <button popovertarget="popup-aluno-<%= idPopoverAluno %>" title="Ver / Editar">
                                            <img src="${pageContext.request.contextPath}/img/svg/crud/pencil-black.svg">
                                        </button>
                                        <form action="${pageContext.request.contextPath}/aluno?action=delete" method="post" style="display:inline;">
                                            <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
                                            <button type="submit" class="delete" title="Excluir" onclick="return confirm('Tem certeza que deseja excluir este aluno?');">
                                                <img src="${pageContext.request.contextPath}/img/svg/crud/trash.svg">
                                            </button>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            <div id="popup-aluno-<%= idPopoverAluno %>" class="popup" popover="auto">
                                <h1>Editar Aluno</h1>
                                <form action="${pageContext.request.contextPath}/aluno?action=update" method="post">
                                    <div class="input-major">
                                        <div class="input-container">
                                            <p>Matrícula</p>
                                            <input class="text-box" type="text" value="<%= aluno.getMatricula() %>" readonly>
                                            <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
                                        </div>
                                        <div class="input-container">
                                            <p class="required">Nome</p>
                                            <input class="text-box" name="nome" type="text" value="<%= aluno.getNome() %>">
                                        </div>
                                        <div class="input-container">
                                            <p class="required">Usuário</p>
                                            <input class="text-box" name="usuario" type="text" value="<%= aluno.getUsuario() %>">
                                        </div>
                                        <div class="input-container">
                                            <p>Senha</p>
                                            <input class="text-box" name="senha" type="password" value="" placeholder="Nova senha (vazio = manter atual)">
                                        </div>
                                        <div class="input-container">
                                            <p class="required">Palavra-chave</p>
                                            <input class="text-box" name="palavra" type="text" value="<%= aluno.getPalavra() %>">
                                        </div>
                                        <div class="input-container">
                                            <p class="required">Turma</p>
                                            <input class="text-box" name="turma" type="text" value="<%= aluno.getTurma() != null ? aluno.getTurma() : "" %>">
                                        </div>
                                    </div>
                                    <button class="button fat" type="submit">Salvar alterações</button>
                                </form>
                            </div>
                            <%
                                    }
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>

            <!-- ===== SEÇÃO DE OBSERVAÇÕES ===== -->
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

                        if (observacoes != null) {
                            for (ObservacaoEntity obs : observacoes) {
                                idPopoverObs++;
                                AlunoEntity alunoObs = alunoRepository.findByMatricula(obs.getMatriculaAluno());
                                String nomeAluno = (alunoObs != null) ? alunoObs.getNome() : "Aluno não encontrado";
                                String nomeProfessor = "Desconhecido";
                                try {
                                    nomeProfessor = professorRepository.findById(obs.getIdProfessor()).getNome();
                                } catch (Exception e) {
                                    // professor não encontrado
                                }
                    %>
                    <div style="background-color: <%= obs.getCor().getHex() %>" class="card">
                        <div>
                            <h2><%= obs.getTitulo() %></h2>
                            <p>direcionada para <%= nomeAluno %></p>
                            <p><small>por Prof. <%= nomeProfessor %></small></p>
                        </div>
                        <button popovertarget="<%= "popover-obs-" + idPopoverObs %>" class="button">Ver detalhes</button>
                    </div>
                    <div id="<%= "popover-obs-" + idPopoverObs %>" class="popup" popover="auto">
                        <h1><%= obs.getTitulo() %></h1>
                        <div>
                            <div class="input-major">
                                <div class="input-container">
                                    <p>Professor</p>
                                    <input class="text-box" type="text" value="<%= nomeProfessor %>" readonly>
                                </div>

                                <div class="input-container">
                                    <p>Aluno</p>
                                    <input class="text-box" type="text" value="<%= nomeAluno %>" readonly>
                                </div>

                                <div class="input-container">
                                    <p>Observação</p>
                                    <input class="text-box" type="text" value="<%= obs.getObservacao() %>" readonly>
                                </div>
                            </div>
                            <div style="display: flex; gap: 10px;">
                                <form action="${pageContext.request.contextPath}/observacao?action=delete" method="post" style="flex:1;">
                                    <input type="hidden" name="id" value="<%= obs.getId() %>">
                                    <button class="button fat" type="submit" style="background-color: #D13E3E;" onclick="return confirm('Excluir esta observação?');">Excluir</button>
                                </form>
                                <button class="button fat close-popover" type="button" style="flex:1;">Fechar</button>
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

<!-- ===== POPUP: Adicionar Observação (Admin pode escolher professor) ===== -->
<div id="popup-obs" class="popup" popover="auto">
    <h1>Adicionar Observação</h1>
    <form action="${pageContext.request.contextPath}/observacao?action=create" method="post">
        <div class="input-major">
            <div class="input-container">
                <p class="required">Aluno</p>
                <input class="text-box" name="matriculaAluno" list="students-datalist" required>
                <datalist id="students-datalist">
                    <option disabled selected>Selecione um aluno</option>
                    <%
                        if (listAlunos != null) {
                            for (AlunoEntity aluno : listAlunos) {
                    %>
                    <option value="<%= aluno.getMatricula() %>"><%= aluno.getNome() %> (<%= aluno.getTurma() %>)</option>
                    <%
                            }
                        }
                    %>
                </datalist>
            </div>

            <div class="input-container">
                <p class="required">Professor responsável (ID)</p>
                <input class="text-box" name="idProfessor" type="number" placeholder="ID do professor" required>
            </div>

            <div class="input-container">
                <p class="required">Título</p>
                <input class="text-box" name="titulo" type="text" value="">
            </div>

            <div class="input-container">
                <p class="required">Conteúdo</p>
                <textarea class="text-box" name="observacao" cols="30" rows="10"></textarea>
            </div>

            <div class="input-container">
                <p class="required">Cor</p>
                <select name="cor">
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
        </div>

        <button class="button" type="submit">Registrar</button>
    </form>
</div>

</body>

<script src="${pageContext.request.contextPath}/js/popover-close.js"></script>
<script>
    function filtrarTabela() {
        const filtro = document.getElementById('searchTable').value.toLowerCase();
        const linhas = document.querySelectorAll('#report-card tbody tr');
        linhas.forEach(function(linha) {
            const texto = linha.textContent.toLowerCase();
            linha.style.display = texto.includes(filtro) ? '' : 'none';
        });
    }
</script>

</html>

