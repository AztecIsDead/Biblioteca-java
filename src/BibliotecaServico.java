import java.time.LocalDate;
import java.util.*;

public class BibliotecaServico {

    final RepositorioClienteCsv repoClientes;
    final RepositorioFuncionarioCsv repoFuncionarios;
    final RepositorioLivroCsv repoLivros;
    final RepositorioRequisicaoCsv repoRequisicoes;
    final RepositorioEmprestimoCsv repoEmprestimos;
    final RepositorioPagamentoCsv repoPagamentos;
    final RepositorioEventoCsv repoEventos;
    final RepositorioNotificacaoCsv repoNotificacoes;
    final RepositorioSessaoCsv repoSessoes;

    public BibliotecaServico(String clientesCsv,
                             String funcionariosCsv,
                             String livrosCsv,
                             String requisicoesCsv,
                             String emprestimosCsv,
                             String sessoesCsv,
                             String pagamentosCsv,
                             String eventosCsv,
                             String notificacoesCsv) {

        this.repoClientes = new RepositorioClienteCsv(clientesCsv);
        this.repoFuncionarios = new RepositorioFuncionarioCsv(funcionariosCsv);
        this.repoLivros = new RepositorioLivroCsv(livrosCsv);
        this.repoRequisicoes = new RepositorioRequisicaoCsv(requisicoesCsv);
        this.repoEmprestimos = new RepositorioEmprestimoCsv(emprestimosCsv);
        this.repoPagamentos = new RepositorioPagamentoCsv(pagamentosCsv);
        this.repoEventos = new RepositorioEventoCsv(eventosCsv);
        this.repoNotificacoes = new RepositorioNotificacaoCsv(notificacoesCsv);
        this.repoSessoes = new RepositorioSessaoCsv(sessoesCsv);
    }

    public Optional<Cliente> autenticarCliente(String usuario, String senha) {
        return repoClientes.findAll().stream()
                .filter(c -> c.getUsuario().equals(usuario) && c.getSenhaHash().equals(senha))
                .findFirst();
    }

    public Optional<Funcionario> autenticarFuncionario(String usuario, String senha) {
        return repoFuncionarios.findAll().stream()
                .filter(f -> f.getUsuario().equals(usuario) && f.getSenhaHash().equals(senha))
                .findFirst();
    }

    public Cliente registrarCliente(String nome, int idade, String usuario, String senha, Cliente.TipoCliente tipo, boolean aplicarHash) {
        int id = repoClientes.nextId();
        String senhaFinal = senha; // sem hash
        Cliente c = new Cliente(id, nome, idade, usuario, senhaFinal, tipo);
        List<Cliente> todos = repoClientes.findAll();
        todos.add(c);
        repoClientes.salvarTodos(todos);
        return c;
    }

    public Funcionario registrarFuncionario(String nome, int idade, String usuario, String senha, boolean aplicarHash) {
        int id = repoFuncionarios.nextId();
        String senhaFinal = senha; // sem hash
        Funcionario f = new Funcionario(id, nome, idade, usuario, senhaFinal, "");
        List<Funcionario> todos = repoFuncionarios.findAll();
        todos.add(f);
        repoFuncionarios.salvarTodos(todos);
        return f;
    }

    public Livro adicionarLivro(String titulo, String autor, boolean raro, int totalCopias) {
        Livro l = new Livro(titulo, autor, raro, totalCopias);
        List<Livro> todos = repoLivros.findAll();
        todos.add(l);
        repoLivros.salvarTodos(todos);
        return l;
    }

    public List<Emprestimo> getEmprestimosDoCliente(int clienteId) {
        return repoEmprestimos.findAll()
                .stream()
                .filter(e -> e.getClienteId() == clienteId)
                .toList();
    }

    public List<Livro> todosLivros() {
        return repoLivros.findAll();
    }

    public List<Cliente> todosClientes() {
        return repoClientes.findAll();
    }

    public List<Funcionario> todosFuncionarios() {
        return repoFuncionarios.findAll();
    }

    public List<Requisicao> listarRequisicoesPendentes() {
        return repoRequisicoes.findAll().stream()
                .filter(r -> r.getStatus().equalsIgnoreCase("pendente"))
                .toList();
    }

    public List<Emprestimo> todosEmprestimos() {
        return repoEmprestimos.findAll();
    }

    public Optional<String> criarRequisicao(int clienteId, String livroId) {
        Optional<Cliente> c = repoClientes.findById(clienteId);
        if (c.isEmpty()) return Optional.of("Cliente não encontrado");

        Optional<Livro> l = repoLivros.findById(livroId);
        if (l.isEmpty()) return Optional.of("Livro não encontrado");

        if (l.get().isRaro() && !c.get().isVip()) return Optional.of("Somente VIP podem requisitar livros raros");

        List<Requisicao> todas = repoRequisicoes.findAll();
        int nextId = todas.stream().mapToInt(Requisicao::getId).max().orElse(0) + 1;

        Requisicao r = new Requisicao(nextId, clienteId, livroId, l.get().getTitulo(), "pendente", LocalDate.now());
        todas.add(r);
        repoRequisicoes.salvarTodos(todas);

        return Optional.empty();
    }

    public Optional<String> processarRequisicao(String id, boolean aprovar) {
        int idNum = Integer.parseInt(id);

        List<Requisicao> todas = repoRequisicoes.findAll();
        Optional<Requisicao> opt = todas.stream().filter(r -> r.getId() == idNum).findFirst();
        if (opt.isEmpty()) return Optional.of("Requisição não encontrada");

        Requisicao r = opt.get();

        if (!r.getStatus().equalsIgnoreCase("pendente"))
            return Optional.of("Requisição já processada");

        if (!aprovar) {
            r.setStatus("rejeitada");
            repoRequisicoes.salvarTodos(todas);
            return Optional.empty();
        }

        Optional<String> res = criarEmprestimo(r.getClienteId(), r.getLivroId());
        if (res.isPresent()) return res;

        r.setStatus("aprovada");
        repoRequisicoes.salvarTodos(todas);

        return Optional.empty();
    }

    public Optional<String> criarEmprestimo(int clienteId, String livroId) {
        Optional<Cliente> c = repoClientes.findById(clienteId);
        if (c.isEmpty()) return Optional.of("Cliente não encontrado");

        Optional<Livro> l = repoLivros.findById(livroId);
        if (l.isEmpty()) return Optional.of("Livro não encontrado");


        Livro livro = l.get();
        Cliente cliente = c.get();


        if (livro.isRaro() && !cliente.isVip())
            return Optional.of("Somente VIP podem pegar livros raros");

        if (livro.getCopiasDisponiveis() <= 0)
            return Optional.of("Nenhuma cópia disponível");

        livro.emprestarUmaCopia();
        List<Livro> todos = repoLivros.findAll();
        todos.removeIf(x -> x.getId().equals(livro.getId()));
        todos.add(livro);
        repoLivros.salvarTodos(todos);

        Emprestimo emp = new Emprestimo(clienteId, livroId, cliente.isVip());
        List<Emprestimo> empList = repoEmprestimos.findAll();
        empList.add(emp);
        repoEmprestimos.salvarTodos(empList);

        Notificacao n = new Notificacao(clienteId,
                "Empréstimo criado: " + livro.getTitulo() + " até " + emp.getDataPrevista());
        List<Notificacao> ns = repoNotificacoes.findAll();
        ns.add(n);
        repoNotificacoes.salvarTodos(ns);


        return Optional.empty();
    }

    public Optional<String> devolverEmprestimo(String emprestimoId) {
        List<Emprestimo> todos = repoEmprestimos.findAll();
        Optional<Emprestimo> opt = todos.stream().filter(e -> e.getId().equals(emprestimoId)).findFirst();
        if (opt.isEmpty()) return Optional.of("Empréstimo não encontrado");

        Emprestimo e = opt.get();
        if (e.isDevolvido()) return Optional.of("Já devolvido");

        LocalDate hoje = LocalDate.now();
        e.setDataDevolucao(hoje);

        Optional<Cliente> cliente = repoClientes.findById(e.getClienteId());
        boolean vip = cliente.isPresent() && cliente.get().isVip();

        double multa = e.calcularMulta(vip);

        todos.removeIf(x -> x.getId().equals(e.getId()));
        todos.add(e);
        repoEmprestimos.salvarTodos(todos);

        Optional<Livro> l = repoLivros.findById(e.getLivroId());
        if (l.isPresent()) {
            Livro lv = l.get();
            lv.devolverUmaCopia();
            List<Livro> ls = repoLivros.findAll();
            ls.removeIf(x -> x.getId().equals(lv.getId()));
            ls.add(lv);
            repoLivros.salvarTodos(ls);
        }

        if (multa > 0) {
            Pagamento p = new Pagamento(e.getClienteId(), multa, "Multa atraso - " + e.getId());
            List<Pagamento> pag = repoPagamentos.findAll();
            pag.add(p);
            repoPagamentos.salvarTodos(pag);

            Notificacao n = new Notificacao(e.getClienteId(), "Multa gerada: R$ " + multa);
            List<Notificacao> ns = repoNotificacoes.findAll();
            ns.add(n);
            repoNotificacoes.salvarTodos(ns);
        }

        return Optional.empty();
    }

    public Optional<Cliente> findClienteById(int id) {
        return repoClientes.findById(id);
    }

    public Optional<Funcionario> findFuncionarioById(int id) {
        return repoFuncionarios.findById(id);
    }

    public Optional<Livro> findLivroById(String id) {
        return repoLivros.findById(id);
    }
}
