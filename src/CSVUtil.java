import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CSVUtil {
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public static <T extends CSVGravavel> void gravarCSV(ArrayList<T> lista, String caminhoArquivo) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("Lista vazia, nada a gravar: " + caminhoArquivo);
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
                        String senha = campos.length > 4 ? campos[4] : "";
                        boolean vip = Boolean.parseBoolean(campos[5]);
                        lista.add(tipoClasse.cast(new Cliente(nome, idade, devendo, livroAlugado == null || livroAlugado.isEmpty() ? "Nenhum" : livroAlugado, senha, vip)));
                    } catch (Exception e) {
                        System.out.println("Linha de Cliente inválida, pulando: " + e.getMessage());
                    }
                } else if (tipoClasse == Livro.class) {
                    try {
                        String titulo = campos[0];
                        String autor = campos[1];
                        boolean disponibilidade = Boolean.parseBoolean(campos[2]);
                        boolean exclusividade = Boolean.parseBoolean(campos[3]);
                        lista.add(tipoClasse.cast(new Livro(titulo, autor, disponibilidade, exclusividade)));
                    } catch (Exception e) {
                        System.out.println("Linha de Livro inválida, pulando: " + e.getMessage());
                    }
                } else if (tipoClasse == Request.class) {
                    try {
                        String cliente = campos[0];
                        String titulo = campos[1];
                        Request.Status st = Request.Status.valueOf(campos[2]);
                        LocalDate due = null;
                        if (campos.length > 3 && !campos[3].trim().isEmpty()) {
                            due = LocalDate.parse(campos[3], F);
                        }
                        lista.add(tipoClasse.cast(new Request(cliente, titulo, st, due)));
                    } catch (Exception e) {
                        System.out.println("Linha de Request inválida, pulando: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o CSV: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Erro ao ler arquivo CSV vazio. " + e.getMessage());
        }
        return lista;
    }
}
