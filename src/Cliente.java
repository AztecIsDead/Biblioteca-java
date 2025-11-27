public class Cliente extends Usuario implements CSVGravavel {
    private boolean status; // devendo?
    private String livroAlugado;
    private String senha;
    private boolean vip;

    public Cliente(String nome, int idade, boolean status, String livroAlugado, String senha, boolean vip) {
        super(nome,idade);
        this.status = status;
        this.livroAlugado = livroAlugado;
        this.senha = senha == null ? "" : senha;
        this.vip = vip;
    }

    public boolean getStatusDevendo(){
        return this.status;
    }

    public String getLivroAlugado(){
        return this.livroAlugado;
    }

    public void setLivroAlugado(String livroAlugado){
        this.livroAlugado = livroAlugado;
    }

    public void setStatusDevendo(boolean status){
        this.status = status;
    }

    public String getSenha() {
        return senha == null ? "" : senha;
    }

    public void setSenha(String senha) {
        this.senha = senha == null ? "" : senha;
    }

    public boolean getVip(){
        return this.vip;
    }

    public void setVip(boolean vip){
        this.vip = vip;
    }

    @Override
    public String toCSV() {
        return getNome() + "," + getIdade() + "," + getStatusDevendo() +
                "," + (getLivroAlugado() == null ? "" : getLivroAlugado()) + "," + getSenha() + getVip();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Nome,Idade,Devendo,LivroAlugado,Senha,VIP";
    }

    @Override
    public String toString() {
        return getNome() + " (" + getIdade() + ") - Livro: " +
                (getLivroAlugado() == null || getLivroAlugado().isEmpty() ? "Nenhum" : getLivroAlugado())
                + " - Devendo: " + (getStatusDevendo() ? "Sim" : "Não");
    }
}
