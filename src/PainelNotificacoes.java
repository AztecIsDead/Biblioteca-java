import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelNotificacoes extends JDialog {

    private final GUIService guiService;
    private final Funcionario funcionario;

    public static void abrir(GUIService gui, Funcionario f) {
        new PainelNotificacoes(null, gui, f).setVisible(true);
    }

    public PainelNotificacoes(Frame owner, GUIService guiService, Funcionario funcionario) {
        super(owner, "Notificações", true);
        this.guiService = guiService;
        this.funcionario = funcionario;
        init();
    }

    private void init() {
        setSize(500, 350);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        List<Notificacao> lista = guiService.listarNotificacoes(funcionario.getId());
        DefaultListModel<String> modelo = new DefaultListModel<>();

        for (Notificacao n : lista) modelo.addElement(n.getMensagem());

        JList<String> jlist = new JList<>(modelo);
        add(new JScrollPane(jlist), BorderLayout.CENTER);
    }
}
