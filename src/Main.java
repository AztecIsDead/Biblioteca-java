import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        ArrayList<Funcionario> funcionariosCadastrados = new ArrayList<>();

        funcionariosCadastrados.add(new Funcionario("João", 20, "Estagiario"));
        funcionariosCadastrados.add(new Funcionario("Yan", 19, "Desenvolvedor"));
        funcionariosCadastrados.add(new Funcionario("Caius", 25, "Chefe"));
        funcionariosCadastrados.add(new Funcionario("Renan", 100, "QA"));

        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionario.csv");

        List<Funcionario> funcionariosLidos = CSVUtil.lerCSV("funcionario.csv", Funcionario.class);
        System.out.println("\nFuncionários lidos do arquivo:");
        for (Funcionario f : funcionariosLidos) {
            System.out.println(f);
        }
       
        ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

        clientesCadastrados.add(new Cliente("Ana", 21,true));
        clientesCadastrados.add(new Cliente("Pedro", 30,false));

        CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv");

        List<Cliente> clientesLidos = CSVUtil.lerCSV("clientes.csv", Cliente.class);
        System.out.println("\nClientes lidos do arquivo:");
        for (Cliente c : clientesLidos) {
            System.out.println(c);
        }


         List<Livro> livros = new ArrayList<>();
        livros.add(new Livro("O Hobbit", "J.R.R. Tolkien", false));
        livros.add(new Livro("1984", "George Orwell", true));

        CSVUtil.gravarCSV(livros, "livros.csv");

        List<Livro> livrosLidos = CSVUtil.lerCSV("livros.csv", Livro.class);
        System.out.println("\nLivros lidos do arquivo:");
        for (Livro l : livrosLidos) {
            System.out.println(l);
        }
    }
}
