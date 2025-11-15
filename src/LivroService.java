import java.util.ArrayList;
import java.util.List;

public class LivroService {
    private List<Livro> cache = new ArrayList<>();

    public LivroService() {
        loadAll();
    }

    public final void loadAll() {
        try {
            Catalogo c = new Catalogo();
            ArrayList<Livro> lista = c.getCatalogoLivros();
            if (lista == null) cache = new ArrayList<>();
            else cache = new ArrayList<>(lista);
        } catch (Exception e) {
            System.out.println("Erro ao carregar catálogo: " + e.getMessage());
            cache = new ArrayList<>();
        }
    }

    public List<Livro> findAll() {
        return cache;
    }

    public void save(Livro livro) {
        if (livro == null) return;

        Catalogo c = new Catalogo();
        ArrayList<Livro> listaCatalogo = c.getCatalogoLivros();
        if (listaCatalogo == null) listaCatalogo = new ArrayList<>();

        int idx = -1;
        for (int i = 0; i < listaCatalogo.size(); i++) {
            Livro l = listaCatalogo.get(i);
            if (l.getTitulo().equalsIgnoreCase(livro.getTitulo())
                    && l.getAutor().equalsIgnoreCase(livro.getAutor())) {
                idx = i;
                break;
            }
        }

        if (idx >= 0) listaCatalogo.set(idx, livro);
        else listaCatalogo.add(livro);

        CSVUtil.gravarCSV(listaCatalogo, "livros.csv");
        cache = new ArrayList<>(listaCatalogo);
    }

    public void delete(Livro livro) {
        if (livro == null) return;

        Catalogo c = new Catalogo();
        ArrayList<Livro> listaCatalogo = c.getCatalogoLivros();
        if (listaCatalogo == null) listaCatalogo = new ArrayList<>();

        listaCatalogo.removeIf(l ->
                l.getTitulo().equalsIgnoreCase(livro.getTitulo()) &&
                        l.getAutor().equalsIgnoreCase(livro.getAutor())
        );

        CSVUtil.gravarCSV(listaCatalogo, "livros.csv");
        cache = new ArrayList<>(listaCatalogo);
    }
}
