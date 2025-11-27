import javax.swing.*;
import java.awt.*;

public class LoginCliente extends JDialog {

    private final GUIService guiService;
    private final boolean vip;

    public static void abrir(Frame owner, GUIService guiService, boolean vip) {
        new LoginCliente(owner, guiService, vip).setVisible(true);
    }

    public LoginCliente(Frame owner, GUIService guiService, boolean vip) {
        super(owner, vip ? "Login Cliente VIP" : "Login Cliente", true);
        this.guiService = guiService;
        this.vip = vip;
        init();
    }

    private void init() {
        setSize(320, 200);
        setLayout(new GridLayout(3, 2, 10, 10));
        setLocationRelativeTo(getOwner());

        JTextField tfUsuario = new JTextField();
        JPasswordField tfSenha = new JPasswordField();

        add(new JLabel("Usuário:"));
        add(tfUsuario);
        add(new JLabel("Senha:"));
        add(tfSenha);

        JButton btnEntrar = new JButton("Entrar");
        JButton btnCancelar = new JButton("Cancelar");
        add(btnEntrar);
        add(btnCancelar);

        btnEntrar.addActionListener(e -> {

            String usuario = tfUsuario.getText().trim();
            String senha = new String(tfSenha.getPassword());

            Cliente encontrado = null;
            for (Cliente c : guiService.listarClientes()) {
                if (c.getUsuario().equals(usuario) && c.getSenhaHash().equals(senha)) {
                    encontrado = c;
                    break;
                }
            }

            if (encontrado == null) {

                int op = JOptionPane.showConfirmDialog(
                        this,
                        "Cliente não encontrado.\nDeseja criar um novo?" + (vip ? "\n(Requer senha mestre 123)" : ""),
                        "Criar Cliente",
                        JOptionPane.YES_NO_OPTION
                );

                if (op == JOptionPane.NO_OPTION)
                    return;

                if (vip) {
                    String mestre = JOptionPane.showInputDialog(this, "Senha mestre para criar Cliente VIP:");
                    if (!"123".equals(mestre)) {
                        JOptionPane.showMessageDialog(this, "Senha incorreta.");
                        return;
                    }
                }

                String nome = JOptionPane.showInputDialog(this, "Nome:");
                int idade;
                try {
                    idade = Integer.parseInt(JOptionPane.showInputDialog(this, "Idade:"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Idade inválida.");
                    return;
                }

                Cliente.TipoCliente tipo = vip ? Cliente.TipoCliente.VIP : Cliente.TipoCliente.REGULAR;

                Cliente novo = guiService.registrarCliente(nome, idade, usuario, senha, tipo, false);
                JOptionPane.showMessageDialog(this, "Cliente criado!");

                dispose();
                PainelCliente.abrir(guiService, novo);
                return;
            }
            if (vip && encontrado.getTipo() != Cliente.TipoCliente.VIP) {
                JOptionPane.showMessageDialog(this, "Este cliente não é VIP.");
                return;
            }

            dispose();
            PainelCliente.abrir(guiService, encontrado);
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
