public class Livro implements CSVGravavel {
    private String titulo;
    private String autor;
    private boolean disponibilidade;

    public Livro(String titulo, String autor, boolean disponibilidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponibilidade = disponibilidade;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public void setDisponibilidade(boolean disponibilidade){
        this.disponibilidade = disponibilidade;
    }

    public String getTitulo(){
        return this.titulo;
    }

    public String getAutor(){
        return this.autor;
    }

    public boolean getDisponibilidade(){
        return this.disponibilidade;
    }

    public boolean isDisponibilidade(){
        return this.disponibilidade;
    }

    @Override
    public String toString(){
        if (getDisponibilidade()){
        return getTitulo() + "," + getAutor() + "," + "Disponível";
        }
        return getTitulo() + "," + getAutor() + "," + "Indisponível";
    }

    @Override
    public String toCSV() {
        return getTitulo() + "," + getAutor() + "," + getDisponibilidade();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Titulo,Autor,Disponibilidade";
    }

}