import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento implements CSVGravavel {

    private int id;
    private String nome;
    private LocalDate data;
    private String local;
    private List<Integer> inscritos;

    public Evento(int id, String nome, LocalDate data, String local, List<Integer> inscritos) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.inscritos = inscritos == null ? new ArrayList<>() : inscritos;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getData() { return data; }
    public String getLocal() { return local; }
    public List<Integer> getInscritos() { return inscritos; }

    public void inscrever(int clienteId) {
        if (!inscritos.contains(clienteId)) inscritos.add(clienteId);
    }

    public void removerInscricao(int clienteId) {
        inscritos.remove(Integer.valueOf(clienteId));
    }

    @Override
    public String toCSV() {
        String inscritosStr = String.join(",",
                inscritos.stream().map(String::valueOf).toArray(String[]::new)
        );

        return id + "," +
                CSVUtil.escape(nome) + "," +
                (data == null ? "" : data.toString()) + "," +
                CSVUtil.escape(local) + "," +
                CSVUtil.escape(inscritosStr);
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,nome,data,local,inscritos";
    }

    public static Evento fromCSV(String[] s) {
        int id = s.length > 0 && !s[0].isEmpty() ? Integer.parseInt(s[0]) : 0;
        String nome = s.length > 1 ? s[1] : "";
        LocalDate data = s.length > 2 && !s[2].isEmpty() ? LocalDate.parse(s[2]) : null;
        String local = s.length > 3 ? s[3] : "";
        String inscritosStr = s.length > 4 ? s[4] : "";

        List<Integer> lista = new ArrayList<>();

        if (!inscritosStr.isEmpty()) {
            String[] parts = inscritosStr.split(",");
            for (String p : parts) {
                if (!p.isEmpty()) lista.add(Integer.parseInt(p));
            }
        }

        return new Evento(id, nome, data, local, lista);
    }
}
