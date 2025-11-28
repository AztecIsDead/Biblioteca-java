import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioPagamentoCsv {
    private final String arquivo;
    public RepositorioPagamentoCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Pagamento> findAll() {
        List<Pagamento> out = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                out.add(Pagamento.fromCSV(s));
            }
        } catch (Exception _) {}
        return out;
    }

    public void salvarTodos(List<Pagamento> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
            } else {
                bw.write(lista.get(0).getCabecalhoCSV());
                bw.newLine();
                for (Pagamento p : lista) { bw.write(p.toCSV()); bw.newLine(); }
            }
        } catch (Exception e) { System.err.println("Erro salvar pagamentos: " + e.getMessage()); }
    }
}
