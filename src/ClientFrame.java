import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {
    private Cliente currentClient = null;
    private JLabel lblStatus;
    private JLabel lblLivro;

    public ClientFrame() {
        super("Cliente - Biblioteca");
        initUI();
        setSize(500, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        JPanel top = new JPanel(new GridLayout(2, 2, 6, 6));
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        top.add(new JLabel("Usuário logado:"));
        lblStatus = new JLabel("Nenhum");
        top.add(lblStatus);

        top.add(new JLabel("Livro alugado:"));
        lblLivro = new JLabel("Nenhum");
        top.add(lblLivro);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);

        // BOTÕES
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRegistrar = new JButton("Registrar (novo cliente)");
        JButton btnLogar = new JButton("Logar");
        JButton btnRequest = new JButton("Pedir aluguel");
        JButton btnLogout = new JButton("Logout");

        actions.add(btnRegistrar);
        actions.add(btnLogar);
        actions.add(btnRequest);
        actions.add(btnLogout);

        getContentPane().add(actions, BorderLayout.CENTER);

        // EVENTOS

        btnRegistrar.addActionListener(e -> onRegistrar());
        btnLogar.addActionListener(e -> onLogar());
        btnLogout.addActionListener(e -> onLogout());
        btnRequest.addActionListener(e -> onRequest());
    }

    private void onRegistrar() {
        ClienteFormDialog dlg = new ClienteFormDialog(this);
        dlg.setVisible(true);

        if (dlg.isSaved()) {
            Cliente novo = dlg.getClienteFromForm();
            new ClienteService().save(novo);
            JOptionPane.showMessageDialog(this, "Cliente registrado: " + novo.getNome());
        }
    }

    private void onLogar() {
        JPanel p = new JPanel(new GridLayout(0, 1));
        JTextField tfNome = new JTextField();
        JPasswordField pfSenha = new JPasswordField();

        p.add(new JLabel("Nome:"));
        p.add(tfNome);
        p.add(new JLabel("Senha:"));
        p.add(pfSenha);

        int ok = JOptionPane.showConfirmDialog(
                this, p, "Login Cliente",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (ok != JOptionPane.OK_OPTION) return;

        String nome = tfNome.getText().trim();
        String senha = new String(pfSenha.getPassword());

        Catalogo c = new Catalogo();
        Cliente cl = c.buscarClienteNome(nome);

        if (cl == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!cl.getSenha().equals(senha)) {
            JOptionPane.showMessageDialog(this, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentClient = cl;
        lblStatus.setText(cl.getNome());
        lblLivro.setText(cl.getLivroAlugado());

        JOptionPane.showMessageDialog(this, "Logado como " + cl.getNome());
    }

    private void onLogout() {
        currentClient = null;
        lblStatus.setText("Nenhum");
        lblLivro.setText("Nenhum");
    }

    private void onRequest() {
        if (currentClient == null) {
            JOptionPane.showMessageDialog(this, "Você precisa logar primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Catalogo c = new Catalogo();
        var disponiveis = c.getCatalogoLivros().stream()
                .filter(Livro::getDisponibilidade)
                .toList();

        if (disponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há livros disponíveis no momento.");
            return;
        }

        String[] titulos = disponiveis.stream().map(Livro::getTitulo).toArray(String[]::new);

        String escolhido = (String) JOptionPane.showInputDialog(
                this,
                "Escolha um livro para solicitar:",
                "Pedido de aluguel",
                JOptionPane.PLAIN_MESSAGE,
                null,
                titulos,
                titulos[0]
        );

        if (escolhido == null) return;

        c.addRequest(currentClient.getNome(), escolhido);
        JOptionPane.showMessageDialog(this, "Pedido enviado. Aguarde aprovação do funcionário.");
    }
}
