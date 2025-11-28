import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class Emprestimo implements CSVGravavel {

    private String id;
    private int clienteId;
    private String livroId;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private boolean devolvido;

    public Emprestimo(String id, int clienteId, String livroId,
                      LocalDate dataEmprestimo, LocalDate dataPrevista,
                      LocalDate dataDevolucao, boolean devolvido) {
        this.id = id;
        this.clienteId = clienteId;
        this.livroId = livroId;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    public Emprestimo(int clienteId, String livroId, boolean clienteVip) {
        this.id = "EMP-" + UUID.randomUUID().toString();
        this.clienteId = clienteId;
        this.livroId = livroId;
        this.dataEmprestimo = LocalDate.now();
        this.dataPrevista = dataEmprestimo.plusDays(clienteVip ? 21 : 14);
        this.dataDevolucao = null;
        this.devolvido = false;
    }

    public String getId() { return id; }
    public String getIdEmprestimo() { return id; }
    public int getClienteId() { return clienteId; }
    public int getIdCliente() { return clienteId; }
    public String getLivroId() { return livroId; }
    public String getIdLivro() { return livroId; }
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public LocalDate getDataDevolucaoReal() { return dataDevolucao; }
    public LocalDate getDataDevolucaoPrevista() { return dataPrevista; }
    public void setDataDevolucao(LocalDate d) { this.dataDevolucao = d; this.devolvido = (d != null); }
    public boolean isDevolvido() { return devolvido; }
    public boolean isEncerrado() { return devolvido; }

    public long diasAtraso() {
        if (dataDevolucao == null || dataPrevista == null) return 0;
        if (!dataDevolucao.isAfter(dataPrevista)) return 0;
        return ChronoUnit.DAYS.between(dataPrevista, dataDevolucao);
    }

    public double calcularMulta(boolean clienteVip) {
        long dias = diasAtraso();
        double base = dias * 2.0;
        return clienteVip ? base * 0.5 : base;
    }

    public double getMultaPaga() {
        return 0.0;
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,clienteId,livroId,dataEmprestimo,dataPrevista,dataDevolucao,devolvido";
    }

    @Override
    public String toCSV() {
        return CSVUtil.escape(id) + "," +
                clienteId + "," +
                CSVUtil.escape(livroId) + "," +
                (dataEmprestimo == null ? "" : dataEmprestimo.toString()) + "," +
                (dataPrevista == null ? "" : dataPrevista.toString()) + "," +
                (dataDevolucao == null ? "" : dataDevolucao.toString()) + "," +
                devolvido;
    }

    public static Emprestimo fromCSV(String[] s) {
        String id = s.length > 0 ? s[0] : "EMP-" + UUID.randomUUID().toString();
        int clienteId = s.length > 1 && !s[1].isEmpty() ? Integer.parseInt(s[1]) : -1;
        String livroId = s.length > 2 ? s[2] : "";
        LocalDate de = s.length > 3 && !s[3].isEmpty() ? LocalDate.parse(s[3]) : null;
        LocalDate dp = s.length > 4 && !s[4].isEmpty() ? LocalDate.parse(s[4]) : null;
        LocalDate dd = s.length > 5 && !s[5].isEmpty() ? LocalDate.parse(s[5]) : null;
        boolean dev = s.length > 6 && !s[6].isEmpty() && Boolean.parseBoolean(s[6]);
        return new Emprestimo(id, clienteId, livroId, de, dp, dd, dev);
    }
}
