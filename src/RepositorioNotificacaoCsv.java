import java.util.ArrayList;
import java.util.List;

public class RepositorioNotificacaoCsv extends RepositorioCsvBase<Notificacao> {

    public RepositorioNotificacaoCsv(String arquivo) {
        super(arquivo);
    }

    @Override
    protected Notificacao fromCSV(String[] campos) {
        return Notificacao.fromCSV(campos);
    }

    @Override
    protected String toCSV(Notificacao n) {
        return n.toCSV();
    }

    @Override
    protected String getCabecalho() {
        return new Notificacao(0, "").getCabecalhoCSV();
    }

    public List<Notificacao> listarPorCliente(int clienteId) {
        List<Notificacao> r = new ArrayList<>();
        for (Notificacao n : findAll()) {
            if (n.getClienteId() == clienteId) {
                r.add(n);
            }
        }
        return r;
    }
}
