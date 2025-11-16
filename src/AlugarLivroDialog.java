import javax.swing.*;
import java.awt.*;

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

        for (Cliente c : catalogo.getClientesCadastrados()) cbClientes.addItem(c.getNome());
        for (Livro l : catalogo.getCatalogoLivros()) if (l.getDisponibilidade()) cbLivrosDisponiveis.addItem(l.getTitulo());

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; p.add(new JLabel("Cliente:"), c);
        c.gridx = 1; p.add(cbClientes, c);

        c.gridx = 0; c.gridy = 1; p.add(new JLabel("Livro disponível:"), c);
        c.gridx = 1; p.add(cbLivrosDisponiveis, c);

        JButton btnOk = new JButton("Alugar");
        btnOk.addActionListener(e -> onAlugar());

        JButton btnCancel = new JButton("Cancelar");
        btnCancel.addActionListener(e -> onCancel());

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

        // pergunta o tipo de operação
        String[] options = {"Imediato (sem due date)", "Com prazo (definir dias)", "Cancelar"};
        int opt = JOptionPane.showOptionDialog(
                this,
                "Escolha o tipo de aluguel:",
                "Tipo de aluguel",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (opt == 2 || opt == JOptionPane.CLOSED_OPTION) return;

        boolean ok = false;
        if (opt == 0) { // imediato
            ok = catalogo.alugarLivroParaCliente(nomeCliente, tituloLivro);
        } else if (opt == 1) { // com prazo -> pergunta dias e usa approveRequest
            String s = JOptionPane.showInputDialog(this, "Prazo em dias (ex: 14):", "14");
            if (s == null) return;
            int days = 14;
            try { days = Integer.parseInt(s); } catch (Exception ex) { days = 14; }
            ok = catalogo.approveRequest(nomeCliente, tituloLivro, days);
        }

        if (ok) {
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso para " + nomeCliente + "!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            confirmado = true;
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível completar a operação. Verifique cliente/livro/estado.", "Erro", JOptionPane.ERROR_MESSAGE);
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
