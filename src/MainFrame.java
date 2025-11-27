import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final GUIService guiService;

    public MainFrame(GUIService guiService) {
        this.guiService = guiService;

        setTitle("Sistema de Biblioteca");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JLabel titulo = new JLabel("Biblioteca", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(2, 1, 15, 15));
        painel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JButton btnEntrar = new JButton("Entrar");
        JButton btnSair = new JButton("Sair");

        painel.add(btnEntrar);
        painel.add(btnSair);

        add(painel, BorderLayout.CENTER);

        btnEntrar.addActionListener(e -> TelaInicial.abrir(guiService));

        btnSair.addActionListener(e -> System.exit(0));
    }

    public static void abrir(GUIService gui) {
        SwingUtilities.invokeLater(() -> new MainFrame(gui).setVisible(true));
    }
}
