import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends JDialog {
    private String usuario;
    private String senha;
    private boolean confirmado = false;

    public LoginDialog(Frame owner) {
        super(owner, "Login", true);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JPanel fields = new JPanel(new GridLayout(2,2,5,5));
        fields.add(new JLabel("Usuário:"));
        JTextField txtUsuario = new JTextField();
        fields.add(txtUsuario);
        fields.add(new JLabel("Senha:"));
        JPasswordField txtSenha = new JPasswordField();
        fields.add(txtSenha);
        add(fields, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");
        botoes.add(ok);
        botoes.add(cancel);
        add(botoes, BorderLayout.SOUTH);

        ok.addActionListener(e -> {
            usuario = txtUsuario.getText();
            senha = new String(txtSenha.getPassword());
            confirmado = true;
            setVisible(false);
        });
        cancel.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() { return confirmado; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }

    public static LoginDialog showDialog(Frame owner) {
        LoginDialog d = new LoginDialog(owner);
        d.setVisible(true);
        return d;
    }
}
