import java.util.*;

public class MigradorSenhas {
    public static void migrar(String arquivoClientes) {
        RepositorioClienteCsv repo = new RepositorioClienteCsv(arquivoClientes);
        List<Cliente> lista = repo.findAll();
        List<Cliente> atualizados = new ArrayList<>();
        for (Cliente c : lista) {
            String senha = c.getSenhaHash();
            if (senha != null && !senha.contains(":")) {
                String hash = SenhaUtil.gerarHash(senha);
                c.setSenhaHash(hash);
            }
            atualizados.add(c);
        }
        repo.salvarTodos(atualizados);
        System.out.println("Migração concluída. Total de clientes migrados: " + atualizados.size());
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Uso: java MigradorSenhas data/clientes.csv");
            System.exit(1);
        }
        migrar(args[0]);
    }
}
