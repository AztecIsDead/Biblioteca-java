import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel {
    private final String[] col = {"ID", "Nome", "Idade", "Usuário", "Tipo"};
    private List<Cliente> dados = new ArrayList<>();

    public ClienteTableModel() {}
    public void setDados(List<Cliente> lista) { this.dados = (lista == null) ? new ArrayList<>() : new ArrayList<>(lista); fireTableDataChanged(); }
    public Cliente getClienteAt(int i) { return dados.get(i); }

    @Override public int getRowCount() { return dados.size(); }
    @Override public int getColumnCount() { return col.length; }
    @Override public String getColumnName(int c) { return col[c]; }
    @Override public Object getValueAt(int row, int column) {
        Cliente c = dados.get(row);
        return switch (column) {
            case 0 -> c.getId();
            case 1 -> c.getNome();
            case 2 -> c.getIdade();
            case 3 -> c.getUsuario();
            case 4 -> c.getTipo() == null ? "" : c.getTipo().name();
            default -> null;
        };
    }
}
