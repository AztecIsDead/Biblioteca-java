import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.Timer;

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

    private RequestTableModel requestTableModel;
    private JTable requestTable;

    private JLabel lblSessionTime;
    private Timer sessionTimer;
    private long sessionSeconds = 0;
    private String funcionarioLogado;

    public MainFrame(String funcionarioLogado) {
        super("Sistema Biblioteca - Swing (Admin)");
        this.funcionarioLogado = funcionarioLogado;

        livroService = new LivroService();
        clienteService = new ClienteService();
        funcionarioService = new FuncionarioService();

        initUI();
        loadAllInBackground();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                stopSessionTimer();
            }
        });

        startSessionTimer();
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Livros", criarPainelLivros());
        tabs.addTab("Clientes", criarPainelClientes());
        tabs.addTab("Funcionários", criarPainelFuncionarios());
        tabs.addTab("Pedidos", criarPainelPedidos());

        getContentPane().add(tabs, BorderLayout.CENTER);

        lblSessionTime = new JLabel("Tempo total do funcionário: 00:00:00");
        JPanel barraStatus = new JPanel(new FlowLayout(FlowLayout.LEFT));
        barraStatus.add(lblSessionTime);
        getContentPane().add(barraStatus, BorderLayout.SOUTH);
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
        JButton btnAlugar = new JButton("Alugar livro (imediato)");
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
                        boolean ok = c.approveRequest(novo.getNome(), livroEscolhido, 14);
                        if (!ok) System.out.println("Falha em aprovar aluguel");
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
                        boolean ok = c.approveRequest(atualizado.getNome(), livroNovo, 14);
                        if (!ok) System.out.println("Falha ao aprovar novo livro");
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
                @Override protected Void doInBackground() {
                    Catalogo cat = new Catalogo();
                    Cliente fromFile = cat.buscarClienteNome(c.getNome());
                    if (fromFile != null) {
                        String livro = fromFile.getLivroAlugado();
                        if (livro != null && !livro.equalsIgnoreCase("Nenhum")) {
                            Livro l = cat.buscarLivroTitulo(livro);
                            if (l != null) { l.setDisponibilidade(true); cat.atualizarCatalogo(); }
                        }
                    }
                    new ClienteService().delete(c);
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

    private void atualizarClientesNaTabela() {
        if (!mostrarApenasDevedores) clienteTableModel.setClientes(clienteService.findAll());
        else {
            List<Cliente> devedores = clienteService.findAll().stream().filter(Cliente::getStatusDevendo).collect(Collectors.toList());
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

    private JPanel criarPainelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        requestTableModel = new RequestTableModel(new java.util.ArrayList<>());
        requestTable = new JTable(requestTableModel);
        requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        panel.add(new JScrollPane(requestTable), BorderLayout.CENTER);

        JButton btnRefresh = new JButton("Atualizar");
        btnRefresh.addActionListener(e -> loadRequests());

        JButton btnAprovar = new JButton("Aprovar");
        btnAprovar.addActionListener(e -> onAprovarPedido());

        JButton btnRejeitar = new JButton("Rejeitar");
        btnRejeitar.addActionListener(e -> onRejeitarPedido());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnRefresh); top.add(btnAprovar); top.add(btnRejeitar);
        panel.add(top, BorderLayout.NORTH);

        return panel;
    }

    private void loadRequests() {
        Catalogo c = new Catalogo();
        java.util.List<Request> pendentes = c.getPendingRequests();
        requestTableModel.setRequests(pendentes);
    }

    private void onAprovarPedido() {
        int row = requestTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um pedido."); return; }
        Request r = requestTableModel.getRequestAt(row);
        String s = JOptionPane.showInputDialog(this, "Prazo em dias (ex: 14):", "14");
        if (s == null) return;
        int days = 14;
        try { days = Integer.parseInt(s); } catch (Exception ex) { days = 14; }
        Catalogo c = new Catalogo();
        boolean ok = c.approveRequest(r.getClienteNome(), r.getTituloLivro(), days);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido aprovado.");
            livroService.loadAll();
            clienteService.loadAll();
            loadRequests();
            livroTableModel.setLivros(livroService.findAll());
            atualizarClientesNaTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao aprovar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onRejeitarPedido() {
        int row = requestTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Selecione um pedido."); return; }
        Request r = requestTableModel.getRequestAt(row);
        Catalogo c = new Catalogo();
        boolean ok = c.rejectRequest(r.getClienteNome(), r.getTituloLivro());
        if (ok) {
            JOptionPane.showMessageDialog(this, "Pedido rejeitado.");
            loadRequests();
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao rejeitar.", "Erro", JOptionPane.ERROR_MESSAGE);
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
                loadRequests();
            }
        }.execute();
    }


    private void startSessionTimer() {
        sessionSeconds = SessionCSV.loadTotalSeconds(funcionarioLogado);
        updateSessionLabel();

        sessionTimer = new Timer(1000, e -> {
            sessionSeconds++;
            updateSessionLabel();
        });
        sessionTimer.start();
    }

    private void stopSessionTimer() {
        if (sessionTimer != null) sessionTimer.stop();
        SessionCSV.saveTotalSeconds(funcionarioLogado, sessionSeconds);
    }

    private void updateSessionLabel() {
        long h = sessionSeconds / 3600;
        long m = (sessionSeconds % 3600) / 60;
        long s = sessionSeconds % 60;
        lblSessionTime.setText(String.format("Tempo total do funcionário: %02d:%02d:%02d", h, m, s));
    }
}
