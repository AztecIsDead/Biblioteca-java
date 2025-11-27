import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelEventos extends JDialog {

    private final GUIService guiService;
    private JTable tabela;

    public static void abrir(GUIService gui) {
        new PainelEventos(null, gui).setVisible(true);
    }

    public PainelEventos(Frame owner, GUIService guiService) {
        super(owner, "Eventos", true);
        this.guiService = guiService;
        init();
    }


    private void init() {
        setSize(700, 420);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        montarTabela();
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(montarBotoes(), BorderLayout.SOUTH);
    }

    private void montarTabela() {
        List<Evento> eventos = guiService.listarEventos();

        String[] colunas = { "ID", "Título", "Vagas", "Inscritos" };
        String[][] dados = new String[eventos.size()][4];

        for (int i = 0; i < eventos.size(); i++) {
            Evento e = eventos.get(i);
            dados[i][0] = String.valueOf(e.getId());
            dados[i][1] = e.getNome();
            dados[i][2] = String.valueOf(e.getVagas());
            dados[i][3] = String.valueOf(e.getInscritos().size());
        }

        tabela = new JTable(dados, colunas);
    }

    private JPanel montarBotoes() {
        JPanel botoes = new JPanel(new FlowLayout());

        JButton btnParticipantes = new JButton("Ver Participantes");
        btnParticipantes.addActionListener(e -> verParticipantes());

        JButton btnInscrever = new JButton("Inscrever Cliente");
        btnInscrever.addActionListener(e -> inscreverCliente());

        JButton btnCriar = new JButton("Criar Evento");
        btnCriar.addActionListener(e -> {
            PainelCriarEvento.abrir(guiService);
            recarregarTabela();
        });

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> recarregarTabela());

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        botoes.add(btnParticipantes);
        botoes.add(btnInscrever);
        botoes.add(btnCriar);     // <--- botao novo aqui
        botoes.add(btnAtualizar);
        botoes.add(btnFechar);

        return botoes;
    }


    private void verParticipantes() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um evento.");
            return;
        }

        int id = Integer.parseInt(tabela.getValueAt(row, 0).toString());
        PainelParticipantesEvento.abrir(guiService, id);
    }

    private void inscreverCliente() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um evento.");
            return;
        }

        String clienteIdStr = JOptionPane.showInputDialog(this, "ID do cliente:");
        if (clienteIdStr == null) return;

        try {
            int clienteId = Integer.parseInt(clienteIdStr);
            int eventoId = Integer.parseInt(tabela.getValueAt(row, 0).toString());

            var res = guiService.inscreverEmEvento(clienteId, eventoId);
            JOptionPane.showMessageDialog(this,
                    res.isPresent() ? res.get() : "Inscrito com sucesso!");

            recarregarTabela();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        }
    }

    private void recarregarTabela() {
        remove(0); // remove JScrollPane antigo
        montarTabela();
        add(new JScrollPane(tabela), BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
