# 📋 Análise Completa do Projeto DomDiogo

## 🏗️ Visão Geral

**DomDiogo** é uma aplicação web Java (Servlet/JSP) para gerenciar pontos turísticos de São Sebastião - SP. Usa **Jakarta Servlet 5.0**, **PostgreSQL**, **Maven**, e é empacotada como WAR. A arquitetura segue um padrão **MVC simplificado** (Model → Repository → Servlet → JSP).

---

## ✅ Pontos Positivos

| Aspecto | Detalhe |
|---|---|
| **Organização** | Boa separação em pacotes: `model`, `repository`, `servlet`. Classes utilitárias (`ServletHelper`, `ConnectionFactory`) bem isoladas. |
| **Variáveis de ambiente** | Uso de `java-dotenv` para credenciais do banco — evita hardcoding de senhas. |
| **Try-with-resources** | Todos os repositórios usam `try (Connection conn = ...)`, garantindo fechamento automático da conexão. |
| **Prepared Statements** | Todas as queries usam `PreparedStatement`, protegendo contra **SQL Injection**. |
| **Páginas de erro** | `web.xml` configura tratamento de erros 404/500 com JSP dedicado. |
| **Proteção de views** | As JSPs de CRUD ficam em `WEB-INF/view/`, inacessíveis diretamente pelo browser. |
| **Verificação de sessão** | As pages protegidas (`list.jsp`, `create.jsp`, `edit.jsp`) verificam se o admin está logado. |
| **UX no frontend** | Toggle de senha, confirmação de senha, popup de erro, busca client-side, confirmação de exclusão. |
| **Newsletter** | Feature criativa de gerar HTML de newsletter a partir dos cards. |

---

## 🔴 Problemas Críticos de Segurança

### 1. Senhas armazenadas em texto puro

`AdminRepository.register()` grava password como plain text e `AdminRepository.isValidLogin()` compara password em plain text.

**Impacto:** Se o banco for comprometido, todas as senhas são expostas.

**Solução:** Usar **BCrypt** (ex: `jBCrypt` ou `Spring Security Crypto`) para hash + salt.

### 2. XSS (Cross-Site Scripting) em JSPs

Todas as JSPs usam `<%= ... %>` sem escaping:

```jsp
<%= sight.getName() %>
<%= sight.getDescription() %>
<%= error %>
<%= admin.getName() %>
```

**Impacto:** Um atacante pode injetar `<script>` via campos de formulário.

**Solução:** Usar JSTL `<c:out value="..."/>` ou `fn:escapeXml()` em vez de `<%= %>`.

### 3. Endpoint `/update-password` sem autenticação

`UpdatePasswordServlet` aceita um POST com `email` + `password` sem verificar sessão, token, ou qualquer autenticação. Qualquer pessoa pode redefinir a senha de qualquer conta.

**Solução:** Implementar token de redefinição com expiração, ou exigir verificação por e-mail.

### 4. Endpoint `/register` sem restrição

Qualquer pessoa pode criar uma conta de administrador. Não há convite, aprovação, ou limite.

**Solução:** Restringir registro a admins existentes, ou usar código de convite.

---

## 🟡 Problemas de Arquitetura e Design

### 5. Novas instâncias de `ConnectionFactory` em cada request

Cada servlet cria `new ConnectionFactory()` e cada chamada ao repository cria uma nova `Connection`. Sem **connection pool**.

```java
new AdminRepository(new ConnectionFactory()); // em cada doPost/doGet
```

**Impacto:** Performance degradada sob carga; possível esgotamento de conexões.

**Solução:** Usar um pool como **HikariCP** ou **DBCP**, e injetar uma única instância (singleton) de `ConnectionFactory`.

### 6. `Dotenv.load()` chamado a cada conexão

Dentro de `ConnectionFactory.getConnection()`, o `.env` é relido do disco em toda requisição.

**Solução:** Carregar `Dotenv` uma única vez no construtor ou como campo estático.

### 7. Ausência de validação server-side

Nenhum servlet valida os dados recebidos (nulos, tamanho, formato). Exemplos:

- `EditSightServlet`: `Integer.parseInt(request.getParameter("id"))` lança `NumberFormatException` se `id` for nulo/inválido.
- `RegisterServlet`: não valida formato de e-mail, tamanho de senha.
- `CreateSightServlet`: não valida se os campos estão preenchidos.

**Solução:** Validar todos os parâmetros antes de processá-los.

### 8. Model `Sight.date` como `String`

O campo `date` deveria ser `java.time.LocalDate`, não `String`. Isso causa problemas de validação, ordenação e formatação.

### 9. `PreparedStatement` e `ResultSet` sem fechamento explícito

Embora `Connection` use try-with-resources, os `PreparedStatement` e `ResultSet` não são fechados:

```java
PreparedStatement ps = connection.prepareStatement(SQL);
ResultSet rs = ps.executeQuery();
// ps e rs nunca são fechados
```

**Solução:** Incluir todos no try-with-resources:

```java
try (Connection c = ...; PreparedStatement ps = ...; ResultSet rs = ...) { }
```

### 10. Exceções engolidas como RuntimeException

Todo tratamento de erro é `throw new RuntimeException(e)`, perdendo contexto e dificultando debugging.

**Solução:** Criar exceptions customizadas (`DatabaseException`, `AuthenticationException`) ou usar logging com **SLF4J/Logback**.

---

## 🟡 Problemas no Frontend

### 11. Caminhos relativos frágeis nos JSPs de login

Os JSPs em `/pages/login/` usam `../../css/`, `../../img/`, que podem quebrar dependendo de como os servlets fazem forward (o browser vê a URL do servlet, não do JSP).

**Solução:** Usar `${pageContext.request.contextPath}` para caminhos absolutos.

### 12. `password.js` e `password-validation.js` têm código duplicado

`check-password.js` e `password-validation.js` fazem essencialmente a mesma coisa (toggle de confirmPassword + validação de match). A versão `password-validation.js` é mais defensiva (com `if` guards), mas ambas coexistem.

**Solução:** Unificar em um único arquivo.

### 13. `index.jsp` é placeholder

Ainda contém "Hello World" e link para `hello-servlet` que não existe.

**Solução:** Redirecionar para `/login` ou remover.

### 14. Arquivo `ddd.html` e `profile.html` não referenciados

Existem na estrutura mas nenhum servlet ou link aponta para eles. Possível lixo.

### 15. `postgresql-42.7.10.jar` na raiz do projeto

Há um JAR do PostgreSQL na raiz, mas a dependência já está no `pom.xml` (v42.7.7). Redundante e confuso.

---

## 🟡 Boas Práticas Ausentes

| Item | Situação |
|---|---|
| **Testes** | JUnit está no `pom.xml` mas **nenhum teste existe**. |
| **Logging** | Nenhum framework de log (SLF4J, Log4j). Erros vão para `RuntimeException`. |
| **CSRF Protection** | Nenhum token CSRF nos formulários. POST de delete/update pode ser forjado. |
| **Filtros de autenticação** | A verificação de sessão está duplicada em cada JSP. Deveria ser um `Filter` centralizado. |
| **DAO Pattern** | Os repositórios misturam lógica de acesso a dados com criação de objetos. Sem interface/abstração. |
| **Transações** | Nenhum controle transacional. Todas as operações são auto-commit. |
| **Paginação** | `list()` retorna TODOS os registros. Sem paginação, ordenação server-side. |
| **`.env` no repositório** | Não há `.gitignore` visível; pode estar versionando credenciais. |

---

## 📊 Resumo de Severidade

| Severidade | Qtd | Itens |
|---|---|---|
| 🔴 Crítico | 4 | Senhas plain text, XSS, reset sem auth, registro aberto |
| 🟡 Importante | 11 | Connection pool, validação, PreparedStatement leak, caminhos CSS, duplicação JS, etc. |
| 🔵 Melhoria | 6 | Testes, logging, CSRF, filtros, paginação, transações |

---

## 🎯 Prioridades de Correção Recomendadas

1. **Hash de senhas** com BCrypt
2. **Escape XSS** em todos os JSPs (migrar para JSTL `<c:out>`)
3. **Proteger `/update-password`** com token de redefinição
4. **Restringir `/register`**
5. **Connection pool** (HikariCP)
6. **Filtro de autenticação** centralizado
7. **Validação server-side** em todos os servlets
8. **Fechar PreparedStatement/ResultSet** corretamente
9. **Carregar Dotenv uma única vez**
10. **Escrever testes unitários** para os repositórios

---

O projeto tem uma base sólida e organizada, com bom uso de PreparedStatements e separação de responsabilidades. Os problemas principais estão em **segurança** (senhas, XSS, endpoints desprotegidos) e **resiliência** (validação, connection pool, tratamento de erros).

