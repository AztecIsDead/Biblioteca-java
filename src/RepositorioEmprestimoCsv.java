import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEmprestimoCsv {
    private final String arquivo;
    public RepositorioEmprestimoCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Emprestimo> findAll() {
        List<Emprestimo> lista = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                if (s.length < 7) continue;
                Emprestimo e = Emprestimo.fromCSV(s);
                lista.add(e);
            }
        } catch (Exception _) {}
        return lista;
    }

    public void salvarTodos(List<Emprestimo> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (!(lista.isEmpty())) {
                bw.write(lista.getFirst().getCabecalhoCSV());
                bw.newLine();
                for (Emprestimo e : lista) { bw.write(e.toCSV()); bw.newLine(); }
            }
        } catch (Exception ex) { System.err.println("Erro ao salvar empréstimos: " + ex.getMessage()); }
    }
}
