import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;


//classe de interacoes com a database
public class Catalogo {
    public void registrarLivro(){
        ArrayList<Livro> catalagoLivros = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        Livro livro = new Livro("Titulo", "Autor", false);

        System.out.println("Digite o título do livro:");
        String titulo = scanner.nextLine();
        livro.setTitulo(titulo);

        System.out.println("Digite o autor do livro:");
        String autor = scanner.nextLine();
        livro.setAutor(autor);

        catalagoLivros.add(livro);

        System.out.println("livro registrado com sucesso!");

        CSVUtil.gravarCSV(catalagoLivros, "livros.csv");
    }

    public void registrarFuncionario(){
        boolean inputValido = false;
        int idade = 0;

        ArrayList<Funcionario> funcionarios = new ArrayList<>();
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

        funcionarios.add(funcionario);
        System.out.println("Funcionário registrado com sucesso!");

        CSVUtil.gravarCSV(funcionarios, "funcionarios.csv");
    }

    public void registrarCliente(){
        boolean inputValido = false;
        int idade = 0;

        ArrayList<Cliente> clientes = new ArrayList<>();
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
        clientes.add(cliente);
        System.out.println("Cliente registrado com sucesso!");

        CSVUtil.gravarCSV(clientes, "clientes.csv");
    }
}
