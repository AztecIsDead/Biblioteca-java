import javax.swing.*;
import java.awt.*;

public class LoginEscolha extends JDialog {

    private final GUIService guiService;

    public LoginEscolha(Frame owner, GUIService guiService) {
        super(owner, "Escolha de Login", true);
        this.guiService = guiService;
        init();
    }

    private void init() {
        setSize(350, 280);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnCliente = new JButton("Entrar como Cliente");
        JButton btnVip = new JButton("Entrar como Cliente VIP");
        JButton btnFuncionario = new JButton("Entrar como Funcionário");
        JButton btnNovoCliente = new JButton("Criar Novo Cliente");
        JButton btnNovoFuncionario = new JButton("Criar Novo Funcionário");
        JButton btnSair = new JButton("Sair");


        panel.add(btnCliente);
        panel.add(btnVip);
        panel.add(btnFuncionario);
        panel.add(btnNovoCliente);
        panel.add(btnNovoFuncionario);
        panel.add(btnSair);


        add(panel, BorderLayout.CENTER);

        btnCliente.addActionListener(e -> {
            dispose();
            LoginCliente.abrir((Frame) getOwner(), guiService, false);
        });

        btnFuncionario.addActionListener(e -> {
            dispose();
            LoginFuncionario.abrir((Frame) getOwner(), guiService);
        });
        btnVip.addActionListener(e -> {
            dispose();
            LoginCliente.abrir((Frame) getOwner(), guiService, true);
        });
        btnCliente.addActionListener(e -> {
            dispose();
            LoginCliente.abrir((Frame) getOwner(), guiService, false);
        });


        btnNovoCliente.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Nome:");
            if (nome == null) return;

            String idadeS = JOptionPane.showInputDialog(this, "Idade:");
            if (idadeS == null) return;

            int idade;
            try { idade = Integer.parseInt(idadeS); } catch (Exception ex) { return; }

            String usuario = JOptionPane.showInputDialog(this, "Usuário:");
            if (usuario == null) return;

            String senha = JOptionPane.showInputDialog(this, "Senha:");
            if (senha == null) return;

            guiService.registrarCliente(nome, idade, usuario, senha, Cliente.TipoCliente.REGULAR, true);
            JOptionPane.showMessageDialog(this, "Cliente criado com sucesso.");
        });

        btnNovoFuncionario.addActionListener(e -> {
            String chave = JOptionPane.showInputDialog(this, "Senha mestre do sistema:");
            if (!"123".equals(chave)) {
                JOptionPane.showMessageDialog(this, "Senha incorreta.");
                return;
            }

            String nome = JOptionPane.showInputDialog(this, "Nome:");
            if (nome == null) return;

            String idadeS = JOptionPane.showInputDialog(this, "Idade:");
            if (idadeS == null) return;

            int idade;
            try { idade = Integer.parseInt(idadeS); } catch (Exception ex) { return; }

            String usuario = JOptionPane.showInputDialog(this, "Usuário:");
            if (usuario == null) return;

            String senha = JOptionPane.showInputDialog(this, "Senha:");
            if (senha == null) return;

            guiService.registrarFuncionario(nome, idade, usuario, senha, true);
            JOptionPane.showMessageDialog(this, "Funcionário criado com sucesso.");
        });

        btnSair.addActionListener(e -> dispose());

        setLocationRelativeTo(getOwner());
    }

    public static void abrir(GUIService guiService) {
        new LoginEscolha(null, guiService).setVisible(true);
    }
}
