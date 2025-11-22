import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SessionTableModel extends AbstractTableModel {

    private String[] colunas = {"Funcionário", "Início", "Fim", "Duração"};
    private List<SessionRecord> registros;

    public SessionTableModel(List<SessionRecord> registros) {
        this.registros = registros;
    }

    public void setRegistros(List<SessionRecord> registros) {
        this.registros = registros;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return registros == null ? 0 : registros.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int c) {
        return colunas[c];
    }

    @Override
    public Object getValueAt(int r, int c) {
        SessionRecord rec = registros.get(r);
        switch (c) {
            case 0: return rec.getFuncionario();
            case 1: return rec.getInicio();
            case 2: return rec.getFim();
            case 3: return rec.getDuracao();
        }
        return null;
    }
}
