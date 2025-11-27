import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class ClientFrame extends JFrame {

    private final GUIService gui;
    private Cliente clienteLogado;

    private final JLabel lblBemVindo = new JLabel("Bem-vindo!");
    private final JTextArea txtHistorico = new JTextArea(10, 40);

    public ClientFrame(GUIService gui, Cliente clienteLogado) {
        this.gui = gui;
        this.clienteLogado = clienteLogado;

        setTitle("Biblioteca - Cliente");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        init();
        loadData();

        setVisible(true);
    }

    private void init() {
        setLayout(new BorderLayout());

        lblBemVindo.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblBemVindo, BorderLayout.NORTH);

        txtHistorico.setEditable(false);
        JScrollPane sc = new JScrollPane(txtHistorico);
        add(sc, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnRequisitar = new JButton("Requisitar Livro");
        JButton btnEmprestimos = new JButton("Ver Empréstimos");

        botoes.add(btnRequisitar);
        botoes.add(btnEmprestimos);
        add(botoes, BorderLayout.SOUTH);

        // Ações
        btnRequisitar.addActionListener(e -> requisitarLivro());
        btnEmprestimos.addActionListener(e -> mostrarEmprestimos());
    }

    private void loadData() {
        lblBemVindo.setText("Bem-vindo, " + clienteLogado.getNome() + "!");

        txtHistorico.setText("");

        txtHistorico.append("Tipo: " + clienteLogado.getTipo() + "\n");
        txtHistorico.append("Idade: " + clienteLogado.getIdade() + "\n");
        txtHistorico.append("VIP: " + (clienteLogado.isVip() ? "Sim" : "Não") + "\n");
        txtHistorico.append("\n");
        txtHistorico.append("Histórico de requisições e empréstimos:\n");
        txtHistorico.append("(Ainda não implementado aqui, apenas carregamento básico.)\n");
    }

    private void requisitarLivro() {
        String livroId = JOptionPane.showInputDialog(this, "ID do livro:");
        if (livroId == null || livroId.isBlank()) return;

        Optional<String> r = gui.criarRequisicao(clienteLogado.getId(), livroId);
        if (r.isPresent())
            JOptionPane.showMessageDialog(this, "Erro: " + r.get());
        else
            JOptionPane.showMessageDialog(this, "Requisição criada!");

        loadData();
    }

    private void mostrarEmprestimos() {
        java.util.List<Emprestimo> todos = gui.listarEmprestimos();

        StringBuilder sb = new StringBuilder();
        sb.append("Empréstimos do cliente ").append(clienteLogado.getNome()).append(":\n\n");

        for (Emprestimo e : todos) {
            if (e.getClienteId() == clienteLogado.getId()) {
                sb.append("ID: ").append(e.getId()).append("\n");
                sb.append("Livro: ").append(e.getLivroId()).append("\n");
                sb.append("Data emprest.: ").append(e.getDataEmprestimo()).append("\n");
                sb.append("Data devolução: ").append(e.getDataDevolucao()).append("\n");
                sb.append("-----------------------------\n");
            }
        }

        if (sb.toString().endsWith(":\n\n"))
            sb.append("Nenhum empréstimo encontrado.");

        JOptionPane.showMessageDialog(this, sb.toString(), "Meus Empréstimos", JOptionPane.INFORMATION_MESSAGE);
    }
}
