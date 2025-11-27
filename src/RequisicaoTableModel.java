import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RequisicaoTableModel extends AbstractTableModel {

    private final String[] colunas = {
            "ID", "Cliente ID", "Livro", "Status", "Data"
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

        switch (columnIndex) {
            case 0: return r.getId();
            case 1: return r.getClienteId();
            case 2: return r.getLivroTitulo();
            case 3: return r.getStatus();
            case 4: return r.getData();
        }
        return null;
    }

    public void setDados(List<Requisicao> lista) {
        this.dados = lista;
        fireTableDataChanged();
    }

    public Requisicao getRequisicaoAt(int row) {
        return dados.get(row);
    }
}
