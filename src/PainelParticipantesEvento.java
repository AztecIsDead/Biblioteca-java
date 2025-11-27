import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelParticipantesEvento extends JDialog {

    private final GUIService guiService;
    private final int eventoId;

    public static void abrir(GUIService gui, int eventoId) {
        new PainelParticipantesEvento(null, gui, eventoId).setVisible(true);
    }

    public PainelParticipantesEvento(Frame owner, GUIService guiService, int eventoId) {
        super(owner, "Participantes do Evento", true);
        this.guiService = guiService;
        this.eventoId = eventoId;
        init();
    }

    private void init() {
        setSize(500, 400);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        Evento e = guiService.findEventoById(eventoId).orElse(null);
        if (e == null) {
            JOptionPane.showMessageDialog(this, "Evento não encontrado!");
            dispose();
            return;
        }

        List<Integer> inscritos = e.getInscritos();

        String[] col = {"ID Cliente", "Nome"};
        String[][] dados = new String[inscritos.size()][2];

        for (int i = 0; i < inscritos.size(); i++) {
            int cid = inscritos.get(i);
            var c = guiService.findClienteById(cid);
            dados[i][0] = String.valueOf(cid);
            dados[i][1] = c.map(Cliente::getNome).orElse("Desconhecido");
        }

        JTable tabela = new JTable(dados, col);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton fechar = new JButton("Fechar");
        fechar.addActionListener(e2 -> dispose());
        add(fechar, BorderLayout.SOUTH);
    }
}
