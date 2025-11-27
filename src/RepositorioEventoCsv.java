import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioEventoCsv {
    private final String arquivo;
    public RepositorioEventoCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Evento> findAll() {
        List<Evento> out = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            if (linhas == null) return out;
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                out.add(Evento.fromCSV(s));
            }
        } catch (Exception e) {}
        return out;
    }

    public void salvarTodos(List<Evento> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
            } else {
                bw.write(lista.get(0).getCabecalhoCSV());
                bw.newLine();
                for (Evento ev : lista) { bw.write(ev.toCSV()); bw.newLine(); }
            }
        } catch (Exception e) { System.err.println("Erro salvar eventos: " + e.getMessage()); }
    }
}
