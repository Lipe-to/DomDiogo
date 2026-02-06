public class ObservacaoEntity {
    private int id;
    private String observacoes;
    private int id_aluno;
    private int id_professor;

    public ObservacaoEntity( int id, String observacoes, int id_aluno, int id_professor){
        this.id = id;
        this.observacoes = observacoes;
        this.id_aluno = id_aluno;
        this.id_professor =id_professor;
    }
    public int getId() {
        return this.id;
    }
    public String getObservacoes(){
        return this.observacoes;
    }

    public int getId_aluno() {
        return this.id_aluno;
    }

    public int getId_professor() {
        return this.id_professor;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String toString() {
        return "Observacao:" +
                "\tId=" + id +
                "\tObservacoes='" + observacoes+
                "\tId_aluno=" + id_aluno +
                "\tId_professor=" + id_professor;
    }
}
