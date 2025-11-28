// RepositorioRequisicaoCsv.java
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioRequisicaoCsv {
    private final String arquivo;

    public RepositorioRequisicaoCsv(String arquivo) {
        this.arquivo = arquivo;
        try {
            File f = new File(arquivo);
            if (f.getParentFile() != null) f.getParentFile().mkdirs();
            if (!f.exists()) f.createNewFile();
        } catch (Exception e) {
            System.err.println("Erro ao criar arquivo de requisicoes: " + e.getMessage());
        }
    }

    public List<Requisicao> findAll() {
        List<Requisicao> lista = new ArrayList<>();
        try {
            List<String[]> linhas = CSVUtil.lerCSV(arquivo);
            if (linhas.isEmpty()) return lista;
            boolean primeira = true;
            for (String[] s : linhas) {
                if (primeira) { primeira = false; continue; }
                try {
                    Requisicao r = Requisicao.fromCSV(s);
                    lista.add(r);
                } catch (Exception ex) {
                    System.err.println("Linha de requisição malformada: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ler requisicoes: " + e.getMessage());
        }
        return lista;
    }

    public void salvarTodos(List<Requisicao> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {
            if (lista == null || lista.isEmpty()) {
                bw.write("id,clienteId,livroId,livroTitulo,status,data");
                bw.newLine();
                return;
            }
            bw.write(lista.getFirst().getCabecalhoCSV());
            bw.newLine();
            for (Requisicao r : lista) {
                bw.write(r.toCSV());
                bw.newLine();
            }
        } catch (Exception e) {
            System.err.println("Erro ao salvar requisicoes: " + e.getMessage());
        }
    }
}
