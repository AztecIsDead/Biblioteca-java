import javax.swing.*;
import java.awt.*;

public class ClienteFormDialog extends JDialog {
    private JTextField tfNome;
    private JSpinner spIdade;
    private JPasswordField pfSenha;
    private boolean saved = false;

    public ClienteFormDialog(Frame owner) {
        super(owner, true);
        setTitle("Registrar Cliente");
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        tfNome = new JTextField(25);
        spIdade = new JSpinner(new SpinnerNumberModel(18, 0, 150, 1));
        pfSenha = new JPasswordField(20);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();
        cst.insets = new Insets(6,6,6,6);
        cst.anchor = GridBagConstraints.WEST;

        cst.gridx = 0; cst.gridy = 0; form.add(new JLabel("Nome:"), cst);
        cst.gridx = 1; form.add(tfNome, cst);

        cst.gridx = 0; cst.gridy = 1; form.add(new JLabel("Idade:"), cst);
        cst.gridx = 1; form.add(spIdade, cst);

        cst.gridx = 0; cst.gridy = 2; form.add(new JLabel("Senha (para login):"), cst);
        cst.gridx = 1; form.add(pfSenha, cst);

        JButton ok = new JButton("Salvar");
        ok.addActionListener(e -> onSave());
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener(e -> onCancel());

        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            tfNome.setText("");
            spIdade.setValue(18);
            pfSenha.setText("");
        } else {
            tfNome.setText(cliente.getNome());
            spIdade.setValue(cliente.getIdade());
            pfSenha.setText(cliente.getSenha());
        }
    }
    public Cliente getClienteFromForm() {
        String nome = tfNome.getText().trim();
        int idade = (Integer) spIdade.getValue();
        String senha = new String(pfSenha.getPassword());
        // cliente sempre criado sem livro alugado e sem devendo
        return new Cliente(nome, idade, false, "Nenhum", senha, false);
    }
    private void onSave() {
        if (tfNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (pfSenha.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Senha obrigatória", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saved = true;
        setVisible(false);
    }

    private void onCancel() { saved = false; setVisible(false); }
    public boolean isSaved() { return saved; }
}
