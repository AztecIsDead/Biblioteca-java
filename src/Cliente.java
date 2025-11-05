public class Cliente extends Usuario {
    private String nome;
    private int idade;
    private boolean devendo;

    public Cliente(String nome, int idade) {
        super(nome,idade);
    }

    public String  getStatus(){
        if (devendo){
            return "Devendo";
        }
        else return "Não está devendo";
    }

    @Override
    public String toString() {
        return getNome() + ", " + getIdade() +", " + getStatus();
    }
}

//implementar maneira de rastrear qual(is) livro(s) o cliente esta devendo