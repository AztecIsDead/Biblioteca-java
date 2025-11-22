import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SessionReader {

    public static List<SessionRecord> readAll() {
        List<SessionRecord> list = new ArrayList<>();

        try {
            File f = new File("sessions.csv");

            if (!f.exists()) {
                f.createNewFile();
                return list;
            }

            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length == 4) {
                    list.add(new SessionRecord(p[0], p[1], p[2], p[3]));
                }
            }
            br.close();

        } catch (Exception e) {
            System.out.println("Erro lendo sessions.csv: " + e.getMessage());
        }

        return list;
    }
}
