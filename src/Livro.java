public class Livro implements CSVGravavel {
    private String titulo;
    private String autor;
    private boolean disponibilidade;

    public Livro(String titulo, String autor, boolean disponibilidade) {
        this.titulo = titulo;
        this.autor = autor;
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

    public String getDisponibilidadeString(){
        if (this.disponibilidade){
            return "Disponível";
        }
        else {
            return "Indisponível";
        }
    }

    @Override
    public String toString(){
        return getTitulo() + "," + getAutor() + "," + getDisponibilidadeString();
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