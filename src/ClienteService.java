import java.util.ArrayList;
import java.util.List;

public class ClienteService {

    private final RepositorioClienteCsv repo;
    private List<Cliente> cache = new ArrayList<>();

    public ClienteService() {
        this.repo = new RepositorioClienteCsv("data/clientes.csv");
        loadAll();
    }

    public final void loadAll() {
        try {
            cache = new ArrayList<>(repo.findAll());
        } catch (Exception e) {
            System.out.println("Erro carregar clientes: " + e.getMessage());
            cache = new ArrayList<>();
        }
    }

    public List<Cliente> findAll() {
        return cache;
    }

    public void save(Cliente cliente) {
        if (cliente == null) return;
        List<Cliente> lista = repo.findAll();

        int idx = -1;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == cliente.getId()) {
                idx = i;
                break;
            }
        }

        if (idx >= 0) lista.set(idx, cliente);
        else lista.add(cliente);

        repo.salvarTodos(lista);
        cache = new ArrayList<>(lista);
    }

    public void delete(Cliente cliente) {
        if (cliente == null) return;

        List<Cliente> lista = repo.findAll();
        lista.removeIf(c -> c.getId() == cliente.getId());

        repo.salvarTodos(lista);
        cache = new ArrayList<>(lista);
    }
}
