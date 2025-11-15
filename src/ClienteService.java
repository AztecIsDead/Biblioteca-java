import java.util.ArrayList;
import java.util.List;

public class ClienteService {
    private List<Cliente> cache = new ArrayList<>();

    public ClienteService() { loadAll(); }

    public final void loadAll() {
        try {
            Catalogo c = new Catalogo();
            ArrayList<Cliente> list = c.getClientesCadastrados();
            cache = (list == null) ? new ArrayList<>() : new ArrayList<>(list);
        } catch (Exception e) {
            System.out.println("Erro carregar clientes: " + e.getMessage());
            cache = new ArrayList<>();
        }
    }

    public List<Cliente> findAll() { return cache; }

    public void save(Cliente cliente) {
        if (cliente == null) return;
        Catalogo c = new Catalogo();
        ArrayList<Cliente> list = c.getClientesCadastrados();
        if (list == null) list = new ArrayList<>();
        int idx = -1;
        for (int i = 0; i < list.size(); i++) {
            Cliente cc = list.get(i);
            if (cc.getNome().equalsIgnoreCase(cliente.getNome())) { idx = i; break; }
        }
        if (idx >= 0) list.set(idx, cliente); else list.add(cliente);
        CSVUtil.gravarCSV(list, "clientes.csv");
        cache = new ArrayList<>(list);
    }

    public void delete(Cliente cliente) {
        if (cliente == null) return;
        Catalogo c = new Catalogo();
        ArrayList<Cliente> list = c.getClientesCadastrados();
        if (list == null) list = new ArrayList<>();
        list.removeIf(cl -> cl.getNome().equalsIgnoreCase(cliente.getNome()));
        CSVUtil.gravarCSV(list, "clientes.csv");
        cache = new ArrayList<>(list);
    }
}
