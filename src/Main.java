public class Main {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        catalogo.registrarCliente();
        catalogo.buscarClienteNome("Alberto").alugarLivro("1984");
        catalogo.atualizarClientes();
    }
}