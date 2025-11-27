import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RelatorioServico {
    private final RepositorioEmprestimoCsv repoEmprestimos;
    private final RepositorioLivroCsv repoLivros;
    private final RepositorioClienteCsv repoClientes;
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    public RelatorioServico(String csvEmprestimos, String csvLivros, String csvClientes) {
        this.repoEmprestimos = new RepositorioEmprestimoCsv(csvEmprestimos);
        this.repoLivros = new RepositorioLivroCsv(csvLivros);
        this.repoClientes = new RepositorioClienteCsv(csvClientes);
    }
    public Map<String, Long> emprestimosPorMes() {
        return repoEmprestimos.findAll().stream()
                .collect(Collectors.groupingBy(e -> {
                    LocalDate d = e.getDataEmprestimo();
                    return d.getYear() + "-" + String.format("%02d", d.getMonthValue());
                }, Collectors.counting()));
    }
    public Map<String, Long> frequenciaPorTitulo() {
        Map<String, String> livroIdParaTitulo = repoLivros.findAll().stream()
                .collect(Collectors.toMap(Livro::getId, Livro::getTitulo));
        return repoEmprestimos.findAll().stream()
                .collect(Collectors.groupingBy(e -> livroIdParaTitulo.getOrDefault(e.getLivroId(), "Desconhecido"), Collectors.counting()));
    }
    public List<Emprestimo> historicoCliente(int clienteId) {
        return repoEmprestimos.findAll().stream()
                .filter(e -> e.getClienteId() == clienteId)
                .sorted(Comparator.comparing(Emprestimo::getDataEmprestimo).reversed())
                .collect(Collectors.toList());
    }
    public void exportarMapaParaCsv(Map<String, Long> mapa, String caminhoArquivo, String cabecalho) {
        List<String> linhas = new ArrayList<>();
        linhas.add(cabecalho);
        mapa.forEach((k, v) -> linhas.add(k + ";" + v));
        try {
            Path p = Paths.get(caminhoArquivo);
            if (p.getParent() != null) Files.createDirectories(p.getParent());
            Files.write(p, linhas);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao exportar relatório: " + ex.getMessage(), ex);
        }
    }
}
