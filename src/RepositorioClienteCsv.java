import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioClienteCsv {
    private final String arquivo;
    public RepositorioClienteCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Cliente> findAll() {
        List<Cliente> out = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            if (linhas.isEmpty()) return out;
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                try {
                    Cliente c = Cliente.fromCSV(s);
                    out.add(c);
                } catch (Exception ex) {
                    System.err.println("Linha de cliente malformada: " + String.join(",", s));
                }
            }
        } catch (Exception _) {}
        return out;
    }

    public void salvarTodos(List<Cliente> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
                bw.write("");
            } else {
                bw.write(lista.getFirst().getCabecalhoCSV());
                bw.newLine();
                for (Cliente c : lista) { bw.write(c.toCSV()); bw.newLine(); }
            }
        } catch (Exception e) { System.err.println("Erro salvar clientes: " + e.getMessage()); }
    }

    public void salvar(Cliente cliente) {
        if (cliente == null) return;
        List<Cliente> lista = findAll();
        lista.removeIf(c -> c.getId() == cliente.getId());
        lista.add(cliente);
        salvarTodos(lista);
    }

    public int nextId() {
        int max = 0;
        for (Cliente c : findAll()) if (c.getId() > max) max = c.getId();
        return max + 1;
    }

    public Optional<Cliente> findById(int id) {
        return findAll().stream().filter(c -> c.getId() == id).findFirst();
    }

    public Optional<Cliente> findByUsuario(String usuario) {
        if (usuario == null) return Optional.empty();
        return findAll().stream().filter(c -> usuario.equalsIgnoreCase(c.getUsuario())).findFirst();
    }
}
