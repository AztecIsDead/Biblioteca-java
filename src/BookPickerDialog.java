import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookPickerDialog extends JDialog {

    private String escolhido = null;

    public static String escolher(Frame owner, List<Livro> livros) {
        BookPickerDialog dlg = new BookPickerDialog(owner, livros);
        dlg.setVisible(true);
        return dlg.escolhido;
    }

    public BookPickerDialog(Frame owner, List<Livro> livros) {
        super(owner, "Escolher Livro", true);
        setSize(400, 300);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        DefaultListModel<String> model = new DefaultListModel<>();
        for (Livro l : livros) {
            model.addElement(l.getId() + " - " + l.getTitulo() + " (" + l.getAutor() + ")");
        }

        JList<String> list = new JList<>(model);
        add(new JScrollPane(list), BorderLayout.CENTER);

        JButton btnOk = new JButton("Selecionar");
        JButton btnCanc = new JButton("Cancelar");

        JPanel bottom = new JPanel();
        bottom.add(btnOk);
        bottom.add(btnCanc);
        add(bottom, BorderLayout.SOUTH);

        btnOk.addActionListener(e -> {
            int sel = list.getSelectedIndex();
            if (sel == -1) return;
            String linha = model.get(sel);
            escolhido = linha.split(" - ")[0];
            dispose();
        });

        btnCanc.addActionListener(e -> dispose());
    }
}
