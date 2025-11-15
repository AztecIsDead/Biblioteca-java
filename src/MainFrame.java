import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private LivroService service;
    private LivroModeloDeTable tableModel;
    private JTable table;

    public MainFrame() {
        super("Sistema Biblioteca - Swing");
        service = new LivroService();
        initUI();
        loadDataInBackground();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        List<Livro> livros = service.findAll();
        tableModel = new LivroModeloDeTable(livros);
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(table);

        JButton btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> onNovo());

        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(e -> onEditar());

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> onExcluir());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(btnNovo);
        topPanel.add(btnEditar);
        topPanel.add(btnExcluir);

        getContentPane().setLayout(new BorderLayout(6,6));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
    }

    private void onNovo() {
        LivroFormDialog dlg = new LivroFormDialog(this);
        dlg.setLivro(null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Livro novo = dlg.getLivroFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    service.save(novo);
                    return null;
                }
                @Override protected void done() {
                    tableModel.setLivros(service.findAll());
                }
            }.execute();
        }
    }

    private void onEditar() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para editar.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Livro l = tableModel.getLivroAt(row);
        LivroFormDialog dlg = new LivroFormDialog(this);
        dlg.setLivro(l);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Livro atualizado = dlg.getLivroFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    service.save(atualizado);
                    return null;
                }
                @Override protected void done() {
                    tableModel.setLivros(service.findAll());
                }
            }.execute();
        }
    }

    private void onExcluir() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para excluir.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Livro l = tableModel.getLivroAt(row);
        int resp = JOptionPane.showConfirmDialog(this, "Excluir o livro \"" + l.getTitulo() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    service.delete(l);
                    return null;
                }
                @Override protected void done() {
                    tableModel.setLivros(service.findAll());
                }
            }.execute();
        }
    }

    private void loadDataInBackground() {
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() {
                service.loadAll();
                return null;
            }
            @Override protected void done() {
                tableModel.setLivros(service.findAll());
            }
        }.execute();
    }
}
