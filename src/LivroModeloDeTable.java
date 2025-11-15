import javax.swing.table.AbstractTableModel;
import java.util.List;
public class LivroModeloDeTable extends AbstractTableModel {
    private final String[] infoColunas = {"Titulo", "Autor", "Disponivel"};
    private List<Livro> livros;

    public LivroModeloDeTable(List<Livro> livros){this.livros = livros;}

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
        fireTableDataChanged();
    }
    public Livro getLivroAt(int row){
        if (row < 0 || row >= livros.size()) return null;
        return livros.get(row);
    }
    @Override
    public int getRowCount(){return livros == null ? 0: livros.size();}
    @Override
    public int getColumnCount(){return infoColunas.length;}
    @Override
    public String getColumnName(int column){return infoColunas[column];}
    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
        Livro livro = livros.get(rowIndex);
        switch (columnIndex){
            case 0: return livro.getTitulo();
            case 1: return livro.getAutor();
            case 2: return livro.isDisponibilidade() ? "Sim" : "Não";
        }
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }
}
