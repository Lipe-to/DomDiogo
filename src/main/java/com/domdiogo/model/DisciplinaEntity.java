public class DisciplinaEntity {
    private int id;
    private String nome;
    private int id_professor;

    public DisciplinaEntity (int id, String nome, int id_professor){
        this.id = id;
        this.nome = nome;
        this.id_professor = id_professor;
    }
    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public int getId_professor() {
        return this.id_professor;
    }

    public String toString() {
        return "Disciplina{" +
                "\tId=" + id +
                "\tNome='" + nome +
                "\tId_professor=" + id_professor;
    }
}
