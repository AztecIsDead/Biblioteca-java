public class Cliente extends Usuario implements CSVGravavel {
    private boolean status;

    public Cliente(String nome, int idade, boolean status) {
        super(nome,idade);
        this.status = false;
    }

    public String getStatusDevendo(){
        if (status){
            return "Devendo";
        }
        else return "Não está devendo";
    }

    public void setStatusDevendo(boolean status){
        this.status = status;
    }

    @Override
    public String toString() {
        return getNome() + ", " + getIdade() +", " + getStatusDevendo();
    }

    @Override
    public String toCSV() {
        return getNome()+ "," + getIdade() + "," + getStatusDevendo();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Nome,Idade,Status";
    }

}

//implementar maneira de rastrear qual(is) livro(s) o cliente esta devendo