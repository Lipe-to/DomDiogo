public class DisciplinaEntity {
    private int id;
    private String nome;
    private int id_professor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId_professor() {
        return id_professor;
    }

    public void setId_professor(int id_professor) {
        this.id_professor = id_professor;
    }

    public DisciplinaEntity(int id, String nome, int id_professor) {
        this.id = id;
        this.nome = nome;
        this.id_professor = id_professor;
    }
}