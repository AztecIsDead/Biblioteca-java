import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MainFrame extends JFrame {
    private LivroService livroService;
    private ClienteService clienteService;
    private FuncionarioService funcionarioService;

    private LivroModeloDeTable livroTableModel;
    private JTable livroTable;

    private ClienteModeloDeTable clienteTableModel;
    private JTable clienteTable;
    private boolean mostrarApenasDevedores = false;

    private FuncionarioModeloDeTable funcionarioTableModel;
    private JTable funcionarioTable;

    public MainFrame() {
        super("Sistema Biblioteca - Swing");
        livroService = new LivroService();
        clienteService = new ClienteService();
        funcionarioService = new FuncionarioService();
        initUI();
        loadAllInBackground();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Livros", criarPainelLivros());
        tabs.addTab("Clientes", criarPainelClientes());
        tabs.addTab("Funcionários", criarPainelFuncionarios());
        getContentPane().add(tabs, BorderLayout.CENTER);
    }

    private JPanel criarPainelLivros() {
        JPanel panel = new JPanel(new BorderLayout());
        livroTableModel = new LivroModeloDeTable(livroService.findAll());
        livroTable = new JTable(livroTableModel);
        livroTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(livroTable), BorderLayout.CENTER);

        JButton novo = new JButton("Novo");
        novo.addActionListener(e -> onNovoLivro());
        JButton editar = new JButton("Editar");
        editar.addActionListener(e -> onEditarLivro());
        JButton excluir = new JButton("Excluir");
        excluir.addActionListener(e -> onExcluirLivro());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(novo); top.add(editar); top.add(excluir);
        panel.add(top, BorderLayout.NORTH);
        return panel;
    }

    private void onNovoLivro() {
        LivroFormDialog dlg = new LivroFormDialog(this);
        dlg.setLivro(null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Livro novo = dlg.getLivroFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { livroService.save(novo); return null;}
                @Override protected void done() { livroTableModel.setLivros(livroService.findAll()); }
            }.execute();
        }
    }

    private void onEditarLivro() {
        int row = livroTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um livro."); return; }
        Livro l = livroTableModel.getLivroAt(row);
        LivroFormDialog dlg = new LivroFormDialog(this);
        dlg.setLivro(l);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Livro atualizado = dlg.getLivroFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { livroService.save(atualizado); return null; }
                @Override protected void done() { livroTableModel.setLivros(livroService.findAll()); }
            }.execute();
        }
    }

    private void onExcluirLivro() {
        int row = livroTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um livro."); return; }
        Livro l = livroTableModel.getLivroAt(row);
        int r = JOptionPane.showConfirmDialog(this, "Excluir livro \""+l.getTitulo()+"\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { livroService.delete(l); return null; }
                @Override protected void done() { livroTableModel.setLivros(livroService.findAll()); }
            }.execute();
        }
    }
    private JPanel criarPainelClientes() {
        JPanel panel = new JPanel(new BorderLayout());
        clienteTableModel = new ClienteModeloDeTable(clienteService.findAll());
        clienteTable = new JTable(clienteTableModel);
        clienteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(clienteTable), BorderLayout.CENTER);

        JButton novo = new JButton("Novo");
        novo.addActionListener(e -> onNovoCliente());
        JButton editar = new JButton("Editar");
        editar.addActionListener(e -> onEditarCliente());
        JButton excluir = new JButton("Excluir");
        excluir.addActionListener(e -> onExcluirCliente());

        JToggleButton filtrarDevedores = new JToggleButton("Mostrar apenas devedores");
        filtrarDevedores.addActionListener(e -> {
            mostrarApenasDevedores = filtrarDevedores.isSelected();
            atualizarClientesNaTabela();
        });
        JButton btnAlugar = new JButton("Alugar Livro");
        btnAlugar.addActionListener(e -> onAlugarLivro());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(novo); top.add(editar); top.add(excluir); top.add(filtrarDevedores); top.add(btnAlugar);
        panel.add(top, BorderLayout.NORTH);
        return panel;
    }
    private void onNovoCliente() {
        ClienteFormDialog dlg = new ClienteFormDialog(this);
        dlg.setCliente(null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Cliente novo = dlg.getClienteFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    new ClienteService().save(novo);
                    String livroEscolhido = novo.getLivroAlugado();
                    if (livroEscolhido != null && !livroEscolhido.equalsIgnoreCase("Nenhum")) {
                        Catalogo c = new Catalogo();
                        boolean ok = c.alugarLivroParaCliente(novo.getNome(), livroEscolhido);
                        if (!ok) {
                            System.out.println("Aluguel falhou para " + novo.getNome() + " / " + livroEscolhido);
                        }
                    }
                    return null;
                }
                @Override protected void done() {
                    clienteService.loadAll();
                    livroService.loadAll();
                    atualizarClientesNaTabela();
                    livroTableModel.setLivros(livroService.findAll());
                }
            }.execute();
        }
    }
    private void onEditarCliente() {
        int row = clienteTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um cliente."); return; }
        Cliente antigo = clienteTableModel.getClienteAt(row);
        String livroAntigo = antigo.getLivroAlugado() == null ? "Nenhum" : antigo.getLivroAlugado();
        ClienteFormDialog dlg = new ClienteFormDialog(this);
        dlg.setCliente(antigo);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Cliente atualizado = dlg.getClienteFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    new ClienteService().save(atualizado);
                    String livroNovo = atualizado.getLivroAlugado() == null ? "Nenhum" : atualizado.getLivroAlugado();
                    Catalogo c = new Catalogo();
                    if (!"Nenhum".equalsIgnoreCase(livroAntigo) && !livroAntigo.equalsIgnoreCase(livroNovo)) {
                        Livro lOld = c.buscarLivroTitulo(livroAntigo);
                        if (lOld != null) {
                            lOld.setDisponibilidade(true);
                            c.atualizarCatalogo();
                        }
                        Cliente clienteDoArquivo = c.buscarClienteNome(atualizado.getNome());
                        if (clienteDoArquivo != null) {
                            clienteDoArquivo.setStatusDevendo(false);
                            clienteDoArquivo.setLivroAlugado("Nenhum");
                            c.atualizarClientes();
                        }
                    }
                    if (!"Nenhum".equalsIgnoreCase(livroNovo) && !livroNovo.equalsIgnoreCase(livroAntigo)) {
                        boolean ok = c.alugarLivroParaCliente(atualizado.getNome(), livroNovo);
                        if (!ok) {
                            System.out.println("Falha ao alugar novo livro: " + livroNovo + " para " + atualizado.getNome());
                        }
                    }
                    return null;
                }
                @Override protected void done() {
                    clienteService.loadAll();
                    livroService.loadAll();
                    atualizarClientesNaTabela();
                    livroTableModel.setLivros(livroService.findAll());
                }
            }.execute();
        }
    }
    private void onExcluirCliente() {
        int row = clienteTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um cliente."); return; }
        Cliente c = clienteTableModel.getClienteAt(row);
        int r = JOptionPane.showConfirmDialog(this, "Excluir cliente \""+c.getNome()+"\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { new ClienteService().delete(c); return null; }
                @Override protected void done() { clienteService.loadAll(); atualizarClientesNaTabela(); }
            }.execute();
        }
    }

    private void atualizarClientesNaTabela() {
        if (!mostrarApenasDevedores) {
            clienteTableModel.setClientes(clienteService.findAll());
        } else {
            List<Cliente> devedores = clienteService.findAll().stream()
                    .filter(Cliente::getStatusDevendo)
                    .collect(Collectors.toList());
            clienteTableModel.setClientes(devedores);
        }
    }
    private void onAlugarLivro() {
        AlugarLivroDialog dlg = new AlugarLivroDialog(this);
        dlg.setVisible(true);
        if (dlg.isConfirmado()) {
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() {
                    livroService.loadAll();
                    clienteService.loadAll();
                    return null;
                }
                @Override protected void done() {
                    livroTableModel.setLivros(livroService.findAll());
                    atualizarClientesNaTabela();
                }
            }.execute();
        }
    }

    private JPanel criarPainelFuncionarios() {
        JPanel panel = new JPanel(new BorderLayout());
        funcionarioTableModel = new FuncionarioModeloDeTable(funcionarioService.findAll());
        funcionarioTable = new JTable(funcionarioTableModel);
        funcionarioTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(funcionarioTable), BorderLayout.CENTER);

        JButton novo = new JButton("Novo");
        novo.addActionListener(e -> onNovoFuncionario());
        JButton editar = new JButton("Editar");
        editar.addActionListener(e -> onEditarFuncionario());
        JButton excluir = new JButton("Excluir");
        excluir.addActionListener(e -> onExcluirFuncionario());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(novo); top.add(editar); top.add(excluir);
        panel.add(top, BorderLayout.NORTH);
        return panel;
    }

    private void onNovoFuncionario() {
        FuncionarioFormDialog dlg = new FuncionarioFormDialog(this);
        dlg.setFuncionario(null);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Funcionario novo = dlg.getFuncionarioFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { new FuncionarioService().save(novo); return null; }
                @Override protected void done() { funcionarioService.loadAll(); funcionarioTableModel.setFuncionarios(funcionarioService.findAll()); }
            }.execute();
        }
    }

    private void onEditarFuncionario() {
        int row = funcionarioTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um funcionário."); return; }
        Funcionario f = funcionarioTableModel.getFuncionarioAt(row);
        FuncionarioFormDialog dlg = new FuncionarioFormDialog(this);
        dlg.setFuncionario(f);
        dlg.setVisible(true);
        if (dlg.isSaved()) {
            Funcionario atualizado = dlg.getFuncionarioFromForm();
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { new FuncionarioService().save(atualizado); return null; }
                @Override protected void done() { funcionarioService.loadAll(); funcionarioTableModel.setFuncionarios(funcionarioService.findAll()); }
            }.execute();
        }
    }

    private void onExcluirFuncionario() {
        int row = funcionarioTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um funcionário."); return; }
        Funcionario f = funcionarioTableModel.getFuncionarioAt(row);
        int r = JOptionPane.showConfirmDialog(this, "Excluir funcionário \""+f.getNome()+"\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (r == JOptionPane.YES_OPTION) {
            new SwingWorker<Void, Void>() {
                @Override protected Void doInBackground() { new FuncionarioService().delete(f); return null; }
                @Override protected void done() { funcionarioService.loadAll(); funcionarioTableModel.setFuncionarios(funcionarioService.findAll()); }
            }.execute();
        }
    }
    private void loadAllInBackground() {
        new SwingWorker<Void, Void>() {
            @Override protected Void doInBackground() {
                livroService.loadAll();
                clienteService.loadAll();
                funcionarioService.loadAll();
                return null;
            }
            @Override protected void done() {
                livroTableModel.setLivros(livroService.findAll());
                clienteTableModel.setClientes(clienteService.findAll());
                funcionarioTableModel.setFuncionarios(funcionarioService.findAll());
            }
        }.execute();
    }
}
