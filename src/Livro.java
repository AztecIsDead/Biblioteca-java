public class Livro implements CSVGravavel {
    private String titulo;
    private String autor;
    private boolean disponibilidade;
    private boolean exclusividade;

    public Livro(String titulo, String autor, boolean disponibilidade, boolean exclusividade) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponibilidade = disponibilidade;
        this.exclusividade = exclusividade;
    }

    public void setExclusividade(boolean exclusividade){
        this.exclusividade = exclusividade;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setAutor(String autor){
        this.autor = autor;
    }

    public void setDisponibilidade(boolean disponibilidade){
        if (this.exclusividade){
            this.disponibilidade = disponibilidade;
        }
        else this.disponibilidade = true;
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

    public boolean getExclusividade(){
        return this.exclusividade;
    }

    public String toString(){
        if (getDisponibilidade()){
            if (getExclusividade()){
                return getTitulo() + "," + getAutor() + "," + "Disponível" + "," + "Raro";
            }
            else return getTitulo() + "," + getAutor() + "," + "Disponível" + "," + "Comum";
        }
        if (getExclusividade()){
            return getTitulo() + "," + getAutor() + "," + "Indisponível" + "," + "Raro";
        }
        return getTitulo() + "," + getAutor() + "," + "Indisponível" + "," + "Comum";
    }

    @Override
    public String toCSV() {
        return getTitulo() + "," + getAutor() + "," + getDisponibilidade() + "," + getExclusividade();
    }

    @Override
    public String getCabecalhoCSV() {
        return "Titulo,Autor,Disponibilidade,Exclusividade";
    }

}