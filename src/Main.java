import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //TESTE PRA LISTA DE FUNCIONÁRIOS
        Scanner scanner = new Scanner(System.in);
        ArrayList<Funcionario> funcionariosCadastrados = getFuncionarios();
        ArrayList<Cliente> clientesCadastrados = new ArrayList<>();
        System.out.println("Teste de cadastro de funcionários");
        System.out.println("Digite o seu nome e entre como funcionário:");
        String nomeBusca = scanner.nextLine();
        if (!buscarFuncionario(nomeBusca)){
            System.out.println("O funcionário não está na nossa lista de funcionários!");
        }
    }

    private static ArrayList<Funcionario> getFuncionarios() {
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
        return funcionariosCadastrados;
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
    }
}
