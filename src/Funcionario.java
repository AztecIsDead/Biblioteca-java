public class Funcionario extends Usuario {
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
}