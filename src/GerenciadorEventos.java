import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorEventos {

    private static final String ARQUIVO = "eventos.csv";
    private static RepositorioEventoCsv repositorio = new RepositorioEventoCsv(ARQUIVO);

    public static List<Evento> listarTodos() {
        return repositorio.findAll();
    }

    public static Evento buscarPorId(int id) {
        return repositorio.findById(id).orElse(null);
    }

    public static Evento criarEvento(
            String nome,
            LocalDateTime inicio,
            String local,
            int capacidadeNormal,
            int capacidadeVip
    ) {
        int id = repositorio.proximoId();

        Evento novo = new Evento(
                id,
                nome,
                inicio,
                local,
                capacidadeNormal,
                capacidadeVip,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                StatusEvento.ATIVO
        );

        repositorio.save(novo);
        return novo;
    }

    public static void editarEvento(Evento e) {
        repositorio.update(e);
    }

    public static void excluirEvento(int id) {
        repositorio.delete(id);
    }

    public static boolean inscreverCliente(int eventoId, int clienteId, boolean vip) {
        Evento e = buscarPorId(eventoId);

        if (e == null || e.getStatus() != StatusEvento.ATIVO)
            return false;

        if (e.isInscrito(clienteId))
            return false;

        e.inscreverCliente(clienteId, vip);
        repositorio.update(e);

        return true;
    }

    public static boolean cancelarInscricao(int eventoId, int clienteId) {
        Evento e = buscarPorId(eventoId);

        if (e == null)
            return false;

        if (!e.isInscrito(clienteId))
            return false;

        e.removerCliente(clienteId);
        repositorio.update(e);

        return true;
    }

    public static void setStatus(int eventoId, StatusEvento status) {
        Evento e = buscarPorId(eventoId);
        if (e == null) return;

        e.setStatus(status);
        repositorio.update(e);
    }
}
