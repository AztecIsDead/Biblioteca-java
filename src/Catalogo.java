import java.util.ArrayList;
import java.util.List;

public class Catalogo {

    private final RepositorioClienteCsv repoClientes;
    private final RepositorioFuncionarioCsv repoFuncionarios;
    private final RepositorioLivroCsv repoLivros;
    private final RepositorioRequisicaoCsv repoRequisicoes;
    private final RepositorioEmprestimoCsv repoEmprestimos;
    private final RepositorioPagamentoCsv repoPagamentos;
    private final RepositorioEventoCsv repoEventos;
    private final RepositorioNotificacaoCsv repoNotificacoes;
    private final RepositorioSessaoCsv repoSessoes;

    public Catalogo() { this("dataerrado/"); }

    public Catalogo(String baseDir) {
        if (!baseDir.endsWith("/")) baseDir = baseDir + "/";
        this.repoClientes = new RepositorioClienteCsv(baseDir + "clientes.csv");
        this.repoFuncionarios = new RepositorioFuncionarioCsv(baseDir + "funcionarios.csv");
        this.repoLivros = new RepositorioLivroCsv(baseDir + "livros.csv");
        this.repoRequisicoes = new RepositorioRequisicaoCsv(baseDir + "requisicoes.csv");
        this.repoEmprestimos = new RepositorioEmprestimoCsv(baseDir + "emprestimos.csv");
        this.repoPagamentos = new RepositorioPagamentoCsv(baseDir + "pagamentos.csv");
        this.repoEventos = new RepositorioEventoCsv(baseDir + "eventos.csv");
        this.repoNotificacoes = new RepositorioNotificacaoCsv(baseDir + "notificacoes.csv");
        this.repoSessoes = new RepositorioSessaoCsv(baseDir + "sessoes.csv");
    }

    public Catalogo(RepositorioLivroCsv repoLivro) {
        this.repoLivros = repoLivro;
        this.repoClientes = new RepositorioClienteCsv("data/clientes.csv");
        this.repoFuncionarios = new RepositorioFuncionarioCsv("data/funcionarios.csv");
        this.repoRequisicoes = new RepositorioRequisicaoCsv("data/requisicoes.csv");
        this.repoEmprestimos = new RepositorioEmprestimoCsv("data/emprestimos.csv");
        this.repoPagamentos = new RepositorioPagamentoCsv("data/pagamentos.csv");
        this.repoEventos = new RepositorioEventoCsv("data/eventos.csv");
        this.repoNotificacoes = new RepositorioNotificacaoCsv("data/notificacoes.csv");
        this.repoSessoes = new RepositorioSessaoCsv("data/sessoes.csv");
    }
    public ArrayList<Livro> getCatalogoLivros() {
        return getLivrosCadastrados();}

    public ArrayList<Cliente> getClientesCadastrados() {
        List<Cliente> l = repoClientes.findAll();
        return (l == null) ? new ArrayList<>() : new ArrayList<>(l);
    }

    public ArrayList<Funcionario> getFuncionariosCadastrados() {
        List<Funcionario> l = repoFuncionarios.findAll();
        return (l == null) ? new ArrayList<>() : new ArrayList<>(l);
    }

    public ArrayList<Livro> getLivrosCadastrados() {
        List<Livro> l = repoLivros.findAll();
        return (l == null) ? new ArrayList<>() : new ArrayList<>(l);
    }

    public ArrayList<Requisicao> getRequisicoesCadastradas() {
        List<Requisicao> l = repoRequisicoes.findAll();
        return (l == null) ? new ArrayList<>() : new ArrayList<>(l);
    }

    public ArrayList<Emprestimo> getEmprestimosCadastrados() {
        List<Emprestimo> l = repoEmprestimos.findAll();
        return (l == null) ? new ArrayList<>() : new ArrayList<>(l);
    }

    public RepositorioClienteCsv getRepoClientes() { return repoClientes; }
    public RepositorioFuncionarioCsv getRepoFuncionarios() { return repoFuncionarios; }
    public RepositorioLivroCsv getRepoLivros() { return repoLivros; }
    public RepositorioRequisicaoCsv getRepoRequisicoes() { return repoRequisicoes; }
    public RepositorioEmprestimoCsv getRepoEmprestimos() { return repoEmprestimos; }
    public RepositorioPagamentoCsv getRepoPagamentos() { return repoPagamentos; }
    public RepositorioEventoCsv getRepoEventos() { return repoEventos; }
    public RepositorioNotificacaoCsv getRepoNotificacoes() { return repoNotificacoes; }
    public RepositorioSessaoCsv getRepoSessoes() { return repoSessoes; }
}
