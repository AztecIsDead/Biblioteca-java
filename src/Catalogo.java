import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


//classe de interações com a "database"
public class Catalogo {
    ArrayList<Livro> catalogoLivros = CSVUtil.lerCSV("livros.csv", Livro.class);
    ArrayList<Funcionario> funcionariosCadastrados = CSVUtil.lerCSV("funcionarios.csv", Funcionario.class);
    ArrayList<Cliente> clientesCadastrados = CSVUtil.lerCSV("clientes.csv", Cliente.class);

    public void registrarLivro(){
        Scanner scanner = new Scanner(System.in);
        Livro livro = new Livro("Titulo", "Autor", true);

        System.out.println("Digite o título do livro:");
        String titulo = scanner.nextLine();
        livro.setTitulo(titulo);

        System.out.println("Digite o autor do livro:");
        String autor = scanner.nextLine();
        livro.setAutor(autor);

        catalogoLivros.add(livro);

        System.out.println("livro registrado com sucesso!");

        CSVUtil.gravarCSV(catalogoLivros, "livros.csv");
    }

    public void registrarFuncionario(){
        boolean inputValido = false;
        int idade = 0;

        Scanner scanner = new Scanner(System.in);
        Funcionario funcionario = new Funcionario("Nome", idade, "cargo");

        System.out.println("Digite o nome do funcionario:");
        String nome = scanner.nextLine();
        funcionario.setNome(nome);

        while (!inputValido){
            System.out.println("Digite a idade do funcionario:");
            try {
                idade = scanner.nextInt();
                inputValido = true;
                funcionario.setIdade(idade);
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Idade invalida! Tente novamente:");
                scanner.nextLine();
            }
        }
        System.out.println("Digite o cargo do funcionario:");
        String cargo = scanner.nextLine();
        funcionario.setCargo(cargo);

        funcionariosCadastrados.add(funcionario);
        System.out.println("Funcionário registrado com sucesso!");

        CSVUtil.gravarCSV(funcionariosCadastrados, "funcionarios.csv");
    }

    public void registrarCliente(){
        boolean inputValido = false;
        int idade = 0;

        Scanner scanner = new Scanner(System.in);
        Cliente cliente = new Cliente("Nome", idade, false);

        System.out.println("Digite o nome do cliente:");
        String nome = scanner.nextLine();
        cliente.setNome(nome);

        while (!inputValido){
            System.out.println("Digite a idade do cliente:");
            try {
                idade = scanner.nextInt();
                inputValido = true;
                cliente.setIdade(idade);
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Idade invalida! Tente novamente:");
                scanner.nextLine();
            }
        }
        clientesCadastrados.add(cliente);
        System.out.println("Cliente registrado com sucesso!");

        CSVUtil.gravarCSV(clientesCadastrados, "clientes.csv");
    }

    public ArrayList<Livro> buscarLivroTitulo(String titulo){
        ArrayList<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro l : catalogoLivros){
            if (l.getTitulo().equalsIgnoreCase(titulo)){
                livrosEncontrados.add(l);
            }
        }
        return livrosEncontrados;
    }

    public ArrayList<Livro> buscarLivroAutor(String autor){
        ArrayList<Livro> livrosEncontrados = new ArrayList<>();
        for (Livro l : catalogoLivros){
            if (l.getAutor().equalsIgnoreCase(autor)){
                livrosEncontrados.add(l);
            }
        }
        return livrosEncontrados;
    }

    public ArrayList<Cliente> buscarClienteNome(String nome){
        ArrayList<Cliente> clientesEncontrados = new ArrayList<>();
        for (Cliente c : clientesCadastrados){
            if (c.getNome().equalsIgnoreCase(nome)){
                clientesEncontrados.add(c);
            }
        }
        return clientesEncontrados;
    }

    public ArrayList<Funcionario> buscarFuncionarioNome(String nome){
        ArrayList<Funcionario> funcionariosEncontrados = new ArrayList<>();
        for (Funcionario f : funcionariosCadastrados){
            if (f.getNome().equalsIgnoreCase(nome)){
                funcionariosEncontrados.add(f);
            }
        }
        return funcionariosEncontrados;

    }
    public ArrayList<Funcionario> buscarFuncionarioCargo(String cargo){
        ArrayList<Funcionario> funcionariosEncontrados = new ArrayList<>();
        for (Funcionario f : funcionariosCadastrados){
            if (f.getCargo().equalsIgnoreCase(cargo)){
                funcionariosEncontrados.add(f);
            }
        }
        return funcionariosEncontrados;
    }
}