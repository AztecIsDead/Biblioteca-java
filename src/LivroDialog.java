import javax.swing.*;
import java.awt.*;

public class LivroDialog extends JDialog {
    private boolean confirmado = false;
    private JTextField txtTitulo = new JTextField(30);
    private JTextField txtAutor = new JTextField(30);
    private JCheckBox chkRaro = new JCheckBox("Raro");
    private JSpinner spCopias = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
    private Livro resultado;

    public LivroDialog(Frame owner) {
        super(owner, "Livro", true);
        init();
    }

    private void init() {
        setLayout(new BorderLayout());
        JPanel p = new JPanel(new GridLayout(4,2,5,5));
        p.add(new JLabel("Título:")); p.add(txtTitulo);
        p.add(new JLabel("Autor:")); p.add(txtAutor);
        p.add(new JLabel("Raro:")); p.add(chkRaro);
        p.add(new JLabel("Cópias:")); p.add(spCopias);
        add(p, BorderLayout.CENTER);

        JPanel btn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancelar");
        btn.add(ok); btn.add(cancel);
        add(btn, BorderLayout.SOUTH);

        ok.addActionListener(e -> {
            confirmado = true;
            resultado = new Livro(txtTitulo.getText(), txtAutor.getText(), chkRaro.isSelected(), (Integer) spCopias.getValue());
            setVisible(false);
        });
        cancel.addActionListener(e -> {
            confirmado = false;
            setVisible(false);
        });

        pack();
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() { return confirmado; }
    public Livro getResultado() { return resultado; }

    public static LivroDialog showDialog(Frame owner) {
        LivroDialog d = new LivroDialog(owner);
        d.setVisible(true);
        return d;
    }
}
