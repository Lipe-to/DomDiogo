public class AdminEntity {
    private String nome;
    private int id;
    private String email;
    private String senha;
    private String palavra;

    public AdminEntity(String nome, int id, String email, String senha, String palavra) {
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.palavra = palavra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }
}
