import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RequestTableModel extends AbstractTableModel {

    private final String[] colunas = {
            "ID", "Livro", "Status", "Data"
    };

    private List<Requisicao> dados = new ArrayList<>();

    @Override
    public int getRowCount() {
        return dados.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Requisicao r = dados.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> r.getId();
            case 1 -> r.getLivroTitulo();
            case 2 -> r.getStatus();
            case 3 -> r.getData();
            default -> null;
        };
    }

    public void setDados(List<Requisicao> lista) {
        this.dados = lista;
        fireTableDataChanged();
    }

    public Requisicao getRequisicaoAt(int row) {
        return dados.get(row);
    }
}
