import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelRequisicoes extends JDialog {

    private final GUIService guiService;
    private JTable tabela;
    private List<Requisicao> requisicoes;

    public static void abrir(GUIService gui) {
        new PainelRequisicoes(null, gui).setVisible(true);
    }

    public PainelRequisicoes(Frame owner, GUIService guiService) {
        super(owner, "Requisições Pendentes", true);
        this.guiService = guiService;
        init();
    }

    private void init() {
        setSize(700, 400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        requisicoes = guiService.listarRequisicoesPendentes();

        String[] colunas = { "ID", "Cliente", "Livro", "Data", "Status" };
        String[][] dados = new String[requisicoes.size()][5];

        for (int i = 0; i < requisicoes.size(); i++) {
            Requisicao r = requisicoes.get(i);
            dados[i][0] = String.valueOf(r.getId());
            dados[i][1] = String.valueOf(r.getClienteId());
            dados[i][2] = r.getLivroTitulo();
            dados[i][3] = r.getData().toString();
            dados[i][4] = r.getStatus();
        }

        tabela = new JTable(dados, colunas);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel botoes = new JPanel();
        JButton btnAprovar = new JButton("Aprovar");
        JButton btnRejeitar = new JButton("Rejeitar");

        botoes.add(btnAprovar);
        botoes.add(btnRejeitar);

        add(botoes, BorderLayout.SOUTH);

        btnAprovar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) return;
            String id = tabela.getValueAt(linha, 0).toString();
            guiService.processarRequisicao(id, true);
            dispose();
            abrir(guiService);
        });

        btnRejeitar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha < 0) return;
            String id = tabela.getValueAt(linha, 0).toString();
            guiService.processarRequisicao(id, false);
            dispose();
            abrir(guiService);
        });
    }
}
