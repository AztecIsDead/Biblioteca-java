import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ClienteFormDialog extends JDialog {
    private JTextField tfNome;
    private JSpinner spIdade;
    private JCheckBox cbDevendo;
    private JTextField tfLivroAlugado;
    private boolean saved = false;

    public ClienteFormDialog(Frame owner) {
        super(owner, true);
        setTitle("Cliente");
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        tfNome = new JTextField(25);
        spIdade = new JSpinner(new SpinnerNumberModel(18, 0, 150, 1));
        cbDevendo = new JCheckBox("Está devendo?");
        tfLivroAlugado = new JTextField(25);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Nome:"), c);
        c.gridx = 1; form.add(tfNome, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Idade:"), c);
        c.gridx = 1; form.add(spIdade, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Livro Alugado:"), c);
        c.gridx = 1; form.add(tfLivroAlugado, c);

        c.gridx = 0; c.gridy = 3; c.gridwidth = 2; form.add(cbDevendo, c);

        JButton ok = new JButton("Salvar");
        ok.addActionListener((ActionEvent e) -> onSave());
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener((ActionEvent e) -> onCancel());

        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }

    public void setCliente(Cliente c) {
        if (c == null) {
            tfNome.setText("");
            spIdade.setValue(18);
            cbDevendo.setSelected(false);
            tfLivroAlugado.setText("Nenhum");
        } else {
            tfNome.setText(c.getNome());
            spIdade.setValue(c.getIdade());
            cbDevendo.setSelected(c.getStatusDevendo());
            tfLivroAlugado.setText(c.getLivroAlugado() == null ? "Nenhum" : c.getLivroAlugado());
        }
    }

    public Cliente getClienteFromForm() {
        String nome = tfNome.getText().trim();
        int idade = (Integer) spIdade.getValue();
        boolean devendo = cbDevendo.isSelected();
        String livro = tfLivroAlugado.getText().trim();
        if (livro.isEmpty()) livro = "Nenhum";
        return new Cliente(nome, idade, devendo, livro);
    }

    private void onSave() {
        if (tfNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saved = true;
        setVisible(false);
    }

    private void onCancel() { saved = false; setVisible(false); }
    public boolean isSaved() { return saved; }
}
