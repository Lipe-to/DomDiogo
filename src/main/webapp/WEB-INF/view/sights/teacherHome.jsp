<%@ page import="com.domdiogo.model.AlunoEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domdiogo.repository.NotaRepository" %>
<%@ page import="com.domdiogo.model.NotaEntity" %>
<%@ page import="com.domdiogo.repository.AlunoRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Dom Diogo</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/both.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/sights/teacher.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/popup.css">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/variables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font.css">
</head>

<%
    AlunoRepository alunoRepository = new AlunoRepository();
    List<AlunoEntity> listAlunos = alunoRepository.read();

    String nome = (String) session.getAttribute("nome");
    int idProfessor = (int) session.getAttribute("idProfessor");
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
                    <a href="">
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
                    <div class="profile-image"></div> <!-- Condicional em JSP se não houver bd de perfil -->
                    <div>
                        <h3>Neymar Santos</h3>
                        <p>Professor</p>
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
                        <a href="" class="h2">Visão geral <img class="redirect" src="${pageContext.request.contextPath}/img/svg/redirect-blue.svg"
                                alt=""></a>
                        <div>
                            <div>
                                <h3><span>32</span></h3>
                                <span>Total de alunos</span>
                            </div>
                            <div>
                                <h3><span>12</span></h3>
                                <span>Total de turmas</span>
                            </div>
                            <div>
                                <h3><span class="appr">62</span> %</h3>
                                <span>Alunos aprovados</span>
                            </div>
                            <div>
                                <h3><span class="repr">12</span> %</h3>
                                <span>Alunos reprovados</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div id="grades">
                    <h1>Matemática</h1>
                    <select class="select-box">
                        <option value="">Todas as turmas</option>
                        <option value="">6º Ano</option>
                        <option value="">7º Ano</option>
                        <option value="">8º Ano</option>
                        <option value="">9º Ano</option>
                    </select>

                    <div class="table-container">
                        <div class="table-info"> <!-- Contenção da turma -->
                            <div>
                                <h3>Alunos</h3>
                                <sub>Informações e notas</sub>
                            </div>
                            <div class="table-actions">
                                <input checked style="display: none;" type="checkbox" id="search-submit">
                                <label for="search-submit"><img src="${pageContext.request.contextPath}/img/svg/search.svg" alt=""></label>
                                <input class="search-box" type="text" placeholder="Pesquisar por matrícula">
                                <button title="Filtrar"><img src="${pageContext.request.contextPath}/img/svg/filter.svg" alt="Filtrar"></button>
                                <button title="Atualizar notas" popovertarget="popup-grades" > <!-- popovertarget="popup-grades" -->
                                    <div><img src="${pageContext.request.contextPath}/img/svg/document.svg"><span>Atualizar notas</span></div>
                                </button>
                            </div>
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
                                        <th>Média Final<img class="info" title="(N1' + N2') / 2"
                                                src="${pageContext.request.contextPath}/img/svg/info-white.svg"></img></th>
                                        <th>Situação</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        NotaRepository notaRepository = new NotaRepository();
                                        for (AlunoEntity aluno : listAlunos) {
                                            for (NotaEntity nota : notaRepository.findByProfessor(idProfessor)) {
                                    %>
                                    <tr>
                                        <td><%=aluno.getMatricula()%></td>
                                        <td><%=aluno.getNome()%></td>
                                        <td><%=aluno.getTurma() == null ? "Não alocado" : aluno.getTurma()%></td>
                                        <td><%=nota.getN1()%></td>
                                        <td><%=nota.getN2()%></td>
                                        <td class=<%=nota.getMedia() <= 7 ? "appr" : "repr"%>><%=nota.getMedia()%></td>
                                        <td class="situation"><span <%=nota.getMedia() <= 7 ? "approved" : "reproved"%>><%=nota.getMedia() <= 7 ? "Aprovado" : "Reprovado"%></span></td>
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
                    <select class="select-box">
                        <option value="">Todas as turmas</option>
                        <option value="">1°E TECH</option>
                        <option value="">1°F TECH</option>
                        <option value="">1°G TECH</option>
                        <option value="">1°H TECH</option>
                        <option value="">1°I TECH</option>
                    </select>

                    <div class="card-container">
                        <div class="card">
                            <div>
                                <h2>Análise individual</h2>
                                <p>realizada por Daniel Alves</p>
                            </div>
                            <button class="button">Ver detalhes</button>
                        </div>

                        <div class="card blue">
                            <div>
                                <h2>Análise individual</h2>
                                <p>realizada por Daniel Alves</p>
                            </div>
                            <button class="button">Ver detalhes</button>
                        </div>
                        <div class="card green">
                            <div>
                                <h2>Análise individual</h2>
                                <p>realizada por Daniel Alves</p>
                            </div>
                            <button class="button">Ver detalhes</button>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <div id="popup-grades" class="popup" popover="auto">
        <!-- Apesar de cada table ter um símbolo de nota específico, o POPUP de notas será único -->
        <h1>Gerenciar notas</h1>
        <form action="${pageContext.request.contextPath}/nota?action=readAll" method="post">
            <div class="input-major">
                <div class="email input-container">
                    <p class="required">Aluno</p>
                    <select class="text-box" name="" id="">
                        <option value="" disabled selected>
                            Selecione um aluno
                        </option>

                        <optgroup label="1° Ano TECH">
                            <option value="">Nisflei Santos</option>
                        </optgroup>
                    </select>
                </div>

                <div class="input-container">
                    <p class="required">Matrícula</p>
                    <input class="text-box" name="" type="text" value="" readonly>
                </div>

                <div class="input-container">
                    <p class="required">N1'</p>
                    <input class="text-box" name="" type="number" min="0" max="10" step="0.1">
                </div>

                <div class="input-container">
                    <p class="required">N2'</p>
                    <input class="text-box" name="" type="number" min="0" max="10" step="0.1">
                </div>

                <div class="input-container">
                    <p class="required">Média final</p>
                    <input class="text-box" name="" type="text" value="" readonly>
                </div>
            </div>

            <button class="button" type="submit">Registrar</button>
        </form>
    </div>
</body>

</html>