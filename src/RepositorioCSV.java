import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public abstract class RepositorioCSV<T> {
    protected final Path arquivo;

    protected RepositorioCSV(String nomeArquivo) {
        this.arquivo = Paths.get(nomeArquivo);
        try {
            if (arquivo.getParent() != null) Files.createDirectories(arquivo.getParent());
            if (!Files.exists(arquivo)) Files.createFile(arquivo);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar/abrir o arquivo: " + nomeArquivo, e);
        }
    }

    protected List<String> lerLinhas() {
        try {
            return Files.readAllLines(arquivo);
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    protected void gravarLinhas(List<String> linhas) {
        try {
            Files.write(arquivo, linhas, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar arquivo: " + arquivo + " - " + e.getMessage(), e);
        }
    }

    protected void anexarLinha(String linha) {
        try {
            Files.write(arquivo, Arrays.asList(linha), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao anexar ao arquivo: " + arquivo + " - " + e.getMessage(), e);
        }
    }

    protected List<T> lerTodos(Function<String,T> parser) {
        return lerLinhas().stream()
                .filter(l -> !l.isBlank())
                .map(l -> {
                    try {
                        return parser.apply(l);
                    } catch (Exception ex) {
                        System.err.println("Linha CSV malformada em " + arquivo + ": " + l + " -> " + ex.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @FunctionalInterface
    protected interface Function<A,B> { B apply(A a); }
}
