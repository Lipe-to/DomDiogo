public class NotaEntity {
    private int id;
    private double N1;
    private double N2;
    private double media;
    private int id_disciplina;
    private int matricula_aluno;

    public NotaEntity(int id, double N1, double N2, double media, int id_disciplina, int matricula_aluno){
        this.id = id;
        this.N1 = N1;
        this.N2 = N2;
        this.media = media;
        this.id_disciplina = id_disciplina;
        this.matricula_aluno = matricula_aluno;
    }
    public int getId() {
        return this.id;
    }

    public double getN1() {
        return this.N1;
    }

    public double getN2() {
        return this.N2;
    }

    public double getMedia() {
        return this.media;
    }

    public int getId_disciplina() {
        return this.id_disciplina;
    }

    public int getId_aluno() {
        return this.matricula_aluno;
    }

    public void setN1(double n1) {
        N1 = n1;
    }

    public void setMedia(double media) {
        this.media = media;
    }

    public void setN2(double n2) {
        N2 = n2;
    }
    public String toString() {
        return "Nota:" +
                "\tId=" + id +
                "\tN1=" + N1 +
                "\tN2=" + N2 +
                "\tMedia=" + media +
                "\tId_disciplina=" + id_disciplina +
                "\tId_aluno=" + matricula_aluno;
    }
}