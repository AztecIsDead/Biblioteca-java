import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.stream.Collectors;

public class AlugarLivroDialog extends JDialog {
    private JComboBox<String> cbClientes;
    private JComboBox<String> cbLivrosDisponiveis;
    private boolean confirmado = false;
    private Catalogo catalogo;

    public AlugarLivroDialog(Frame owner) {
        super(owner, true);
        setTitle("Alugar Livro");
        catalogo = new Catalogo();
        initComponents();
        pack();
        setLocationRelativeTo(owner);
    }
    private void initComponents() {
        cbClientes = new JComboBox<>();
        cbLivrosDisponiveis = new JComboBox<>();
        for (Cliente c : catalogo.getClientesCadastrados()) {
            cbClientes.addItem(c.getNome());
        }

        for (Livro l : catalogo.getCatalogoLivros()) {
            if (l.getDisponibilidade()) {
                cbLivrosDisponiveis.addItem(l.getTitulo());
            }
        }

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Cliente:"), c);
        c.gridx = 1; p.add(cbClientes, c);

        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Livro disponível:"), c);
        c.gridx = 1; p.add(cbLivrosDisponiveis, c);

        JButton btnOk = new JButton("Alugar");
        btnOk.addActionListener((ActionEvent e) -> onAlugar());

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener((ActionEvent e) -> onCancel());

        JPanel bp = new JPanel();
        bp.add(btnOk);
        bp.add(btnCancel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.SOUTH);
    }

    private void onAlugar() {
        String nomeCliente = (String) cbClientes.getSelectedItem();
        String tituloLivro = (String) cbLivrosDisponiveis.getSelectedItem();

        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tituloLivro == null || tituloLivro.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um livro disponível.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean ok = catalogo.alugarLivroParaCliente(nomeCliente, tituloLivro);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Livro alugado com sucesso para " + nomeCliente + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            confirmado = true;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível alugar. Verifique cliente/livro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        confirmado = false;
        setVisible(false);
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}
