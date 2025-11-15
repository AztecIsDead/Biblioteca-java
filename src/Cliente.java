public class Cliente extends Usuario implements CSVGravavel {
    private boolean status;
    private String livroAlugado;

    public Cliente(String nome, int idade, boolean status, String livroAlugado) {
        super(nome,idade);
        this.status = status;
        this.livroAlugado = livroAlugado;
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

    public void alugarLivro(String tituloLivro){
        Catalogo c = new Catalogo();

        if (c.buscarLivroTitulo(tituloLivro) == null){
            System.out.println("O livro especificado não consta no nosso catálogo!");
            return;
        }
        else if (!c.buscarLivroTitulo(tituloLivro).getDisponibilidade()){
            System.out.println("O livro especificado está indisponível!");
            return;
        }

        c.buscarLivroTitulo(tituloLivro).setDisponibilidade(false);
        livroAlugado = c.buscarLivroTitulo(tituloLivro).getTitulo();
        setStatusDevendo(true);
        c.atualizarCatalogo();
    }

    @Override
    public String toString() {
        if (getStatusDevendo()){
        return getNome() + ", " + getIdade() + ", " + "Está Devendo";
        }
        return getNome() + ", " + getIdade() + ", " + "Não está devendo";
    }

    @Override
    public String toCSV() {
        return getNome() + "," + getIdade() + "," + getStatusDevendo() + "," + getLivroAlugado();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Nome,Idade,Devendo,LivroAlugado";
    }
}