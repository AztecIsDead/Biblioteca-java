import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelEventos extends JDialog {

    private final GUIService guiService;

    public static void abrir(GUIService gui) {
        new PainelEventos(null, gui).setVisible(true);
    }

    public PainelEventos(Frame owner, GUIService guiService) {
        super(owner, "Eventos", true);
        this.guiService = guiService;
        init();
    }

    private void init() {
        setSize(650, 400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        List<Evento> eventos = guiService.listarEventos();

        String[] colunas = { "ID", "Título", "Vagas", "Inscritos" };
        String[][] dados = new String[eventos.size()][4];

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            dados[i][0] = String.valueOf(e.getId());
            dados[i][1] = e.getNome();
            dados[i][2] = String.valueOf(e.getInscritos().size());
        }


        JTable tabela = new JTable(dados, colunas);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        add(btnFechar, BorderLayout.SOUTH);
    }
}
