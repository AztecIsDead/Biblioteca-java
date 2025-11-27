import javax.swing.*;
import java.awt.*;

public class LoginFuncionario extends JDialog {

    private final GUIService guiService;

    public static void abrir(Frame owner, GUIService guiService) {
        new LoginFuncionario(owner, guiService).setVisible(true);
    }

    public LoginFuncionario(Frame owner, GUIService guiService) {
        super(owner, "Login de Funcionário", true);
        this.guiService = guiService;
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

            Funcionario encontrado = null;
            for (Funcionario f : guiService.listarFuncionarios()) {
                if (f.getUsuario().equals(usuario) && f.getSenhaHash().equals(senha)) {
                    encontrado = f;
                    break;
                }
            }

            if (encontrado == null) {

                int op = JOptionPane.showConfirmDialog(
                        this,
                        "Funcionário não encontrado.\nDeseja criar um novo?\n(Requer senha mestre)",
                        "Criar Funcionário",
                        JOptionPane.YES_NO_OPTION
                );

                if (op == JOptionPane.YES_OPTION) {

                    String mestre = JOptionPane.showInputDialog(this, "Senha mestre:");
                    if (!"123".equals(mestre)) {
                        JOptionPane.showMessageDialog(this, "Senha mestre incorreta!");
                        return;
                    }

                    String nome = JOptionPane.showInputDialog(this, "Nome:");
                    int idade;
                    try {
                        idade = Integer.parseInt(JOptionPane.showInputDialog(this, "Idade:"));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Idade inválida.");
                        return;
                    }

                    Funcionario novo = guiService.registrarFuncionario(nome, idade, usuario, senha, false);

                    JOptionPane.showMessageDialog(this, "Funcionário criado!");
                    dispose();
                    PainelFuncionario.abrir(guiService, novo);
                }

                return;
            }

            dispose();
            PainelFuncionario.abrir(guiService, encontrado);
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
