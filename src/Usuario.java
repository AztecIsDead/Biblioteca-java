public abstract class Usuario {
    private String nome;
    private int idade;

    public Usuario(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome(){
        return this.nome;
    }

    public String getIdade() {
        return this.idade + " anos";
    }
}