import java.util.Scanner;
public class Cliente {
    private String nome;
    private int idade;
    private boolean devendo;

    public Cliente(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;
    }

    public String getNome(){
        return this.nome;
    }

    public int getIdade(){
        return this.idade;
    }

    public String  getStatus(){
        if (devendo){
            return "Devendo";
        }
        else return "Não está devendo";
    }
}

//implementar maneira de rastrear qual(is) livro(s) o cliente esta devendo