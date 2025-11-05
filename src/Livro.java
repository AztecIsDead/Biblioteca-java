public class Livro {
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

    public String getDisponibilidade(){
        if (this.disponibilidade){
            return "Disponível";
        }
        else {
            return "Indisponível";
        }
    }

    @Override
    public String toString() {
        return this.titulo + " - " + this.autor
                + ", " + getDisponibilidade();
    }
}