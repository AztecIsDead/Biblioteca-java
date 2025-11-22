import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class SessionLogger {

    private static final String LOG_FILE = "sessions.csv";

    public static void logSession(String funcionario, Instant inicio, Instant fim) {
        if (funcionario == null || inicio == null || fim == null) return;

        Duration dur = Duration.between(inicio, fim);
        long h = dur.toHours();
        long m = dur.toMinutes() % 60;
        long s = dur.getSeconds() % 60;

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.systemDefault());

        String linha = funcionario + "," +
                fmt.format(inicio) + "," +
                fmt.format(fim) + "," +
                String.format("%02d:%02d:%02d", h, m, s);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(linha);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar log de sessão: " + e.getMessage());
        }
    }
}
