import javax.swing.table.AbstractTableModel;
import java.util.List;

public class FuncionarioModeloDeTable extends AbstractTableModel {
    private final String[] colNames = {"Nome", "Idade", "Cargo"};
    private List<Funcionario> funcionarios;

    public FuncionarioModeloDeTable(List<Funcionario> funcionarios) { this.funcionarios = funcionarios; }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
        fireTableDataChanged();
    }

    public Funcionario getFuncionarioAt(int row) {
        if (funcionarios == null) return null;
        if (row < 0 || row >= funcionarios.size()) return null;
        return funcionarios.get(row);
    }

    @Override public int getRowCount() { return funcionarios == null ? 0 : funcionarios.size(); }
    @Override public int getColumnCount() { return colNames.length; }
    @Override public String getColumnName(int column) { return colNames[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (funcionarios == null) return "";
        Funcionario f = funcionarios.get(rowIndex);
        switch (columnIndex) {
            case 0: return f.getNome();
            case 1: return f.getIdade();
            case 2: return f.getCargo();
            default: return "";
        }
    }

    @Override public boolean isCellEditable(int rowIndex, int columnIndex) { return false; }
}
