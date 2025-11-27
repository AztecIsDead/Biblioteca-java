import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventoCrudDialog extends JDialog {

    private final GUIService gui;
    private final Evento eventoEdicao;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public EventoCrudDialog(Frame owner, GUIService guiService, Evento editar) {
        super(owner, editar == null ? "Novo Evento" : "Editar Evento", true);
        this.gui = guiService;
        this.eventoEdicao = editar;
        init();
    }

    private void init() {
        setSize(420, 300);
        setLayout(new GridLayout(7, 2, 5, 5));
        setLocationRelativeTo(getOwner());

        JTextField campoId = new JTextField();
        JTextField campoNome = new JTextField();
        JTextField campoInicio = new JTextField();
        JTextField campoLocal = new JTextField();
        JTextField campoCapNormal = new JTextField();
        JTextField campoCapVip = new JTextField();

        if (eventoEdicao != null) {
            campoId.setText(String.valueOf(eventoEdicao.getId()));
            campoId.setEnabled(false);
            campoNome.setText(eventoEdicao.getNome());
            campoInicio.setText(eventoEdicao.getInicio() == null ? "" : eventoEdicao.getInicio().format(fmt));
            campoLocal.setText(eventoEdicao.getLocal());
            campoCapNormal.setText(String.valueOf(eventoEdicao.getCapacidadeNormal()));
            campoCapVip.setText(String.valueOf(eventoEdicao.getCapacidadeVip()));
        }

        add(new JLabel("ID"));
        add(campoId);

        add(new JLabel("Título"));
        add(campoNome);

        add(new JLabel("Início (yyyy-MM-dd HH:mm)"));
        add(campoInicio);

        add(new JLabel("Local"));
        add(campoLocal);

        add(new JLabel("Capacidade Normal"));
        add(campoCapNormal);

        add(new JLabel("Capacidade VIP"));
        add(campoCapVip);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnCancelar.addActionListener(e -> dispose());

        btnSalvar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(campoId.getText().trim());
                String nome = campoNome.getText().trim();
                String local = campoLocal.getText().trim();
                int capNorm = Integer.parseInt(campoCapNormal.getText().trim());
                int capVip = Integer.parseInt(campoCapVip.getText().trim());
                LocalDateTime inicio = campoInicio.getText().trim().isEmpty()
                        ? null
                        : LocalDateTime.parse(campoInicio.getText().trim(), fmt);

                if (eventoEdicao == null) {
                    Evento novo = new Evento(
                            id, nome, inicio, local,
                            capNorm, capVip,
                            null, null, null,
                            StatusEvento.ATIVO
                    );
                    gui.adicionarEvento(novo);
                } else {
                    eventoEdicao.setNome(nome);
                    eventoEdicao.setInicio(inicio);
                    eventoEdicao.setLocal(local);
                    eventoEdicao.setCapacidadeNormal(capNorm);
                    eventoEdicao.setCapacidadeVip(capVip);
                    gui.atualizarEvento(eventoEdicao);
                }

                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dados inválidos.");
            }
        });

        add(btnSalvar);
        add(btnCancelar);
    }
}
