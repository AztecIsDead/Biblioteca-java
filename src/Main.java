public class Main {
    public static void main(String[] args) {
        if (args != null && args.length > 0 && "seed".equalsIgnoreCase(args[0])) {
            Catalogo c = new Catalogo();
            if (c.getCatalogoLivros() == null || c.getCatalogoLivros().isEmpty()) {
                c.addLivro(new Livro("1984", "George Orwell", true,false));
                c.addLivro(new Livro("Dom Casmurro", "Machado de Assis", true,false));
                c.addLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", true,false));
                System.out.println("Seed: livros adicionados.");
            } else {
                System.out.println("Seed: já existem livros, pulando adição.");
            }
            if (c.getClientesCadastrados() == null || c.getClientesCadastrados().isEmpty()) {
                c.addCliente(new Cliente("Alberto", 30, false, "Nenhum", "", false));
                System.out.println("Seed: cliente 'Alberto' adicionado.");
            } else {
                System.out.println("Seed: já existem clientes, pulando adição.");
            }

            System.out.println("Seed finalizado. Rode 'java -cp out Main' para abrir a GUI.");
            return;
        }
        SwingMain.main(args);
    }
}
