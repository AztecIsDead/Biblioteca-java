import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {

    private final GUIService gui;

    public static void abrir(GUIService gui) {
        SwingUtilities.invokeLater(() -> new TelaInicial(gui).setVisible(true));
    }

    public TelaInicial(GUIService gui) {
        this.gui = gui;
        setTitle("Bem-vindo à Biblioteca");
        setSize(420, 300);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initUI();
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JLabel titulo = new JLabel("Acesse sua Conta", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(4, 1, 12, 12));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JButton btnCliente = new JButton("Sou Cliente");
        JButton btnFuncionario = new JButton("Sou Funcionário");
        JButton btnVIP = new JButton("Sou Cliente VIP");
        JButton btnSair = new JButton("Sair");

        painel.add(btnCliente);
        painel.add(btnFuncionario);
        painel.add(btnVIP);
        painel.add(btnSair);

        add(painel, BorderLayout.CENTER);

        btnCliente.addActionListener(e ->
                LoginCliente.abrir(this, gui, false)
        );

        btnFuncionario.addActionListener(e ->
                LoginFuncionario.abrir(this, gui)
        );

        btnVIP.addActionListener(e -> abrirVIP());

        btnSair.addActionListener(e -> dispose());
    }

    private void abrirVIP() {
        String senha = JOptionPane.showInputDialog(this, "Insira sua senha VIP:");
        if (senha == null) return;

        if (!senha.equals("123")) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Acesso VIP liberado.");

        LoginCliente.abrir(this, gui, true);
    }
}
