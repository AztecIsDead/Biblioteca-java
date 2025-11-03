import java.util.Scanner;
public class Cliente {
    private String nome;
    private int idade;
    private String cpf;

    public Cliente(String nome, int idade, String cpf) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
    }
        public void cadastroCliente(){

            Scanner input = new Scanner(System.in);

            System.out.print("Digite seu Nome: ");
            nome = input.nextLine();

            System.out.print("Digite sua Idade: ");
            idade = input.nextInt();
            input.nextLine();

            System.out.print("Digite seu CPF: ");
            cpf = input.nextLine();

            System.out.println("\n Cliente cadastrado com sucesso!\n");
    }
}
