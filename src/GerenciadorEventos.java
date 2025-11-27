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

    public static void criarEventoAsync(
            String nome,
            LocalDateTime inicio,
            String local,
            int capacidadeNormal,
            int capacidadeVip,
            Runnable callback
    ) {
        new Thread(() -> {
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

            if (callback != null) callback.run();
        }).start();
    }

    public static void editarEventoAsync(Evento e, Runnable callback) {
        new Thread(() -> {
            repositorio.update(e);
            if (callback != null) callback.run();
        }).start();
    }

    public static void excluirEventoAsync(int id, Runnable callback) {
        new Thread(() -> {
            repositorio.delete(id);
            if (callback != null) callback.run();
        }).start();
    }

    public static void inscreverClienteAsync(int eventoId, int clienteId, boolean vip, Runnable callback) {
        new Thread(() -> {
            Evento e = buscarPorId(eventoId);

            if (e != null && e.getStatus() == StatusEvento.ATIVO && !e.isInscrito(clienteId)) {
                e.inscreverCliente(clienteId, vip);
                repositorio.update(e);
            }

            if (callback != null) callback.run();
        }).start();
    }

    public static void cancelarInscricaoAsync(int eventoId, int clienteId, Runnable callback) {
        new Thread(() -> {
            Evento e = buscarPorId(eventoId);

            if (e != null && e.isInscrito(clienteId)) {
                e.removerCliente(clienteId);
                repositorio.update(e);
            }

            if (callback != null) callback.run();
        }).start();
    }

    public static void setStatusAsync(int eventoId, StatusEvento status, Runnable callback) {
        new Thread(() -> {
            Evento e = buscarPorId(eventoId);

            if (e != null) {
                e.setStatus(status);
                repositorio.update(e);
            }

            if (callback != null) callback.run();
        }).start();
    }
}
