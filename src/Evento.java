import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Evento implements CSVGravavel {

    private int id;
    private String nome;
    private LocalDateTime inicio;
    private String local;

    private int capacidadeNormal;
    private int capacidadeVip;

    private List<Integer> inscritosNormais;
    private List<Integer> inscritosVip;
    private List<Integer> filaEspera;

    private StatusEvento status;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Evento(int id, String nome, LocalDateTime inicio, String local,
                  int capNormal, int capVip,
                  List<Integer> normal, List<Integer> vip, List<Integer> fila,
                  StatusEvento status) {

        this.id = id;
        this.nome = nome;
        this.inicio = inicio;
        this.local = local;

        this.capacidadeNormal = capNormal;
        this.capacidadeVip = capVip;

        this.inscritosNormais = normal == null ? new ArrayList<>() : normal;
        this.inscritosVip = vip == null ? new ArrayList<>() : vip;
        this.filaEspera = fila == null ? new ArrayList<>() : fila;

        this.status = status == null ? StatusEvento.ATIVO : status;
    }

    public boolean temVagaVip() {
        return inscritosVip.size() < capacidadeVip;
    }

    public boolean temVagaNormal() {
        return inscritosNormais.size() < capacidadeNormal;
    }

    public boolean estaLotado() {
        return !temVagaNormal();
    }

    public boolean isInscrito(int clienteId) {
        return inscritosVip.contains(clienteId)
                || inscritosNormais.contains(clienteId)
                || filaEspera.contains(clienteId);
    }

    public void inscreverCliente(int clienteId, boolean vip) {
        if (isInscrito(clienteId)) return;
        if (vip) {
            if (temVagaVip()) {
                inscritosVip.add(clienteId);
                return;
            }
            if (temVagaNormal()) {
                inscritosNormais.add(clienteId);
                return;
            }
            return;
        }
        if (temVagaNormal()) {
            inscritosNormais.add(clienteId);
            return;
        }
        filaEspera.add(clienteId);
    }

    public void removerCliente(int clienteId) {
        if (inscritosVip.remove(Integer.valueOf(clienteId))) return;
        if (inscritosNormais.remove(Integer.valueOf(clienteId))) {
            if (!filaEspera.isEmpty()) {
                int novo = filaEspera.removeFirst();
                inscritosNormais.add(novo);
            }
            return;
        }
        filaEspera.remove(Integer.valueOf(clienteId));
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public LocalDateTime getInicio() { return inicio; }
    public String getLocal() { return local; }

    public int getCapacidadeNormal() { return capacidadeNormal; }
    public int getCapacidadeVip() { return capacidadeVip; }

    public List<Integer> getInscritosNormais() { return inscritosNormais; }
    public List<Integer> getInscritosVip() { return inscritosVip; }
    public List<Integer> getFilaEspera() { return filaEspera; }

    public StatusEvento getStatus() { return status; }

    public void setNome(String nome) { this.nome = nome; }
    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public void setLocal(String local) { this.local = local; }
    public void setCapacidadeNormal(int capacidadeNormal) { this.capacidadeNormal = capacidadeNormal; }
    public void setCapacidadeVip(int capacidadeVip) { this.capacidadeVip = capacidadeVip; }
    public void setStatus(StatusEvento status) { this.status = status; }

    public int getTotalInscritos() {
        return inscritosNormais.size() + inscritosVip.size();
    }

    public int getVagasTotais() {
        return capacidadeNormal + capacidadeVip;
    }

    public int getVagas() {
        return getVagasTotais();
    }

    public List<Integer> getInscritos() {
        List<Integer> out = new ArrayList<>();
        out.addAll(inscritosNormais);
        out.addAll(inscritosVip);
        return out;
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,nome,inicio,local,capNormal,capVip,insNormais,insVip,fila,status";
    }

    public static String getCabecalhoPadraoCSV() {
        return "id,nome,inicio,local,capNormal,capVip,insNormais,insVip,fila,status";
    }

    @Override
    public String toCSV() {
        String norm = String.join(",",
                inscritosNormais.stream().map(String::valueOf).toArray(String[]::new));

        String vip = String.join(",",
                inscritosVip.stream().map(String::valueOf).toArray(String[]::new));

        String fila = String.join(",",
                filaEspera.stream().map(String::valueOf).toArray(String[]::new));

        return id + "," +
                CSVUtil.escape(nome) + "," +
                (inicio == null ? "" : inicio.format(FORMATTER)) + "," +
                CSVUtil.escape(local) + "," +
                capacidadeNormal + "," +
                capacidadeVip + "," +
                CSVUtil.escape(norm) + "," +
                CSVUtil.escape(vip) + "," +
                CSVUtil.escape(fila) + "," +
                status;
    }

    public static Evento fromCSV(String[] c) {
        int id = Integer.parseInt(c[0]);
        String nome = c[1];
        LocalDateTime inicio = c[2].isEmpty() ? null : LocalDateTime.parse(c[2], FORMATTER);
        String local = c[3];

        int capNormal = Integer.parseInt(c[4]);
        int capVip = Integer.parseInt(c[5]);

        List<Integer> norm = CSVUtil.splitToIntList(c[6]);
        List<Integer> vip = CSVUtil.splitToIntList(c[7]);
        List<Integer> fila = CSVUtil.splitToIntList(c[8]);

        StatusEvento status = StatusEvento.valueOf(c[9]);

        return new Evento(id, nome, inicio, local, capNormal, capVip, norm, vip, fila, status);
    }
}
