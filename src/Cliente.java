import java.time.LocalDate;

public class Cliente extends Usuario implements CSVGravavel {

    public enum TipoCliente { REGULAR, VIP }

    private int id;
    private String usuario;
    private String senhaHash; // agora armazena senha pura (string)
    private TipoCliente tipo;
    private LocalDate dataCadastro;
    private boolean vip;

    public Cliente(int id, String nome, int idade, String usuario, String senhaHash, TipoCliente tipo) {
        super(nome, idade);
        this.id = id;
        this.usuario = usuario;
        this.senhaHash = senhaHash;
        this.tipo = (tipo == null) ? TipoCliente.REGULAR : tipo;
        this.vip = this.tipo == TipoCliente.VIP;
        this.dataCadastro = LocalDate.now();
    }

    public Cliente(String nome, int idade, String usuario, String senhaHash, TipoCliente tipo) {
        super(nome, idade);
        this.id = Math.abs((nome + usuario + System.nanoTime()).hashCode());
        this.usuario = usuario;
        this.senhaHash = senhaHash;
        this.tipo = (tipo == null) ? TipoCliente.REGULAR : tipo;
        this.vip = this.tipo == TipoCliente.VIP;
        this.dataCadastro = LocalDate.now();
    }

    public int getId() { return id; }
    public int getIdCliente() { return id; }
    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public String getSenhaHash() { return senhaHash; } // getter permanece
    public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
    public TipoCliente getTipo() { return tipo; }
    public TipoCliente getTipoCliente() { return tipo; }
    public void setTipo(TipoCliente tipo) { this.tipo = (tipo == null) ? TipoCliente.REGULAR : tipo; this.vip = this.tipo == TipoCliente.VIP; }
    public boolean isVip() { return vip; }
    public LocalDate getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDate d) { this.dataCadastro = d; }

    @Override
    public String getCabecalhoCSV() {
        return "id,nome,idade,usuario,senhaHash,tipo,dataCadastro";
    }

    @Override
    public String toCSV() {
        return String.valueOf(id) + "," +
                CSVUtil.escape(getNome()) + "," +
                String.valueOf(getIdade()) + "," +
                CSVUtil.escape(usuario) + "," +
                CSVUtil.escape(senhaHash == null ? "" : senhaHash) + "," +
                tipo.name() + "," +
                (dataCadastro == null ? "" : dataCadastro.toString());
    }

    public static Cliente fromCSV(String[] s) {
        int id = s.length > 0 && !s[0].isEmpty() ? Integer.parseInt(s[0].trim()) : Math.abs(( (s.length>1 ? s[1] : "cli") + System.nanoTime()).hashCode());
        String nome = s.length > 1 ? s[1].trim() : "";
        int idade = s.length > 2 && !s[2].isEmpty() ? Integer.parseInt(s[2].trim()) : 0;
        String usuario = s.length > 3 ? s[3].trim() : "";
        String senhaHash = s.length > 4 ? s[4].trim() : "";

        Cliente.TipoCliente tipo = Cliente.TipoCliente.REGULAR;
        if (s.length > 5 && s[5] != null && !s[5].isEmpty()) {
            try { tipo = Cliente.TipoCliente.valueOf(s[5].trim()); } catch (Exception e) { tipo = Cliente.TipoCliente.REGULAR; }
        }

        Cliente c = new Cliente(id, nome, idade, usuario, senhaHash, tipo);
        if (s.length > 6 && s[6] != null && !s[6].isEmpty()) {
            try { c.setDataCadastro(LocalDate.parse(s[6].trim())); } catch (Exception ex) {}
        }
        return c;
    }
}
