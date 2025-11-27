import javax.swing.*;
import java.awt.*;

public class FuncionarioDialog extends JDialog {
    private boolean confirmado = false;
    private JTextField txtNome = new JTextField(30);
    private JSpinner spIdade = new JSpinner(new SpinnerNumberModel(18, 0, 120, 1));
    private JTextField txtUsuario = new JTextField(20);
    private JPasswordField txtSenha = new JPasswordField(20);
    private JCheckBox chkHash = new JCheckBox("Aplicar hash");

    public FuncionarioDialog(Frame owner) {
        super(owner, "Funcionário", true);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(4,2,5,5));
        p.add(new JLabel("Nome:")); p.add(txtNome);
        p.add(new JLabel("Idade:")); p.add(spIdade);
        p.add(new JLabel("Usuário:")); p.add(txtUsuario);
        p.add(new JLabel("Senha:")); p.add(txtSenha);
        add(p, BorderLayout.CENTER);

        JPanel btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");
        btn.add(ok); btn.add(cancel); btn.add(chkHash);
        add(btn, BorderLayout.SOUTH);

        ok.addActionListener(e -> { confirmado = true; setVisible(false); });
        cancel.addActionListener(e -> { confirmado = false; setVisible(false); });

        pack();
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() { return confirmado; }
    public String getNome() { return txtNome.getText(); }
    public int getIdade() { return (Integer) spIdade.getValue(); }
    public String getUsuario() { return txtUsuario.getText(); }
    public String getSenha() { return new String(txtSenha.getPassword()); }
    public boolean isAplicarHash() { return chkHash.isSelected(); }

    public static FuncionarioDialog showDialog(Frame owner) {
        FuncionarioDialog d = new FuncionarioDialog(owner);
        d.setVisible(true);
        return d;
    }
}
