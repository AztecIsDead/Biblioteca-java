import java.util.ArrayList;
import java.util.List;

public class FuncionarioService {
    private List<Funcionario> cache = new ArrayList<>();

    public FuncionarioService() { loadAll(); }

    public final void loadAll() {
        try {
            Catalogo c = new Catalogo();
            ArrayList<Funcionario> list = c.getFuncionariosCadastrados();
            cache = (list == null) ? new ArrayList<>() : new ArrayList<>(list);
        } catch (Exception e) {
            System.out.println("Erro carregar funcionarios: " + e.getMessage());
            cache = new ArrayList<>();
        }
    }

    public List<Funcionario> findAll() { return cache; }

    public void save(Funcionario f) {
        if (f == null) return;
        Catalogo c = new Catalogo();
        ArrayList<Funcionario> list = c.getFuncionariosCadastrados();
        if (list == null) list = new ArrayList<>();
        int idx = -1;
        for (int i = 0; i < list.size(); i++) {
            Funcionario ff = list.get(i);
            if (ff.getNome().equalsIgnoreCase(f.getNome())) { idx = i; break; }
        }
        if (idx >= 0) list.set(idx, f); else list.add(f);
        CSVUtil.gravarCSV(list, "funcionarios.csv");
        cache = new ArrayList<>(list);
    }

    public void delete(Funcionario f) {
        if (f == null) return;
        Catalogo c = new Catalogo();
        ArrayList<Funcionario> list = c.getFuncionariosCadastrados();
        if (list == null) list = new ArrayList<>();
        list.removeIf(fi -> fi.getNome().equalsIgnoreCase(f.getNome()));
        CSVUtil.gravarCSV(list, "funcionarios.csv");
        cache = new ArrayList<>(list);
    }
}
