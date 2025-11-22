public class SessionRecord {
    private String funcionario;
    private String inicio;
    private String fim;
    private String duracao;

    public SessionRecord(String funcionario, String inicio, String fim, String duracao) {
        this.funcionario = funcionario;
        this.inicio = inicio;
        this.fim = fim;
        this.duracao = duracao;
    }

    public String getFuncionario() { return funcionario; }
    public String getInicio() { return inicio; }
    public String getFim() { return fim; }
    public String getDuracao() { return duracao; }
}
