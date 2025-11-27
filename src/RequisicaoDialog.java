import javax.swing.*;
import java.awt.*;

public class RequisicaoDialog extends JDialog {
    private boolean confirmado = false;
    private JSpinner spClienteId = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
    private JTextField txtLivroId = new JTextField(30);

    public RequisicaoDialog(Frame owner) {
        super(owner, "Criar Requisição", true);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(2,2,5,5));
        p.add(new JLabel("ID Cliente:")); p.add(spClienteId);
        p.add(new JLabel("ID Livro:")); p.add(txtLivroId);
        add(p, BorderLayout.CENTER);

        JPanel btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");
        btn.add(ok); btn.add(cancel);
        add(btn, BorderLayout.SOUTH);

        ok.addActionListener(e -> { confirmado = true; setVisible(false); });
        cancel.addActionListener(e -> { confirmado = false; setVisible(false); });

        pack(); setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() { return confirmado; }
    public int getClienteId() { return (Integer) spClienteId.getValue(); }
    public String getLivroId() { return txtLivroId.getText(); }

    public static RequisicaoDialog showDialog(Frame owner) {
        RequisicaoDialog d = new RequisicaoDialog(owner);
        d.setVisible(true);
        return d;
    }
}
