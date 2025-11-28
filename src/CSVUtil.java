import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVUtil {

    public static String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            s = s.replace("\"", "\"\"");
            return "\"" + s + "\"";
        }
        return s;
    }

    public static String[] parseLine(String linha) {
        if (linha == null) return new String[0];
        List<String> campos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean emAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);

            if (c == '"') {
                if (emAspas && i + 1 < linha.length() && linha.charAt(i + 1) == '"') {
                    atual.append('"');
                    i++;
                } else {
                    emAspas = !emAspas;
                }
            } else if ((c == ',' || c == ';') && !emAspas) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        campos.add(atual.toString());
        campos.replaceAll(String::trim);
        return campos.toArray(new String[0]);
    }

    public static List<String[]> lerCSV(String arquivo) {
        List<String[]> linhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;
                linhas.add(parseLine(linha));
            }
        } catch (Exception e) {
            return new ArrayList<>();
        }

        return linhas;
    }

    public static void gravarCSV(String arquivo, List<? extends CSVGravavel> objetos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivo))) {

            if (objetos == null || objetos.isEmpty()) {
                bw.write("");
                return;
            }

            bw.write(objetos.getFirst().getCabecalhoCSV());
            bw.newLine();

            for (CSVGravavel o : objetos) {
                bw.write(o.toCSV());
                bw.newLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static List<Integer> splitToIntList(String s) {
        List<Integer> lista = new ArrayList<>();

        if (s == null || s.isEmpty())
            return lista;

        String[] parts = s.split(",");

        for (String p : parts) {
            if (!p.isEmpty()) {
                try {
                    lista.add(Integer.parseInt(p.trim()));
                } catch (NumberFormatException ignored) {}
            }
        }

        return lista;
    }

}
