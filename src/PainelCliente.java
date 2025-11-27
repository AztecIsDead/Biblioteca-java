import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class PainelCliente extends JFrame {

    private final GUIService gui;
    private final Cliente cliente;

    public static void abrir(GUIService gui, Cliente cliente) {
        SwingUtilities.invokeLater(() -> new PainelCliente(gui, cliente).setVisible(true));
    }

    public PainelCliente(GUIService gui, Cliente cliente) {
        this.gui = gui;
        this.cliente = cliente;

        setTitle("Painel do Cliente - " + cliente.getNome());
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        JLabel titulo = new JLabel("Bem-vindo, " + cliente.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnReq = new JButton("Fazer Requisição de Livro");
        JButton btnDev = new JButton("Devolver Livro Atual");
        JButton btnEmp = new JButton("Ver Meus Empréstimos");
        JButton btnSair = new JButton("Fechar");

        panel.add(btnReq);
        panel.add(btnDev);
        panel.add(btnEmp);
        panel.add(btnSair);

        add(panel);

        btnReq.addActionListener(e -> fazerRequisicao());
        btnDev.addActionListener(e -> devolverLivro());
        btnEmp.addActionListener(e -> verEmprestimos());
        btnSair.addActionListener(e -> dispose());
    }

    private void fazerRequisicao() {
        List<Livro> livros = gui.listarLivros();
        String livroId = BookPickerDialog.escolher(this, livros);
        if (livroId == null) return;

        Optional<String> erro = gui.criarRequisicao(cliente.getId(), livroId);

        if (erro.isPresent()) JOptionPane.showMessageDialog(this, "Erro: " + erro.get());
        else JOptionPane.showMessageDialog(this, "Requisição enviada com sucesso!");
    }

    private void devolverLivro() {
        List<Emprestimo> todos = gui.listarEmprestimos();

        Emprestimo encontrado = null;
        for (Emprestimo e : todos) {
            if (e.getClienteId() == cliente.getId() && !e.isDevolvido()) {
                encontrado = e;
                break;
            }
        }

        if (encontrado == null) {
            JOptionPane.showMessageDialog(this, "Você não possui empréstimos ativos.");
            return;
        }

        Optional<String> resp = gui.devolverEmprestimo(String.valueOf(encontrado.getId()));
        if (resp.isPresent())
            JOptionPane.showMessageDialog(this, "Erro: " + resp.get());
        else
            JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
    }

    private void verEmprestimos() {
        List<Emprestimo> lista = gui.getEmprestimosDoCliente(cliente.getId());
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum empréstimo encontrado.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Emprestimo e : lista) {
            sb.append("ID: ").append(e.getId())
                    .append(" | Livro: ").append(e.getLivroId())
                    .append(" | Devolvido: ").append(e.isDevolvido())
                    .append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Empréstimos", JOptionPane.INFORMATION_MESSAGE);
    }
}
