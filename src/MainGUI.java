import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Optional;

public class MainGUI {
    private final GUIService gui;
    private final JFrame frame;

    private final LivroTableModel livroModel = new LivroTableModel();
    private final ClienteTableModel clienteModel = new ClienteTableModel();
    private final RequisicaoTableModel requisicaoModel = new RequisicaoTableModel();
    private final EmprestimoTableModel emprestimoModel = new EmprestimoTableModel();

    public MainGUI(GUIService gui) {
        this.gui = gui;
        this.frame = new JFrame("Biblioteca - Painel");
        init();
    }

    private void init() {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1100, 700);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabs = new JTabbedPane();

        JPanel panelLivros = new JPanel(new BorderLayout());
        JTable tblLivros = new JTable(livroModel);
        panelLivros.add(new JScrollPane(tblLivros), BorderLayout.CENTER);

        JPanel topLivros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshLivros = new JButton("Atualizar");
        JButton btnNovoLivro = new JButton("Novo");
        JButton btnEditarLivro = new JButton("Editar");
        JButton btnExcluirLivro = new JButton("Excluir");
        topLivros.add(btnRefreshLivros);
        topLivros.add(btnNovoLivro);
        topLivros.add(btnEditarLivro);
        topLivros.add(btnExcluirLivro);
        panelLivros.add(topLivros, BorderLayout.NORTH);

        JPanel panelClientes = new JPanel(new BorderLayout());
        JTable tblClientes = new JTable(clienteModel);
        panelClientes.add(new JScrollPane(tblClientes), BorderLayout.CENTER);

        JPanel topClientes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshClientes = new JButton("Atualizar");
        JButton btnNovoCliente = new JButton("Novo");
        JButton btnEditarCliente = new JButton("Editar");
        JButton btnExcluirCliente = new JButton("Excluir");
        topClientes.add(btnRefreshClientes);
        topClientes.add(btnNovoCliente);
        topClientes.add(btnEditarCliente);
        topClientes.add(btnExcluirCliente);
        panelClientes.add(topClientes, BorderLayout.NORTH);

        JPanel panelFuncionarios = new JPanel(new BorderLayout());
        DefaultListModel<String> listFuncModel = new DefaultListModel<>();
        JList<String> listFuncionarios = new JList<>(listFuncModel);
        panelFuncionarios.add(new JScrollPane(listFuncionarios), BorderLayout.CENTER);

        JPanel topFuncionarios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshFunc = new JButton("Atualizar");
        JButton btnNovoFunc = new JButton("Novo");
        JButton btnExcluirFunc = new JButton("Excluir");
        topFuncionarios.add(btnRefreshFunc);
        topFuncionarios.add(btnNovoFunc);
        topFuncionarios.add(btnExcluirFunc);
        panelFuncionarios.add(topFuncionarios, BorderLayout.NORTH);

        JPanel panelReq = new JPanel(new BorderLayout());
        JTable tblReq = new JTable(requisicaoModel);
        panelReq.add(new JScrollPane(tblReq), BorderLayout.CENTER);

        JPanel topReq = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshReq = new JButton("Atualizar");
        JButton btnNovaReq = new JButton("Criar");
        JButton btnProcessarReq = new JButton("Processar Selecionada");
        topReq.add(btnRefreshReq);
        topReq.add(btnNovaReq);
        topReq.add(btnProcessarReq);
        panelReq.add(topReq, BorderLayout.NORTH);

        JPanel panelEmp = new JPanel(new BorderLayout());
        JTable tblEmp = new JTable(emprestimoModel);
        panelEmp.add(new JScrollPane(tblEmp), BorderLayout.CENTER);

        JPanel topEmp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRefreshEmp = new JButton("Atualizar");
        JButton btnEmprestar = new JButton("Criar Empréstimo (seleção)");
        JButton btnDevolver = new JButton("Registrar Devolução");
        topEmp.add(btnRefreshEmp);
        topEmp.add(btnEmprestar);
        topEmp.add(btnDevolver);
        panelEmp.add(topEmp, BorderLayout.NORTH);

        tabs.addTab("Livros", panelLivros);
        tabs.addTab("Clientes", panelClientes);
        tabs.addTab("Funcionários", panelFuncionarios);
        tabs.addTab("Requisições", panelReq);
        tabs.addTab("Empréstimos", panelEmp);
        JMenuBar menuBar = new JMenuBar();

        JMenu menuEventos = new JMenu("Eventos");

        JMenuItem itemListar = new JMenuItem("Listar / Gerenciar");
        JMenuItem itemNovo = new JMenuItem("Criar Evento");

        itemListar.addActionListener(e -> {
            PainelEventos painel = new PainelEventos(frame, gui);
            painel.setVisible(true);
            JOptionPane.showMessageDialog(frame, painel, "Eventos", JOptionPane.PLAIN_MESSAGE);
        });

        itemNovo.addActionListener(e -> {
            EventoCrudDialog dlg = new EventoCrudDialog(frame, gui, null);
            dlg.setVisible(true);
        });

        menuEventos.add(itemListar);
        menuEventos.add(itemNovo);
        menuBar.add(menuEventos);

        frame.setJMenuBar(menuBar);

        frame.add(tabs, BorderLayout.CENTER);

        btnRefreshLivros.addActionListener(e -> atualizarLivros());

        btnNovoLivro.addActionListener(e -> {
            LivroDialog dlg = LivroDialog.showDialog(frame);
            if (dlg.isConfirmado()) {
                Livro novo = dlg.getResultado();
                gui.adicionarLivro(novo.getTitulo(), novo.getAutor(), novo.isRaro(), novo.getTotalCopias());
                atualizarLivros();
                JOptionPane.showMessageDialog(frame, "Livro criado.");
            }
        });

        // =============================
        // 🔥 AQUI ESTAVA O ERRO — CORRIGIDO
        // =============================

        btnEditarLivro.addActionListener(e -> {
            int sel = tblLivros.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um livro."); return; }

            Livro selecionado = livroModel.getLivroAt(sel);

            String novoTitulo = JOptionPane.showInputDialog(frame, "Título:", selecionado.getTitulo());
            if (novoTitulo == null) return;

            String novoAutor = JOptionPane.showInputDialog(frame, "Autor:", selecionado.getAutor());
            if (novoAutor == null) return;

            int novasCopias;
            try {
                String sc = JOptionPane.showInputDialog(frame, "Total de cópias:", String.valueOf(selecionado.getTotalCopias()));
                if (sc == null) return;
                novasCopias = Integer.parseInt(sc);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Número inválido.");
                return;
            }

            boolean raro = ConfirmDialog.confirm(frame, "Marcar como raro?");

            // NOVO LIVRO CORRIGIDO
            Livro atualizado = new Livro(
                    selecionado.getId(),
                    novoTitulo,
                    novoAutor,
                    raro,
                    novasCopias,
                    selecionado.getCopiasDisponiveis()
            );

            try {
                RepositorioLivroCsv repo = new RepositorioLivroCsv("data/livros.csv");
                List<Livro> todos = repo.findAll();
                todos.removeIf(l -> l.getId().equals(atualizado.getId()));
                todos.add(atualizado);
                repo.salvarTodos(todos);
                atualizarLivros();
                JOptionPane.showMessageDialog(frame, "Livro atualizado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar: " + ex.getMessage());
            }
        });

        btnExcluirLivro.addActionListener(e -> {
            int sel = tblLivros.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um livro."); return; }
            Livro l = livroModel.getLivroAt(sel);
            if (!ConfirmDialog.confirm(frame, "Excluir livro \"" + l.getTitulo() + "\"?")) return;
            try {
                RepositorioLivroCsv repo = new RepositorioLivroCsv("data/livros.csv");
                List<Livro> todos = repo.findAll();
                todos.removeIf(x -> x.getId().equals(l.getId()));
                repo.salvarTodos(todos);
                atualizarLivros();
                JOptionPane.showMessageDialog(frame, "Livro excluído.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao excluir: " + ex.getMessage());
            }
        });

        btnRefreshClientes.addActionListener(e -> atualizarClientes());
        btnNovoCliente.addActionListener(e -> {
            ClienteDialog dlg = ClienteDialog.showDialog(frame);
            if (!dlg.isConfirmado()) return;
            Cliente.TipoCliente tipo = dlg.getTipo();
            Cliente novo = gui.registrarCliente(dlg.getNome(), dlg.getIdade(), dlg.getUsuario(), dlg.getSenha(), tipo, dlg.isAplicarHash());
            atualizarClientes();
            JOptionPane.showMessageDialog(frame, "Cliente criado: " + novo.getNome());
        });

        btnEditarCliente.addActionListener(e -> {
            int sel = tblClientes.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um cliente."); return; }
            Cliente c = clienteModel.getClienteAt(sel);
            String novoNome = JOptionPane.showInputDialog(frame, "Nome:", c.getNome());
            if (novoNome == null) return;
            String idadeS = JOptionPane.showInputDialog(frame, "Idade:", String.valueOf(c.getIdade()));
            if (idadeS == null) return;
            int novaIdade;
            try { novaIdade = Integer.parseInt(idadeS); } catch (Exception ex) { JOptionPane.showMessageDialog(frame, "Idade inválida"); return; }
            c.setNome(novoNome);
            c.setIdade(novaIdade);
            try {
                RepositorioClienteCsv repo = new RepositorioClienteCsv("data/clientes.csv");
                List<Cliente> todos = repo.findAll();
                todos.removeIf(x -> x.getId() == c.getId());
                todos.add(c);
                repo.salvarTodos(todos);
                atualizarClientes();
                JOptionPane.showMessageDialog(frame, "Cliente atualizado.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao atualizar cliente: " + ex.getMessage());
            }
        });

        btnExcluirCliente.addActionListener(e -> {
            int sel = tblClientes.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um cliente."); return; }
            Cliente c = clienteModel.getClienteAt(sel);
            if (!ConfirmDialog.confirm(frame, "Excluir cliente \"" + c.getNome() + "\"?")) return;
            try {
                RepositorioClienteCsv repo = new RepositorioClienteCsv("data/clientes.csv");
                List<Cliente> todos = repo.findAll();
                todos.removeIf(x -> x.getId() == c.getId());
                repo.salvarTodos(todos);
                atualizarClientes();
                JOptionPane.showMessageDialog(frame, "Cliente excluído.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao excluir cliente: " + ex.getMessage());
            }
        });

        btnRefreshFunc.addActionListener(e -> { atualizarFuncionarios(listFuncModel); });
        btnNovoFunc.addActionListener(e -> {
            FuncionarioDialog dlg = FuncionarioDialog.showDialog(frame);
            if (!dlg.isConfirmado()) return;
            Funcionario f = gui.registrarFuncionario(dlg.getNome(), dlg.getIdade(), dlg.getUsuario(), dlg.getSenha(), dlg.isAplicarHash());
            atualizarFuncionarios(listFuncModel);
            JOptionPane.showMessageDialog(frame, "Funcionário criado: " + f.getNome());
        });

        btnExcluirFunc.addActionListener(e -> {
            int sel = listFuncionarios.getSelectedIndex();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um funcionário."); return; }
            String linha = listFuncModel.get(sel);
            String[] partes = linha.split(" - ", 2);
            int id = Integer.parseInt(partes[0]);
            if (!ConfirmDialog.confirm(frame, "Excluir funcionário ID " + id + "?")) return;
            try {
                RepositorioFuncionarioCsv repo = new RepositorioFuncionarioCsv("data/funcionarios.csv");
                List<Funcionario> todos = repo.findAll();
                todos.removeIf(x -> x.getId() == id);
                repo.salvarTodos(todos);
                atualizarFuncionarios(listFuncModel);
                JOptionPane.showMessageDialog(frame, "Funcionário excluído.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao excluir funcionário: " + ex.getMessage());
            }
        });

        btnRefreshReq.addActionListener(e -> atualizarRequisicoes());
        btnNovaReq.addActionListener(e -> {
            RequisicaoDialog dlg = RequisicaoDialog.showDialog(frame);
            if (!dlg.isConfirmado()) return;
            Optional<String> res = gui.criarRequisicao(dlg.getClienteId(), dlg.getLivroId());
            if (res.isPresent()) JOptionPane.showMessageDialog(frame, "Erro: " + res.get());
            else JOptionPane.showMessageDialog(frame, "Requisição criada.");
            atualizarRequisicoes();
        });

        btnProcessarReq.addActionListener(e -> {
            int sel = tblReq.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione uma requisição."); return; }
            Requisicao r = requisicaoModel.getRequisicaoAt(sel);
            int resp = JOptionPane.showConfirmDialog(frame, "Aprovar requisição " + r.getId() + "?", "Processar", JOptionPane.YES_NO_CANCEL_OPTION);
            if (resp == JOptionPane.CLOSED_OPTION || resp == JOptionPane.CANCEL_OPTION) return;
            gui.processarRequisicao(String.valueOf(r.getId()), resp == JOptionPane.YES_OPTION);
            atualizarRequisicoes();
            atualizarLivros();
            atualizarEmprestimos();
        });

        btnRefreshEmp.addActionListener(e -> atualizarEmprestimos());

        btnEmprestar.addActionListener(e -> {
            String idClienteS = JOptionPane.showInputDialog(frame, "ID do cliente:");
            if (idClienteS == null) return;
            String idLivro = JOptionPane.showInputDialog(frame, "ID do livro:");
            if (idLivro == null) return;
            try {
                int idCliente = Integer.parseInt(idClienteS);
                Optional<String> r = gui.criarEmprestimo(idCliente, idLivro);
                if (r.isPresent()) JOptionPane.showMessageDialog(frame, "Erro: " + r.get());
                else JOptionPane.showMessageDialog(frame, "Empréstimo criado.");
                atualizarEmprestimos();
                atualizarLivros();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "ID do cliente inválido.");
            }
        });

        btnDevolver.addActionListener(e -> {
            int sel = tblEmp.getSelectedRow();
            if (sel == -1) { JOptionPane.showMessageDialog(frame, "Selecione um empréstimo."); return; }
            Emprestimo emp = emprestimoModel.getEmprestimoAt(sel);
            if (!ConfirmDialog.confirm(frame, "Registrar devolução do empréstimo " + emp.getId() + " ?")) return;
            Optional<String> r = gui.devolverEmprestimo(emp.getId());
            if (r.isPresent()) JOptionPane.showMessageDialog(frame, "Erro: " + r.get());
            else JOptionPane.showMessageDialog(frame, "Devolução registrada.");
            atualizarEmprestimos();
            atualizarLivros();
        });

        atualizarLivros();
        atualizarClientes();
        atualizarFuncionarios(listFuncModel);
        atualizarRequisicoes();
        atualizarEmprestimos();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void atualizarLivros() { livroModel.setDados(gui.listarLivros()); }
    private void atualizarClientes() { clienteModel.setDados(gui.listarClientes()); }
    private void atualizarFuncionarios(DefaultListModel<String> listModel) {
        listModel.clear();
        for (Funcionario ff : gui.listarFuncionarios())
            listModel.addElement(ff.getId() + " - " + ff.getNome());
    }
    private void atualizarRequisicoes() { requisicaoModel.setDados(gui.listarRequisicoesPendentes()); }
    private void atualizarEmprestimos() { emprestimoModel.setDados(gui.listarEmprestimos()); }

    public static void main(String[] args) {
        BibliotecaServico svc = new BibliotecaServico(
                "data/clientes.csv",
                "data/funcionarios.csv",
                "data/livros.csv",
                "data/requisicoes.csv",
                "data/emprestimos.csv",
                "data/sessoes.csv",
                "data/pagamentos.csv",
                "data/eventos.csv",
                "data/notificacoes.csv"
        );
        GUIService gui = new GUIService(svc);
        SwingUtilities.invokeLater(() -> new MainGUI(gui));
    }
}
