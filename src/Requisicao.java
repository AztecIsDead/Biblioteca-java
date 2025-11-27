// Requisicao.java
import java.time.LocalDate;

public class Requisicao implements CSVGravavel {

    private int id;
    private int clienteId;
    private String livroId;
    private String livroTitulo;
    private String status;
    private LocalDate data;

    public Requisicao(int id, int clienteId, String livroId, String livroTitulo,
                      String status, LocalDate data) {
        this.id = id;
        this.clienteId = clienteId;
        this.livroId = livroId;
        this.livroTitulo = livroTitulo;
        this.status = status == null ? "pendente" : status.toLowerCase();
        this.data = data == null ? LocalDate.now() : data;
    }

    public int getId() { return id; }
    public int getClienteId() { return clienteId; }
    public String getLivroId() { return livroId; }
    public String getLivroTitulo() { return livroTitulo; }
    public String getStatus() { return status; }
    public LocalDate getData() { return data; }

    public void setStatus(String s) {
        if (s != null) this.status = s.toLowerCase();
    }

    @Override
    public String toCSV() {
        return id + "," +
                clienteId + "," +
                CSVUtil.escape(livroId) + "," +
                CSVUtil.escape(livroTitulo) + "," +
                status + "," +
                (data == null ? "" : data.toString());
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,clienteId,livroId,livroTitulo,status,data";
    }

    public static Requisicao fromCSV(String[] s) {
        int id = s.length > 0 && !s[0].isEmpty() ? Integer.parseInt(s[0]) : 0;
        int clienteId = s.length > 1 && !s[1].isEmpty() ? Integer.parseInt(s[1]) : 0;
        String livroId = s.length > 2 ? s[2] : "";
        String livroTitulo = s.length > 3 ? s[3] : "";
        String status = s.length > 4 && s[4] != null && !s[4].isEmpty() ? s[4].toLowerCase() : "pendente";
        java.time.LocalDate data = s.length > 5 && !s[5].isEmpty() ? java.time.LocalDate.parse(s[5]) : LocalDate.now();
        return new Requisicao(id, clienteId, livroId, livroTitulo, status, data);
    }
}
