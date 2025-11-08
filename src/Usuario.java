
public abstract class Usuario {
    private String nome;
    private int idade;
    protected String nomeDoArquivo;

    public Usuario(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
       
    }

    public String getNome(){
        return this.nome;
    }

    public int getIdade() {
        return this.idade;
    }

}