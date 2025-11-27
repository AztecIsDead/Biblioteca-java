import java.time.LocalDate;
import java.util.UUID;

public class Notificacao implements CSVGravavel {

    private String id;
    private int clienteId;
    private String mensagem;
    private LocalDate data;

    public Notificacao(int clienteId, String mensagem) {
        this.id = UUID.randomUUID().toString();
        this.clienteId = clienteId;
        this.mensagem = mensagem;
        this.data = LocalDate.now();
    }

    public Notificacao(String id, int clienteId, String mensagem, LocalDate data) {
        this.id = id;
        this.clienteId = clienteId;
        this.mensagem = mensagem;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public int getClienteId() {
        return clienteId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDate getData() {
        return data;
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,clienteId,mensagem,data";
    }

    @Override
    public String toCSV() {
        return CSVUtil.escape(id) + "," +
                clienteId + "," +
                CSVUtil.escape(mensagem) + "," +
                (data == null ? "" : data.toString());
    }

    public static Notificacao fromCSV(String[] s) {
        String id = s.length > 0 ? s[0] : UUID.randomUUID().toString();
        int cid = s.length > 1 ? Integer.parseInt(s[1]) : -1;
        String msg = s.length > 2 ? s[2] : "";
        LocalDate d = s.length > 3 && !s[3].isEmpty()
                ? LocalDate.parse(s[3])
                : null;

        return new Notificacao(id, cid, msg, d);
    }
}
