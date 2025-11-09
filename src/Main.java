import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Catalogo catalogo = new Catalogo();
        catalogo.registrarFuncionario();
        catalogo.registrarLivro();
        catalogo.registrarCliente();
        /*ArrayList<Funcionario> funcionariosCadastrados = new ArrayList<>();

        funcionariosCadastrados.add(new Funcionario("João", 20, "Estagiario"));
        funcionariosCadastrados.add(new Funcionario("Yan", 19, "Desenvolvedor"));
        funcionariosCadastrados.add(new Funcionario("Caius", 25, "Chefe"));
        funcionariosCadastrados.add(new Funcionario("Renan", 100, "QA"));

        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionarios.csv");

        ArrayList<Funcionario> funcionariosLidos = CSVUtil.lerCSV("funcionarios.csv", Funcionario.class);
        System.out.println("\nFuncionários lidos do arquivo:");
        for (Funcionario funcionario : funcionariosLidos) {
            System.out.println(funcionario);
        }
       */
        /*ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

        clientesCadastrados.add(new Cliente("Ana", 21,true));
        clientesCadastrados.add(new Cliente("Pedro", 30,false));

        CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv");

        ArrayList<Cliente> clientesLidos = CSVUtil.lerCSV("clientes.csv", Cliente.class);
        System.out.println("\nClientes lidos do arquivo:");
        for (Cliente cliente : clientesLidos) {
            System.out.println(cliente);
        }*/

        /*ArrayList<Livro> livrosLidos = CSVUtil.lerCSV("livros.csv", Livro.class);
        System.out.println("\nLivros lidos do arquivo:");
        for (Livro livro : livrosLidos) {
            System.out.println(livro);
        }*/

    }
}
