import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Request implements CSVGravavel {
    public enum Status {PENDING, APPROVED, REJECTED}

    private String clienteNome;
    private String tituloLivro;
    private Status status;
    private LocalDate dueDate; // pode ser null

    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public Request(String clienteNome, String tituloLivro, Status status, LocalDate dueDate) {
        this.clienteNome = clienteNome;
        this.tituloLivro = tituloLivro;
        this.status = status;
        this.dueDate = dueDate;
    }

    public String getClienteNome(){ return clienteNome; }
    public String getTituloLivro(){ return tituloLivro; }
    public Status getStatus(){ return status; }
    public LocalDate getDueDate(){ return dueDate; }
    public void setStatus(Status s){ this.status = s; }
    public void setDueDate(LocalDate d){ this.dueDate = d; }

    @Override
    public String toCSV() {
        return clienteNome + "," + tituloLivro + "," + status.name() + "," + (dueDate == null ? "" : dueDate.format(F));
    }

    @Override
    public String getCabecalhoCSV() {
        return "Cliente,Titulo,Status,DueDate";
    }

    @Override
    public String toString() {
        return clienteNome + " -> " + tituloLivro + " [" + status.name() + "] " + (dueDate == null ? "" : dueDate.format(F));
    }
}
