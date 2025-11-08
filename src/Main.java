import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        //TESTE PRA LISTA DE FUNCIONÁRIOS
        Scanner scanner = new Scanner(System.in);

        ArrayList<Funcionario> funcionariosCadastrados = new ArrayList<>();

        funcionariosCadastrados.add(new Funcionario("João", 20, "Estagiario"));
        funcionariosCadastrados.add(new Funcionario("Yan", 19, "Desenvolvedor"));
        funcionariosCadastrados.add(new Funcionario("Caius", 25, "Chefe"));
        funcionariosCadastrados.add(new Funcionario("Renan", 100, "QA"));

        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionario.csv");

        List<Funcionario> funcionariosLidos = CSVUtil.lerCSV("funcionario.csv", Funcionario.class);
        System.out.println("\nFuncionários lidos do arquivo:");
        for (Funcionario f : funcionariosLidos) {
            System.out.println(f.getNome() + " - " + f.getCargo());
        }
       
        ArrayList<Cliente> clientesCadastrados = new ArrayList<>();

        clientesCadastrados.add(new Cliente("Ana", 21,true));
        clientesCadastrados.add(new Cliente("Pedro", 30,false));

        CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv");

        List<Cliente> clientesLidos = CSVUtil.lerCSV("clientes.csv", Cliente.class);
        System.out.println("\nClientes lidos do arquivo:");
        for (Cliente c : clientesLidos) {
            System.out.println(c.toCSV());
        }


         List<Livro> livros = new ArrayList<>();
        livros.add(new Livro("O Hobbit", "J.R.R. Tolkien", true ));
        livros.add(new Livro("1984", "George Orwell", false ));

        CSVUtil.gravarCSV(livros, "livros.csv");

        List<Livro> livrosLidos = CSVUtil.lerCSV("livros.csv", Livro.class);
        System.out.println("\nLivros lidos do arquivo:");
        for (Livro l : livrosLidos) {
            System.out.println(l.toCSV());
        }
    }







       /*
        ArrayList<Funcionario> funcionariosCadastrados = getFuncionarios(); 

       System.out.println("Teste de cadastro de funcionários");
        System.out.println("Digite o seu nome e entre como funcionário:");
        String nomeBusca = scanner.nextLine();
        if (!buscarFuncionario(nomeBusca)){
            System.out.println("O funcionário não está na nossa lista de funcionários!");
        }*/
    

    /*private static ArrayList<Funcionario> getFuncionarios() {
        ArrayList<Funcionario> funcionariosCadastrados = new ArrayList<>();

        Funcionario funcionario1 = new Funcionario("Caius", 500000, "Líder");
        Funcionario funcionario2= new Funcionario("Renan", 1337, "Manutenção");
        Funcionario funcionario3 = new Funcionario("Yan", 67, "Logística");
        Funcionario funcionario4 = new Funcionario("Felix", 1, "Cargo secreto");
        Funcionario funcionario5 = new Funcionario("Igor", 3141159, "Contador");

     
        funcionariosCadastrados.add(funcionario1);
        funcionariosCadastrados.add(funcionario2);
        funcionariosCadastrados.add(funcionario3);
        funcionariosCadastrados.add(funcionario4);
        funcionariosCadastrados.add(funcionario5);

        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionarios.csv");

        return getFuncionarios();
    }

  

    

    private static boolean buscarFuncionario(String nomeBusca) {
        ArrayList<Funcionario> funcionariosCadastrados = getFuncionarios();
        for (Funcionario funcionario : funcionariosCadastrados) {
            if (Objects.equals(funcionario.getNome(), nomeBusca)) {
                System.out.println("BEM VINDO!");
                return true;
            }
        }
        return false;
    }*/
}
