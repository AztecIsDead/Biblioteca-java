import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LivroTableModel extends AbstractTableModel {
    private final String[] col = {"ID", "Título", "Autor", "Raro", "Total", "Disponíveis"};
    private List<Livro> dados = new ArrayList<>();

    public LivroTableModel() {}
    public void setDados(List<Livro> lista) { this.dados = (lista == null) ? new ArrayList<>() : new ArrayList<>(lista); fireTableDataChanged(); }
    public Livro getLivroAt(int i) { return dados.get(i); }

    @Override public int getRowCount() { return dados.size(); }
    @Override public int getColumnCount() { return col.length; }
    @Override public String getColumnName(int c) { return col[c]; }
    @Override public Object getValueAt(int row, int col) {
        Livro l = dados.get(row);
        return switch (col) {
            case 0 -> l.getId();
            case 1 -> l.getTitulo();
            case 2 -> l.getAutor();
            case 3 -> l.isRaro();
            case 4 -> l.getTotalCopias();
            case 5 -> l.getCopiasDisponiveis();
            default -> null;
        };
    }
}
