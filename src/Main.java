public class Main {
    public static void main(String[] args) {
        // Modo opcional: "seed" -> popula CSVs com registros de exemplo (se estiverem vazios)
        if (args != null && args.length > 0 && "seed".equalsIgnoreCase(args[0])) {
            Catalogo c = new Catalogo();

            // Adiciona alguns livros se não houver nenhum
            if (c.getCatalogoLivros() == null || c.getCatalogoLivros().isEmpty()) {
                c.addLivro(new Livro("1984", "George Orwell", true));
                c.addLivro(new Livro("Dom Casmurro", "Machado de Assis", true));
                c.addLivro(new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", true));
                System.out.println("Seed: livros adicionados.");
            } else {
                System.out.println("Seed: já existem livros, pulando adição.");
            }

            // Adiciona um cliente de exemplo se não houver nenhum
            if (c.getClientesCadastrados() == null || c.getClientesCadastrados().isEmpty()) {
                c.addCliente(new Cliente("Alberto", 30, false, "Nenhum"));
                System.out.println("Seed: cliente 'Alberto' adicionado.");
            } else {
                System.out.println("Seed: já existem clientes, pulando adição.");
            }

            // As chamadas addLivro/addCliente já gravam via CSV (metodos implementados no Catalogo)
            System.out.println("Seed finalizado. Rode 'java -cp out Main' para abrir a GUI.");
            return;
        }

        // Comportamento padrão: abrir a GUI Swing
        SwingMain.main(args);
    }
}
