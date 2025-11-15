import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
public class LivroFormDialog extends JDialog{
    private JTextField tfTitulo;
    private JTextField tfAutor;
    private JCheckBox cbDisponivel;
    private boolean saved = false;
    //TextBoxes + Checkbox
    public LivroFormDialog(Frame owner){
        super(owner, true);
        setTitle("Livro");
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }
    private void onSave() {
        if (tfTitulo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Título é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tfAutor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Autor é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        saved = true;
        setVisible(false);
    }

    private void onCancel() {
        saved = false;
        setVisible(false);
    }

    public boolean isSaved() {
        return saved;
    }

    private void initComponents(){
        tfTitulo = new JTextField(30);
        tfAutor = new JTextField(30);
        cbDisponivel = new JCheckBox("Disponível");

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0;
        form.add(new JLabel("Titulo"), c);
        c.gridx = 1;
        form.add(tfTitulo, c);

        c.gridx = 0; c.gridy = 1;
        form.add(new JLabel("Autor:"), c);
        c.gridx = 1;
        form.add(tfAutor, c);

        c.gridx = 0; c.gridy = 2; c.gridwidth = 2;
        form.add(cbDisponivel, c);

        JButton btnOk = new JButton("Salvar");
        btnOk.addActionListener((ActionEvent e) -> onSave());
        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener((ActionEvent e) -> onCancel());

        JPanel bp = new JPanel();
        bp.add(btnOk);
        bp.add(btnCancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }
    public void setLivro(Livro livro) {
        if (livro != null) {
            tfTitulo.setText(livro.getTitulo());
            tfAutor.setText(livro.getAutor());
            cbDisponivel.setSelected(livro.isDisponibilidade());
        } else {
            tfTitulo.setText("");
            tfAutor.setText("");
            cbDisponivel.setSelected(true);
        }
    }
    public Livro getLivroFromForm() {
        String t = tfTitulo.getText().trim();
        String a = tfAutor.getText().trim();
        boolean d = cbDisponivel.isSelected();
        return new Livro(t, a, d);
    }
}



