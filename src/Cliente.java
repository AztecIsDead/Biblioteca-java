
public class Cliente extends Usuario implements CSVGravavel {
    private String nome;
    private int idade;
    private boolean status;

    public Cliente(String nome, int idade, boolean status) {
        super(nome,idade);
        this.status = false;
    }

    public String  getStatus(){
        if (status){
            return "Devendo";
        }
        else return "Não está devendo";
    }

    @Override
    public String toString() {
        return getNome() + ", " + getIdade() +", " + getStatus();
    }

    @Override
    public String toCSV() {
        return getNome()+ "," + getIdade() + "," + getStatus();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Nome,Idade,Status";
    }

}

//implementar maneira de rastrear qual(is) livro(s) o cliente esta devendo