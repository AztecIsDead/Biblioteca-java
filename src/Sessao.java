public class Sessao implements CSVGravavel {
    private int funcionarioId;
    private long minutos;

    public Sessao(int funcionarioId, long minutos) {
        this.funcionarioId = funcionarioId;
        this.minutos = minutos;
    }

    public int getFuncionarioId() { return funcionarioId; }
    public long getMinutos() { return minutos; }
    public void addMinutos(long m) { this.minutos += m; }

    @Override
    public String getCabecalhoCSV() { return "funcionarioId,minutos"; }

    @Override
    public String toCSV() { return funcionarioId + "," + minutos; }

    public static Sessao fromCSV(String[] s) {
        int fid = s.length > 0 && !s[0].isEmpty() ? Integer.parseInt(s[0]) : -1;
        long m = s.length > 1 && !s[1].isEmpty() ? Long.parseLong(s[1]) : 0L;
        return new Sessao(fid, m);
    }
}
