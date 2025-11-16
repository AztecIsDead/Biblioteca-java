import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class RequestTableModel extends AbstractTableModel {
    private final String[] cols = {"Cliente","Livro","Status","DueDate"};
    private List<Request> requests = new ArrayList<>();
    private static final DateTimeFormatter F = DateTimeFormatter.ISO_LOCAL_DATE;

    public RequestTableModel(List<Request> requests) { this.requests = requests; }

    public void setRequests(List<Request> requests) { this.requests = requests; fireTableDataChanged(); }
    public Request getRequestAt(int row) { if (requests == null) return null; if (row<0||row>=requests.size()) return null; return requests.get(row); }

    @Override public int getRowCount() { return requests == null ? 0 : requests.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Request r = requests.get(rowIndex);
        switch(columnIndex) {
            case 0: return r.getClienteNome();
            case 1: return r.getTituloLivro();
            case 2: return r.getStatus().name();
            case 3: return r.getDueDate() == null ? "" : r.getDueDate().format(F);
            default: return "";
        }
    }

    @Override public boolean isCellEditable(int r,int c){ return false; }
}
