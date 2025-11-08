public class Funcionario extends Usuario implements CSVGravavel {
    private String cargo;

    public Funcionario(String nome, int idade, String cargo) {
        super(nome,idade);
        this.cargo = cargo;
    }

    public String getCargo(){
        return this.cargo;
    }

    @Override
    public String toString() {
        return getNome() + ", " + getIdade() + ", " + getCargo();
    }

    @Override
    public String toCSV() {
        return getNome() + "," + getIdade() + "," + cargo;
    }

    @Override
    public String getCabecalhoCSV() {
        return "Nome,Cargo,Salario";
    }



}