import java.time.LocalDate;

public class Funcionario extends Usuario implements CSVGravavel {
    private int id;
    private String usuario;
    private String senhaHash; // armazena senha pura
    private LocalDate dataAdmissao;
    private long minutosSessao;
    private String cargo;

    public Funcionario(int id, String nome, int idade, String usuario, String senhaHash, String cargo) {
        super(nome, idade);
        this.id = id;
        this.usuario = usuario;
        this.senhaHash = senhaHash;
        this.cargo = cargo == null ? "" : cargo;
        this.dataAdmissao = LocalDate.now();
    }

    public Funcionario(int id, String nome, int idade, String usuario, String senhaHash) {
        this(id, nome, idade, usuario, senhaHash, "");
    }

    public Funcionario(String nome, int idade, String usuario, String senhaHash, String cargo) {
        super(nome, idade);
        this.id = Math.abs((nome + usuario + System.nanoTime()).hashCode());
        this.usuario = usuario;
        this.senhaHash = senhaHash;
        this.cargo = cargo == null ? "" : cargo;
        this.dataAdmissao = LocalDate.now();
    }

    public int getId() { return id; }
    public int getIdFuncionario() { return id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenhaHash() { return senhaHash; }
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public long getMinutosSessao() { return minutosSessao; }
    public void addMinutosSessao(long m) { this.minutosSessao += m; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo == null ? "" : cargo; }

    @Override
    public String getCabecalhoCSV() {
        return "id,nome,idade,usuario,senhaHash,cargo,dataAdmissao,minutosSessao";
    }

    @Override
    public String toCSV() {
        return id + "," +
                CSVUtil.escape(getNome()) + "," +
                getIdade() + "," +
                CSVUtil.escape(usuario) + "," +
                CSVUtil.escape(senhaHash == null ? "" : senhaHash) + "," +
                CSVUtil.escape(cargo == null ? "" : cargo) + "," +
                (dataAdmissao == null ? "" : dataAdmissao.toString()) + "," +
                minutosSessao;
    }

    public static Funcionario fromCSV(String[] s) {

        int id = s.length > 0 && !s[0].isEmpty()
                ? Integer.parseInt(s[0].trim())
                : Math.abs(("fn" + System.nanoTime()).hashCode());

        String nome = s.length > 1 ? s[1].trim() : "";
        int idade = s.length > 2 && !s[2].isEmpty() ? Integer.parseInt(s[2].trim()) : 0;
        String usuario = s.length > 3 ? s[3].trim() : "";
        String senha = s.length > 4 ? s[4].trim() : "";

        String cargo = s.length > 5 ? s[5].trim() : "";
        LocalDate admissao = (s.length > 6 && !s[6].isEmpty())
                ? LocalDate.parse(s[6].trim())
                : LocalDate.now();

        long minutos = (s.length > 7 && !s[7].isEmpty())
                ? Long.parseLong(s[7].trim())
                : 0;

        Funcionario f = new Funcionario(id, nome, idade, usuario, senha, cargo);
        f.dataAdmissao = admissao;
        f.minutosSessao = minutos;

        return f;
    }
}
