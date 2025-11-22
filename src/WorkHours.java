import java.io.*;
import java.util.*;

public class WorkHours {
    private static final String FILE = "work_hours.csv";
    public static Map<String, Long> load() {
        Map<String, Long> map = new HashMap<>();
        try {
            File f = new File(FILE);
            if (!f.exists()) return map;

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;

            while ((line = br.readLine()) != null) {
                String[] p = line.split(";");
                if (p.length == 2) {
                    map.put(p[0], Long.parseLong(p[1]));
                }
            }
            br.close();
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }
    public static void save(Map<String, Long> map) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(FILE));
            for (String nome : map.keySet()) {
                pw.println(nome + ";" + map.get(nome));
            }
            pw.close();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
