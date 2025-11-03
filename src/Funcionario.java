public class Funcionario {
    private String nome;
    private int idade;
    private String cargo;

    public Funcionario(String nome, int idade, String cargo) {
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
    }
    public String getNome(){
        return nome;
    }
    public int getIdade(){
        return idade;
    }
    public String getCargo(){
        return cargo;
    }
    public void exibirInfo(){
        System.out.println("Nome do Funcionario : " + nome);
        System.out.println("Idade do Funcionario: " + idade);
        System.out.println("Cargo do Funcionario: " + cargo);
    }
}