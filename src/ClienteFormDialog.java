import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ClienteFormDialog extends JDialog {
    private JTextField tfNome;
    private JSpinner spIdade;
    private JCheckBox cbDevendo;
    private JComboBox<String> cbLivroAlugado; // trocado de JTextField para JComboBox
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
        cbLivroAlugado = new JComboBox<>();

        // popular combo com livros existentes (inclui "Nenhum")
        Catalogo c = new Catalogo();
        cbLivroAlugado.addItem("Nenhum");
        for (Livro l : c.getCatalogoLivros()) {
            cbLivroAlugado.addItem(l.getTitulo());
        }

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints cst = new GridBagConstraints();
        cst.insets = new Insets(6,6,6,6);
        cst.anchor = GridBagConstraints.WEST;

        cst.gridx = 0; cst.gridy = 0; form.add(new JLabel("Nome:"), cst);
        cst.gridx = 1; form.add(tfNome, cst);

        cst.gridx = 0; cst.gridy = 1; form.add(new JLabel("Idade:"), cst);
        cst.gridx = 1; form.add(spIdade, cst);

        cst.gridx = 0; cst.gridy = 2; form.add(new JLabel("Livro Alugado:"), cst);
        cst.gridx = 1; form.add(cbLivroAlugado, cst);

        cst.gridx = 0; cst.gridy = 3; cst.gridwidth = 2; form.add(cbDevendo, cst);

        JButton ok = new JButton("Salvar");
        ok.addActionListener((ActionEvent e) -> onSave());
        JButton cancel = new JButton("Cancelar");
        cancel.addActionListener((ActionEvent e) -> onCancel());

        JPanel bp = new JPanel(); bp.add(ok); bp.add(cancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }
    public void setCliente(Cliente cliente) {
        if (cliente == null) {
            tfNome.setText("");
            spIdade.setValue(18);
            cbDevendo.setSelected(false);
            // seleciona "Nenhum"
            cbLivroAlugado.setSelectedItem("Nenhum");
        } else {
            tfNome.setText(cliente.getNome());
            spIdade.setValue(cliente.getIdade());
            cbDevendo.setSelected(cliente.getStatusDevendo());
            String livro = cliente.getLivroAlugado();
            if (livro == null || livro.trim().isEmpty()) livro = "Nenhum";
            // se o livro não estiver presente no combo (por alguma inconsistência), adiciona temporariamente
            boolean found = false;
            for (int i = 0; i < cbLivroAlugado.getItemCount(); i++) {
                if (cbLivroAlugado.getItemAt(i).equalsIgnoreCase(livro)) { found = true; break; }
            }
            if (!found && !livro.equals("Nenhum")) {
                cbLivroAlugado.addItem(livro);
            }
            cbLivroAlugado.setSelectedItem(livro);
        }
    }
    public Cliente getClienteFromForm() {
        String nome = tfNome.getText().trim();
        int idade = (Integer) spIdade.getValue();
        boolean devendo = cbDevendo.isSelected();
        String livro = (String) cbLivroAlugado.getSelectedItem();
        if (livro == null || livro.trim().isEmpty()) livro = "Nenhum";
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
