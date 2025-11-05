public class Livro {
    private String titulo;
    private String autor;
    private int paginas;
    private boolean disponibilidade;

    public Livro(String titulo, String autor, int paginas, boolean disponibilidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.paginas = paginas;
        this.disponibilidade = disponibilidade;
    }

    public int getPaginas(){
        return this.paginas;
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
        return this.titulo + " - " + this.autor + " (" +this.paginas+" paginas)"
                + ", " + getDisponibilidade();
    }
}