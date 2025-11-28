import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioFuncionarioCsv {
    private final String arquivo;
    public RepositorioFuncionarioCsv(String arquivo) { this.arquivo = arquivo; }

    public List<Funcionario> findAll() {
        List<Funcionario> out = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            if (linhas.isEmpty()) return out;
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                try {
                    Funcionario f = Funcionario.fromCSV(s);
                    out.add(f);
                } catch (Exception ex) {
                    System.err.println("Linha de funcionario malformada: " + String.join(",", s));
                }
            }
        } catch (Exception _) {}
        return out;
    }

    public void salvarTodos(List<Funcionario> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
                bw.write("");
            } else {
                bw.write(lista.getFirst().getCabecalhoCSV());
                bw.newLine();
                for (Funcionario f : lista) { bw.write(f.toCSV()); bw.newLine(); }
            }
        } catch (Exception e) { System.err.println("Erro salvar funcionarios: " + e.getMessage()); }
    }

    public void salvar(Funcionario funcionario) {
        if (funcionario == null) return;
        List<Funcionario> lista = findAll();
        lista.removeIf(f -> f.getId() == funcionario.getId());
        lista.add(funcionario);
        salvarTodos(lista);
    }

    public int nextId() {
        int max = 0;
        for (Funcionario f : findAll()) if (f.getId() > max) max = f.getId();
        return max + 1;
    }

    public Optional<Funcionario> findById(int id) {
        return findAll().stream().filter(f -> f.getId() == id).findFirst();
    }

    public Optional<Funcionario> findByUsuario(String usuario) {
        if (usuario == null) return Optional.empty();
        return findAll().stream().filter(f -> usuario.equalsIgnoreCase(f.getUsuario())).findFirst();
    }
}
