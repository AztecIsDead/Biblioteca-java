import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClienteModeloDeTable extends AbstractTableModel {
    private final String[] colNames = {"Nome", "Idade", "Devendo", "LivroAlugado"};
    private List<Cliente> clientes;

    public ClienteModeloDeTable(List<Cliente> clientes) { this.clientes = clientes; }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        fireTableDataChanged();
    }

    public Cliente getClienteAt(int row) {
        if (clientes == null) return null;
        if (row < 0 || row >= clientes.size()) return null;
        return clientes.get(row);
    }

    @Override public int getRowCount() { return clientes == null ? 0 : clientes.size(); }
    @Override public int getColumnCount() { return colNames.length; }
    @Override public String getColumnName(int column) { return colNames[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (clientes == null) return "";
        Cliente c = clientes.get(rowIndex);
        switch (columnIndex) {
            case 0: return c.getNome();
            case 1: return c.getIdade();
            case 2: return c.getStatusDevendo() ? "Sim" : "Não";
            case 3: return c.getLivroAlugado() == null ? "Nenhum" : c.getLivroAlugado();
            default: return "";
        }
    }

    @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
}
