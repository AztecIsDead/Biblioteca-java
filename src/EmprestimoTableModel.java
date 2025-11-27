import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoTableModel extends AbstractTableModel {
    private final String[] col = {"ID", "Cliente", "Livro", "Empréstimo", "Prevista", "Devolução", "Devolvido"};
    private List<Emprestimo> dados = new ArrayList<>();
    private final DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

    public EmprestimoTableModel() {}
    public void setDados(List<Emprestimo> lista) { this.dados = (lista == null) ? new ArrayList<>() : new ArrayList<>(lista); fireTableDataChanged(); }
    public Emprestimo getEmprestimoAt(int i) { return dados.get(i); }

    @Override public int getRowCount() { return dados.size(); }
    @Override public int getColumnCount() { return col.length; }
    @Override public String getColumnName(int c) { return col[c]; }
    @Override public Object getValueAt(int row, int c) {
        Emprestimo e = dados.get(row);
        switch (c) {
            case 0: return e.getId();
            case 1: return e.getClienteId();
            case 2: return e.getLivroId();
            case 3: return e.getDataEmprestimo() == null ? "" : e.getDataEmprestimo().format(fmt);
            case 4: return e.getDataPrevista() == null ? "" : e.getDataPrevista().format(fmt);
            case 5: return e.getDataDevolucao() == null ? "" : e.getDataDevolucao().format(fmt);
            case 6: return e.isDevolvido();
            default: return null;
        }
    }
}
