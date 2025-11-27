import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Evento {
    private int vagas;
    private LocalDate dataReserva;
    private LocalDateTime dataEvento;
    //private Cliente[] participantes;
    //Implementar lista de participantes

    public Evento(int vagas, LocalDate dataReserva, LocalDateTime dataEvento){
        this.vagas = vagas;
        this.dataReserva = dataReserva;
        this.dataEvento = dataEvento;
    }

    public int getVagas() {
        return this.vagas;
    }

    public LocalDate getDataReserva(){
        return this.dataReserva;
    }

    public LocalDateTime getDataEvento(){
        return this.dataEvento;
    }

    public boolean reservarVaga(){
        if (this.vagas > 0){
            this.vagas--;
            return true;
        }
        return false;
    }
}
