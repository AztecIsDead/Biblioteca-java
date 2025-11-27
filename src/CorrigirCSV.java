import java.nio.file.*;
import java.io.IOException;

public class CorrigirCSV {
    public static void main(String[] args) {
        Path caminho = Path.of("C:/Users/usuario/IdeaProjects/Biblioteca-java/data/clientes.csv");

        try {
            String conteudo = Files.readString(caminho);
            String corrigido = conteudo.replace("\"", "").trim();
            Files.writeString(caminho, corrigido);

            System.out.println("✔ CSV corrigido com sucesso!");
        } catch (NoSuchFileException e) {
            System.out.println("❌ Arquivo não encontrado:");
            System.out.println("→ " + caminho.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
