import java.io.*;
import java.util.*;

public class SessionCSV {

    private static final String FILE = "sessions.csv";

    public static long loadTotalSeconds(String nome) {
        File f = new File(FILE);
        if (!f.exists()) return 0;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 2 && p[0].equalsIgnoreCase(nome)) {
                    return Long.parseLong(p[1]);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler sessions.csv: " + e.getMessage());
        }
        return 0;
    }

    public static void saveTotalSeconds(String nome, long seconds) {
        Map<String, Long> map = new HashMap<>();

        File f = new File(FILE);
        if (f.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] p = line.split(",");
                    if (p.length == 2) {
                        map.put(p[0], Long.parseLong(p[1]));
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro lendo sessions.csv: " + e.getMessage());
            }
        }

        map.put(nome, seconds);

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (String n : map.keySet()) {
                pw.println(n + "," + map.get(n));
            }
        } catch (Exception e) {
            System.out.println("Erro salvando sessions.csv: " + e.getMessage());
        }
    }
}
