import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ClientFrame extends JFrame {
    private Cliente currentClient = null;
    private JLabel lblStatus;
    private JLabel lblLivro;
    private JComboBox<String> cbLoginClientes;

    public ClientFrame() {
        super("Cliente - Biblioteca");
        initUI();
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initUI() {
        JPanel top = new JPanel(new GridLayout(3,2,6,6));
        top.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
        top.add(new JLabel("Usuário logado:"));
        lblStatus = new JLabel("Nenhum");
        top.add(lblStatus);
        top.add(new JLabel("Livro alugado:"));
        lblLivro = new JLabel("Nenhum");
        top.add(lblLivro);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);

        // painel de ações
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRegistrar = new JButton("Registrar (novo cliente)");
        JButton btnLogar = new JButton("Logar");
        JButton btnAlugar = new JButton("Alugar livro");
        JButton btnLogout = new JButton("Logout");

        actions.add(btnRegistrar);
        actions.add(btnLogar);
        actions.add(btnAlugar);
        actions.add(btnLogout);

        getContentPane().add(actions, BorderLayout.CENTER);
        cbLoginClientes = new JComboBox<>();
        refreshClientList();
        JPanel pLogin = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pLogin.add(new JLabel("Escolha seu nome:"));
        pLogin.add(cbLoginClientes);
        getContentPane().add(pLogin, BorderLayout.SOUTH);

        btnRegistrar.addActionListener(e -> onRegistrar());
        btnLogar.addActionListener(e -> onLogar());
        btnLogout.addActionListener(e -> onLogout());
        btnAlugar.addActionListener(e -> onAlugar());
    }
    private void refreshClientList() {
        cbLoginClientes.removeAllItems();
        Catalogo c = new Catalogo();
        for (Cliente cl : c.getClientesCadastrados()) {
            cbLoginClientes.addItem(cl.getNome());
        }
    }
    private void onRegistrar() {
        ClienteFormDialog dlg = new ClienteFormDialog(this);
        dlg.setCliente(null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Cliente novo = dlg.getClienteFromForm();
            // garante que cliente não seja criado com livro inexistente (combo já garante isso)
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    new ClienteService().save(novo);
                    return null;
                }
                @Override protected void done() {
                    refreshClientList();
                    JOptionPane.showMessageDialog(ClientFrame.this, "Cliente registrado: " + novo.getNome());
                }
            }.execute();
        }
    }
    private void onLogar() {
        String nome = (String) cbLoginClientes.getSelectedItem();
        if (nome == null || nome.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um nome para logar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Catalogo c = new Catalogo();
        Cliente cl = c.buscarClienteNome(nome);
        if (cl == null) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado (atualize a lista).", "Erro", JOptionPane.ERROR_MESSAGE);
            refreshClientList();
            return;
        }
        currentClient = cl;
        lblStatus.setText(currentClient.getNome());
        lblLivro.setText(currentClient.getLivroAlugado() == null ? "Nenhum" : currentClient.getLivroAlugado());
        JOptionPane.showMessageDialog(this, "Logado como: " + currentClient.getNome());
    }

    private void onLogout() {
        currentClient = null;
        lblStatus.setText("Nenhum");
        lblLivro.setText("Nenhum");
    }

    private void onAlugar() {
        if (currentClient == null) {
            JOptionPane.showMessageDialog(this, "Você precisa logar antes de alugar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Catalogo c = new Catalogo();
        java.util.List<Livro> disponiveis = c.getCatalogoLivros().stream()
                .filter(Livro::getDisponibilidade)
                .toList();
        if (disponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não há livros disponíveis no momento.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String[] titulos = disponiveis.stream().map(Livro::getTitulo).toArray(String[]::new);
        String escolhido = (String) JOptionPane.showInputDialog(
                this,
                "Escolha um livro para alugar:",
                "Alugar",
                JOptionPane.PLAIN_MESSAGE,
                null,
                titulos,
                titulos[0]
        );
        if (escolhido == null) return; // cancelou
        boolean ok = c.alugarLivroParaCliente(currentClient.getNome(), escolhido);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Livro alugado: " + escolhido, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            currentClient = c.buscarClienteNome(currentClient.getNome()); // recarrega cliente do catálogo
            lblLivro.setText(currentClient.getLivroAlugado() == null ? "Nenhum" : currentClient.getLivroAlugado());
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao alugar o livro. Talvez fique indisponível.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
