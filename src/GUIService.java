import java.util.List;
import java.util.Optional;

public class GUIService {

    private final BibliotecaServico svc;

    public GUIService(BibliotecaServico svc) {
        this.svc = svc;
    }

    public List<Livro> listarLivros() {
        return svc.todosLivros();
    }

    public List<Cliente> listarClientes() {
        return svc.todosClientes();
    }

    public List<Funcionario> listarFuncionarios() {
        return svc.todosFuncionarios();
    }

    public List<Requisicao> listarRequisicoesPendentes() {
        return svc.listarRequisicoesPendentes();
    }

    public List<Emprestimo> listarEmprestimos() {
        return svc.todosEmprestimos();
    }

    public List<Evento> listarEventos() {
        return svc.repoEventos.findAll();
    }
    public List<Emprestimo> getEmprestimosDoCliente(int clienteId) {
        return svc.getEmprestimosDoCliente(clienteId);
    }

    public List<Notificacao> listarNotificacoes(int clienteId) {
        return svc.repoNotificacoes.listarPorCliente(clienteId);
    }

    public Livro adicionarLivro(String titulo, String autor, boolean raro, int totalCopias) {
        return svc.adicionarLivro(titulo, autor, raro, totalCopias);
    }

    // registrarCliente / registrarFuncionario agora recebem senha pura e svc NÃO vai hashear
    public Cliente registrarCliente(String nome, int idade, String usuario, String senha, Cliente.TipoCliente tipo, boolean aplicarHash) {
        return svc.registrarCliente(nome, idade, usuario, senha, tipo, aplicarHash);
    }

    public Funcionario registrarFuncionario(String nome, int idade, String usuario, String senha, boolean aplicarHash) {
        return svc.registrarFuncionario(nome, idade, usuario, senha, aplicarHash);
    }

    public Optional<String> criarRequisicao(int clienteId, String livroId) {
        return svc.criarRequisicao(clienteId, livroId);
    }

    public void processarRequisicao(String id, boolean aprovar) {
        svc.processarRequisicao(id, aprovar);
    }

    public Optional<String> criarEmprestimo(int clienteId, String livroId) {
        return svc.criarEmprestimo(clienteId, livroId);
    }

    public Optional<String> devolverEmprestimo(String id) {
        return svc.devolverEmprestimo(id);
    }

    public Optional<Funcionario> findFuncionarioById(int id) {
        return svc.findFuncionarioById(id);
    }

    public Optional<Cliente> findClienteById(int id) {
        return svc.findClienteById(id);
    }

    public Optional<Livro> findLivroById(String id) {
        return svc.findLivroById(id);
    }

    public void excluirFuncionario(int id) {
        var list = svc.todosFuncionarios();
        list.removeIf(f -> f.getId() == id);
        svc.repoFuncionarios.salvarTodos(list);
    }

    public void atualizarFuncionario(Funcionario f) {
        var list = svc.todosFuncionarios();
        list.removeIf(x -> x.getId() == f.getId());
        list.add(f);
        svc.repoFuncionarios.salvarTodos(list);
    }
}
