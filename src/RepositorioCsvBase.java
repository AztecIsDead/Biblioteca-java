import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class RepositorioCsvBase<T extends CSVGravavel> {

    protected final String arquivo;

    public RepositorioCsvBase(String arquivo) {
        this.arquivo = arquivo;
        garantirArquivo();
    }

    private void garantirArquivo() {
        try {
            File f = new File(arquivo);
            File dir = f.getParentFile();
            if (dir != null && !dir.exists()) dir.mkdirs();
            if (!f.exists()) f.createNewFile();
        } catch (Exception ignored) {}
    }

    public List<T> findAll() {
        List<T> lista = new ArrayList<>();

        try {
            File f = new File(arquivo);
            if (!f.exists()) return lista;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String linha;
            boolean skipHeader = true;

            while ((linha = br.readLine()) != null) {

                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                if (linha.trim().isEmpty()) continue;

                String[] campos = CSVUtil.parseLine(linha);
                lista.add(fromCSV(campos));
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void salvarTodos(List<T> lista) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo));

            if (!lista.isEmpty()) {
                bw.write(lista.get(0).getCabecalhoCSV());
                bw.newLine();
            }

            for (T obj : lista) {
                bw.write(toCSV(obj));
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract T fromCSV(String[] campos);

    protected abstract String toCSV(T obj);

    protected abstract String getCabecalho();
}
