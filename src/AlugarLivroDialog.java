import javax.swing.*;
import java.awt.*;

public class AlugarLivroDialog extends JDialog {
    private boolean confirmado = false;
    private JList<String> listaLivros;
    private String livroSelecionadoId = null;

    public AlugarLivroDialog(Frame owner) {
        super(owner, "Alugar / Requisitar Livro", true);
        init();
    }

    private void init() {
        setLayout(new BorderLayout(8, 8));
        setPreferredSize(new Dimension(600, 400));

        RepositorioLivroCsv repo = new RepositorioLivroCsv("data/livros.csv");
        Catalogo catalogo = new Catalogo(repo);

        DefaultListModel<String> lm = new DefaultListModel<>();

        java.util.List<Livro> livros = catalogo.getLivrosCadastrados();
        if (livros == null || livros.isEmpty()) { livros = repo.findAll(); }

        for (Livro l : livros) {
            lm.addElement(l.getId() + " - " + l.getTitulo() + " (" + l.getAutor() + ") [disponíveis: " + l.getCopiasDisponiveis() + "]");
        }

        listaLivros = new JList<>(lm);
        listaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaLivros), BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAlugar = new JButton("Alugar / Requisitar");
        JButton btnCancelar = new JButton("Cancelar");
        botoes.add(btnAlugar);
        botoes.add(btnCancelar);
        add(botoes, BorderLayout.SOUTH);

        btnAlugar.addActionListener(e -> {
            int sel = listaLivros.getSelectedIndex();
            if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um livro."); return; }
            String linha = lm.get(sel);
            String[] partes = linha.split(" - ", 2);
            livroSelecionadoId = partes[0];
            confirmado = true;
            setVisible(false);
        });

        btnCancelar.addActionListener(e -> { confirmado = false; livroSelecionadoId = null; setVisible(false); });

        pack();
        setLocationRelativeTo(getOwner());
    }

    public boolean isConfirmado() { return confirmado; }
    public String getLivroSelecionadoId() { return livroSelecionadoId; }

    public static AlugarLivroDialog showDialog(Frame owner) {
        AlugarLivroDialog d = new AlugarLivroDialog(owner);
        d.setVisible(true);
        return d;
    }
}
