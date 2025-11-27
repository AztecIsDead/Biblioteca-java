import javax.swing.*;
import java.awt.*;

public class ClienteCadastroDialog extends JDialog {

    private boolean confirmado = false;
    private String nome, usuario, senha;
    private int idade;
    private Cliente.TipoCliente tipo;

    public static ClienteCadastroDialog abrir(JFrame parent) {
        ClienteCadastroDialog dlg = new ClienteCadastroDialog(parent);
        dlg.setVisible(true);
        return dlg.confirmado ? dlg : null;
    }

    public ClienteCadastroDialog(JFrame parent) {
        super(parent, "Novo Cliente", true);

        JTextField tfNome = new JTextField();
        JTextField tfIdade = new JTextField();
        JTextField tfUsuario = new JTextField();
        JTextField tfSenha = new JTextField();

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"COMUM", "VIP"});

        JPanel p = new JPanel(new GridLayout(5, 2, 6, 6));
        p.add(new JLabel("Nome:")); p.add(tfNome);
        p.add(new JLabel("Idade:")); p.add(tfIdade);
        p.add(new JLabel("Usuário:")); p.add(tfUsuario);
        p.add(new JLabel("Senha:")); p.add(tfSenha);
        p.add(new JLabel("Tipo:")); p.add(cbTipo);

        JButton ok = new JButton("Criar");
        JButton cancel = new JButton("Cancelar");

        ok.addActionListener(e -> {
            try {
                nome = tfNome.getText();
                usuario = tfUsuario.getText();
                senha = HashUtil.hash(tfSenha.getText());
                idade = Integer.parseInt(tfIdade.getText());
                tipo = Cliente.TipoCliente.valueOf(cbTipo.getSelectedItem().toString());
                confirmado = true;
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos.");
            }
        });

        cancel.addActionListener(e -> dispose());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.add(ok);
        bottom.add(cancel);

        setLayout(new BorderLayout());
        add(p, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public String getNome() { return nome; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
    public int getIdade() { return idade; }
    public Cliente.TipoCliente getTipo() { return tipo; }
}
