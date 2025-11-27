public class Main {
    public static void main(String[] args) {

        BibliotecaServico svc = new BibliotecaServico(
                "data/clientes.csv",
                "data/funcionarios.csv",
                "data/livros.csv",
                "data/requisicoes.csv",
                "data/emprestimos.csv",
                "data/sessoes.csv",
                "data/pagamentos.csv",
                "data/eventos.csv",
                "data/notificacoes.csv"
        );

        GUIService gui = new GUIService(svc);

        MainFrame.abrir(gui);
    }
}
