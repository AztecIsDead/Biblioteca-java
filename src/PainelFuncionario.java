import javax.swing.*;
import java.awt.*;

public class PainelFuncionario extends JDialog {

    private final GUIService guiService;
    private final Funcionario funcionario;

    public static void abrir(GUIService gui, Funcionario f) {
        new PainelFuncionario(null, gui, f).setVisible(true);
    }

    public PainelFuncionario(Frame owner, GUIService guiService, Funcionario funcionario) {
        super(owner, "Painel do Funcionário", true);
        this.guiService = guiService;
        this.funcionario = funcionario;
        init();
    }

    private void init() {
        setSize(600, 450);
        setLocationRelativeTo(getOwner());
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Painel do Funcionário: " + funcionario.getNome(), SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        JPanel botoes = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton btnRequisicoes = new JButton("Requisições Pendentes");
        JButton btnEventos = new JButton("Gerenciar Eventos");
        JButton btnNotificacoes = new JButton("Ver Notificações");
        JButton btnSair = new JButton("Sair");

        botoes.add(btnRequisicoes);
        botoes.add(btnEventos);
        botoes.add(btnNotificacoes);
        botoes.add(btnSair);

        add(botoes, BorderLayout.CENTER);

        btnRequisicoes.addActionListener(e -> PainelRequisicoes.abrir(guiService));
        btnEventos.addActionListener(e -> PainelEventos.abrir(guiService));
        btnNotificacoes.addActionListener(e -> PainelNotificacoes.abrir(guiService, funcionario));
        btnSair.addActionListener(e -> dispose());
    }
}
