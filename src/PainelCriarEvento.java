import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PainelCriarEvento extends JDialog {

    private final GUIService guiService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void abrir(GUIService gui) {
        new PainelCriarEvento(null, gui).setVisible(true);
    }

    public PainelCriarEvento(Frame owner, GUIService guiService) {
        super(owner, "Criar Evento", true);
        this.guiService = guiService;
        init();
    }

    private void init() {
        setSize(420, 350);
        setLocationRelativeTo(getOwner());
        setLayout(new GridLayout(7, 2, 10, 10));

        // ---- CAMPOS ----
        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField();

        JLabel lblData = new JLabel("Data e hora (yyyy-MM-dd HH:mm):");
        JTextField txtData = new JTextField();

        JLabel lblLocal = new JLabel("Local:");
        JTextField txtLocal = new JTextField();

        JLabel lblCapNormal = new JLabel("Capacidade Normal:");
        JTextField txtCapNormal = new JTextField();

        JLabel lblCapVip = new JLabel("Capacidade VIP:");
        JTextField txtCapVip = new JTextField();

        JLabel lblStatus = new JLabel("Status:");
        JComboBox<StatusEvento> cbStatus = new JComboBox<>(StatusEvento.values());

        JButton btnCriar = new JButton("Criar");
        JButton btnCancelar = new JButton("Cancelar");

        add(lblNome); add(txtNome);
        add(lblData); add(txtData);
        add(lblLocal); add(txtLocal);
        add(lblCapNormal); add(txtCapNormal);
        add(lblCapVip); add(txtCapVip);
        add(lblStatus); add(cbStatus);
        add(btnCriar); add(btnCancelar);

        btnCriar.addActionListener(e -> {
            try {
                String nome = txtNome.getText().trim();
                String local = txtLocal.getText().trim();
                String dataStr = txtData.getText().trim();

                if (nome.isEmpty() || local.isEmpty() || dataStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                    return;
                }

                LocalDateTime inicio;
                try {
                    inicio = LocalDateTime.parse(dataStr, FORMATTER);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Data inválida. Use o formato: yyyy-MM-dd HH:mm");
                    return;
                }

                int capNormal = Integer.parseInt(txtCapNormal.getText().trim());
                int capVip = Integer.parseInt(txtCapVip.getText().trim());
                StatusEvento status = (StatusEvento) cbStatus.getSelectedItem();

                int id = guiService.listarEventos().size() + 1;

                Evento novo = new Evento(
                        id,
                        nome,
                        inicio,
                        local,
                        capNormal,
                        capVip,
                        null,
                        null,
                        null,
                        status
                );

                guiService.adicionarEvento(novo);

                JOptionPane.showMessageDialog(this, "Evento criado com sucesso!");
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacidades devem ser números.");
            }
        });

        btnCancelar.addActionListener(e -> dispose());
    }
}
