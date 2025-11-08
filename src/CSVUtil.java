import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {
      public static <T extends CSVGravavel> void gravarCSV(List<T> lista, String caminhoArquivo) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia, nada a gravar.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {

            // Cabeçalho
            writer.write(lista.get(0).getCabecalhoCSV());
            writer.newLine();

            // Dados
            for (T item : lista) {
                writer.write(item.toCSV());
                writer.newLine();
            }

            System.out.println("Arquivo salvo em: " + caminhoArquivo);

        } catch (IOException e) {
            System.out.println("Erro ao gravar o CSV: " + e.getMessage());
        }
    }

     public static <T> List<T> lerCSV(String caminhoArquivo, Class<T> tipoClasse) {
        List<T> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeira = true;

            while ((linha = reader.readLine()) != null) {
                if (primeira) { // pula o cabeçalho
                    primeira = false;
                    continue;
                }

                String[] campos = linha.split(",");

                if (tipoClasse == Funcionario.class) {
                    String nome = campos[0];
                    int idade = Integer.parseInt(campos[1]);
                    String cargo = campos[2];
                    lista.add(tipoClasse.cast(new Funcionario(nome,idade, cargo)));
                }

                else if (tipoClasse == Cliente.class) {
                    String nome = campos[0];
                    int idade = Integer.parseInt(campos[1]);
                    boolean devendo = Boolean.parseBoolean(campos[2]);
                    lista.add(tipoClasse.cast(new Cliente(nome, idade, devendo)));
                }

                else if (tipoClasse == Livro.class) {
                    String titulo = campos[0];
                    String autor = campos[1];
                    boolean disponibilidade = Boolean.parseBoolean(campos[2]);
                    lista.add(tipoClasse.cast(new Livro(titulo, autor, disponibilidade)));
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o CSV: " + e.getMessage());
        }

        return lista;
    }
}
