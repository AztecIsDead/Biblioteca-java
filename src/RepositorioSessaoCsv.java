import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioSessaoCsv {
    private final String arquivo;
    public RepositorioSessaoCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Sessao> findAll() {
        List<Sessao> out = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                out.add(Sessao.fromCSV(s));
            }
        } catch (Exception _) {}
        return out;
    }

    public void salvarTodos(List<Sessao> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
            } else {
                bw.write(lista.get(0).getCabecalhoCSV());
                bw.newLine();
                for (Sessao s : lista) { bw.write(s.toCSV()); bw.newLine(); }
            }
        } catch (Exception e) { System.err.println("Erro salvar sessoes: " + e.getMessage()); }
    }
}
