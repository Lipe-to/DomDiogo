public class AdminEntity {
    private String nome;
    private int id;
    private String email;
    private String senha;
    private String palavra;

    public AdminEntity(int id, String nome, String email, String senha, String palavra){
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.palavra = palavra;
    }
    public int getId() {
        return this.id;
    }
    public String getNome() {
        return this.nome;
    }
    public String getEmail() {
        return this.email;
    }
    public String getSenha() {
        return this.senha;
    }
    public String getPalavra() {
        return this.palavra;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return "Aluno:" +
                "\tId=" + id +
                "\tNome='" + nome +
                "\tEmail='" + email +
                "\tSenha='" + senha+
                "\tPalavra='" + palavra;
    }
}
