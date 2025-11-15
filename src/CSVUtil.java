import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVUtil {
    public static <T extends CSVGravavel> void gravarCSV(ArrayList<T> lista, String caminhoArquivo) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia, nada a gravar.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write(lista.get(0).getCabecalhoCSV());
            writer.newLine();
            for (T item : lista) {
                writer.write(item.toCSV());
                writer.newLine();
            }
            System.out.println("Arquivo salvo em: " + caminhoArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao gravar o CSV: " + e.getMessage());
        }
    }

    public static <T> ArrayList<T> lerCSV(String caminhoArquivo, Class<T> tipoClasse) {
        ArrayList<T> lista = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean primeira = true;
            while ((linha = reader.readLine()) != null) {
                if (primeira) { primeira = false; continue; }
                if (linha.trim().isEmpty()) continue;
                String[] campos = linha.split(",");
                if (tipoClasse == Funcionario.class) {
                    try {
                        String nome = campos[0];
                        int idade = Integer.parseInt(campos[1]);
                        String cargo = campos[2];
                        lista.add(tipoClasse.cast(new Funcionario(nome, idade, cargo)));
                    } catch (Exception e) {
                        System.out.println("Linha de Funcionario inválida, pulando: " + e.getMessage());
                    }
                } else if (tipoClasse == Cliente.class) {
                    try {
                        String nome = campos[0];
                        int idade = Integer.parseInt(campos[1]);
                        boolean devendo = Boolean.parseBoolean(campos[2]);
                        String livroAlugado = campos.length > 3 ? campos[3] : "Nenhum";
                        lista.add(tipoClasse.cast(new Cliente(nome, idade, devendo, livroAlugado)));
                    } catch (Exception e) {
                        System.out.println("Linha de Cliente inválida, pulando: " + e.getMessage());
                    }
                } else if (tipoClasse == Livro.class) {
                    try {
                        String titulo = campos[0];
                        String autor = campos[1];
                        boolean disponibilidade = Boolean.parseBoolean(campos[2]);
                        lista.add(tipoClasse.cast(new Livro(titulo, autor, disponibilidade)));
                    } catch (Exception e) {
                        System.out.println("Linha de Livro inválida, pulando: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o CSV: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Erro ao ler arquivo CSV vazio. " + e.getMessage());
            System.out.println("Essa função retornará apenas listas vazias enquanto o erro persistir.");
        }
        return lista;
    }
}
