import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioEventoCsv {

    private final String arquivo;

    public RepositorioEventoCsv(String arquivo) {
        this.arquivo = arquivo;
    }

    public List<Evento> findAll() {
        List<Evento> out = new ArrayList<>();

        try {
            File f = new File(arquivo);
            if (!f.exists()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
                    bw.write(Evento.getCabecalhoPadraoCSV());
                }
                return out;
            }

            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            if (linhas == null || linhas.isEmpty()) return out;

            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) {
                    primeira = false;
                    continue;
                }

                try {
                    Evento ev = Evento.fromCSV(s);
                    if (ev != null) out.add(ev);
                } catch (Exception ignored) {}
            }

        } catch (Exception ignored) {}

        return out;
    }

    public void salvarTodos(List<Evento> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            bw.write(Evento.getCabecalhoPadraoCSV());
            bw.newLine();

            if (lista != null) {
                for (Evento ev : lista) {
                    bw.write(ev.toCSV());
                    bw.newLine();
                }
            }

        } catch (Exception ignored) {}
    }

    public void save(Evento e) {
        List<Evento> todos = findAll();
        todos.removeIf(x -> x.getId() == e.getId());
        todos.add(e);
        salvarTodos(todos);
    }
    public int proximoId() {
        return findAll().stream().mapToInt(Evento::getId).max().orElse(0) + 1;
    }
    public void delete(int id) {
        List<Evento> todos = findAll();
        todos.removeIf(e -> e.getId() == id);
        salvarTodos(todos);
    }
    public void update(Evento e) {
        List<Evento> todos = findAll();
        todos.removeIf(x -> x.getId() == e.getId());
        todos.add(e);
        salvarTodos(todos);
    }
    public Optional<Evento> findById(int id) {
        return findAll().stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

}
