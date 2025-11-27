import java.util.UUID;

public class Livro implements CSVGravavel {

    private String id;
    private String titulo;
    private String autor;
    private boolean raro;
    private int totalCopias;
    private int copiasDisponiveis;

    public Livro(String id, String titulo, String autor, boolean raro, int totalCopias, int copiasDisponiveis) {
        this.id = (id == null || id.isBlank()) ? UUID.randomUUID().toString() : id;
        this.titulo = titulo;
        this.autor = autor;
        this.raro = raro; // CORRIGIDO
        this.totalCopias = totalCopias;
        this.copiasDisponiveis = copiasDisponiveis;

        ajustarRaridade();
    }

    public Livro(String titulo, String autor, boolean raro, int totalCopias) {
        this(UUID.randomUUID().toString(), titulo, autor, raro, totalCopias, totalCopias);
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getTotalCopias() { return totalCopias; }
    public int getCopiasDisponiveis() { return copiasDisponiveis; }
    public boolean isRaro() { return raro; }

    public boolean isDisponivel() {
        return copiasDisponiveis > 0;
    }

    public void emprestarUmaCopia() {
        if (copiasDisponiveis > 0) copiasDisponiveis--;
    }

    public void devolverUmaCopia() {
        if (copiasDisponiveis < totalCopias) copiasDisponiveis++;
    }

    public void ajustarRaridade() {
        if (raro) {
            totalCopias = 1;
            copiasDisponiveis = Math.min(copiasDisponiveis, 1);
        }
    }

    @Override
    public String getCabecalhoCSV() {
        return "id,titulo,autor,raro,totalCopias,copiasDisponiveis";
    }

    @Override
    public String toCSV() {
        return CSVUtil.escape(id) + "," +
                CSVUtil.escape(titulo) + "," +
                CSVUtil.escape(autor) + "," +
                raro + "," +
                totalCopias + "," +
                copiasDisponiveis;
    }

    public static Livro fromCSV(String[] s) {
        if (s.length < 6) {
            throw new IllegalArgumentException("Linha inválida de livro: campos faltando.");
        }

        String id = s[0];
        String titulo = s[1];
        String autor = s[2];
        boolean raro = Boolean.parseBoolean(s[3]);
        int total = Integer.parseInt(s[4]);
        int disp = Integer.parseInt(s[5]);

        return new Livro(id, titulo, autor, raro, total, disp);
    }
}
