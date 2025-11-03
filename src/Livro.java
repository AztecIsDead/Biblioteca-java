public class Livro {
    private String titulo;
    private String autor;

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }
        public String getTitulo(){
            return titulo;
        }
        public String getAutor(){
            return autor;
        }
        public void exibirInfo(){
            System.out.println("Título do Livro: " + titulo);
            System.out.println("Autor do Livro: " + autor);
        }


}
