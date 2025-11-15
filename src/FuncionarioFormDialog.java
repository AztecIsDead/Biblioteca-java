import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FuncionarioFormDialog extends JDialog {
    private JTextField tfNome;
    private JSpinner spIdade;
    private JTextField tfCargo;
    private boolean saved = false;

    public FuncionarioFormDialog(Frame owner) {
        super(owner, true);
        setTitle("Funcionário");
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        tfNome = new JTextField(25);
        spIdade = new JSpinner(new SpinnerNumberModel(18, 0, 150, 1));
        tfCargo = new JTextField(20);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; form.add(new JLabel("Nome:"), c);
        c.gridx = 1; form.add(tfNome, c);

        c.gridx = 0; c.gridy = 1; form.add(new JLabel("Idade:"), c);
        c.gridx = 1; form.add(spIdade, c);

        c.gridx = 0; c.gridy = 2; form.add(new JLabel("Cargo:"), c);
        c.gridx = 1; form.add(tfCargo, c);

        JButton ok = new JButton("Salvar");
        ok.addActionListener((ActionEvent e) -> onSave());
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener((ActionEvent e) -> onCancel());

        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }

    public void setFuncionario(Funcionario f) {
        if (f == null) {
            tfNome.setText("");
            spIdade.setValue(18);
            tfCargo.setText("");
        } else {
            tfNome.setText(f.getNome());
            spIdade.setValue(f.getIdade());
            tfCargo.setText(f.getCargo());
        }
    }

    public Funcionario getFuncionarioFromForm() {
        String nome = tfNome.getText().trim();
        int idade = (Integer) spIdade.getValue();
        String cargo = tfCargo.getText().trim();
        return new Funcionario(nome, idade, cargo);
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
