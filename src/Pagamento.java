import java.time.LocalDate;
import java.util.UUID;

public class Pagamento implements CSVGravavel {
    private String id;
    private int clienteId;
    private double valor;
    private String descricao;
    private LocalDate data;

    public Pagamento(int clienteId, double valor, String descricao) {
        this.id = UUID.randomUUID().toString();
        this.clienteId = clienteId;
        this.valor = valor;
        this.descricao = descricao;
        this.data = LocalDate.now();
    }

    public Pagamento(String id, int clienteId, double valor, String descricao, LocalDate data) {
        this.id = id == null || id.isBlank() ? UUID.randomUUID().toString() : id;
        this.clienteId = clienteId;
        this.valor = valor;
        this.descricao = descricao;
        this.data = data == null ? LocalDate.now() : data;
    }

    public String getId() { return id; }
    public int getClienteId() { return clienteId; }
    public double getValor() { return valor; }

    @Override
    public String getCabecalhoCSV() { return "id,clienteId,valor,descricao,data"; }

    @Override
    public String toCSV() {
        return CSVUtil.escape(id) + "," + clienteId + "," + valor + "," + CSVUtil.escape(descricao) + "," + (data == null ? "" : data.toString());
    }

    public static Pagamento fromCSV(String[] s) {
        String id = s.length > 0 ? s[0] : UUID.randomUUID().toString();
        int cid = s.length > 1 && !s[1].isEmpty() ? Integer.parseInt(s[1]) : -1;
        double v = s.length > 2 && !s[2].isEmpty() ? Double.parseDouble(s[2]) : 0.0;
        String desc = s.length > 3 ? s[3] : "";
        java.time.LocalDate d = s.length > 4 && !s[4].isEmpty() ? java.time.LocalDate.parse(s[4]) : null;
        return new Pagamento(id, cid, v, desc, d);
    }
}
