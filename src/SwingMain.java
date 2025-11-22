import javax.swing.*;
import java.awt.*;

public class SwingMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            showEntryMenu();
        });
    }
    private static void showEntryMenu() {
        String[] options = {"Funcionário", "Cliente", "Sair"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Você é funcionário ou cliente?",
                "Bem-vindo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == 0) {
            showFuncionarioLogin();
        } else if (choice == 1) {
            showClientFrame();
        } else {
            System.exit(0);
        }
    }
    private static void showFuncionarioLogin() {
        JPanel panel = new JPanel(new GridLayout(0,1));
        JTextField tfNome = new JTextField();
        JPasswordField pfSenha = new JPasswordField();
        panel.add(new JLabel("Nome do funcionário:"));
        panel.add(tfNome);
        panel.add(new JLabel("Senha:"));
        panel.add(pfSenha);

        int ok = JOptionPane.showConfirmDialog(null, panel, "Login Funcionário", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (ok != JOptionPane.OK_OPTION) {
            showEntryMenu();
            return;
        }

        String nome = tfNome.getText().trim();
        String senha = new String(pfSenha.getPassword());

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
            showEntryMenu();
            return;
        }
        if (!Config.FUNCIONARIO_SENHA.equals(senha)) {
            JOptionPane.showMessageDialog(null, "Senha incorreta.", "Erro", JOptionPane.ERROR_MESSAGE);
            showEntryMenu();
            return;
        }
        Catalogo c = new Catalogo();
        boolean encontrado = false;
        for (Funcionario f : c.getFuncionariosCadastrados()) {
            if (f.getNome().equalsIgnoreCase(nome)) { encontrado = true; break; }
        }

        if (!encontrado) {
            int create = JOptionPane.showConfirmDialog(null,
                    "Funcionário não encontrado. Deseja criar um novo funcionário com esse nome?",
                    "Criar funcionário",
                    JOptionPane.YES_NO_OPTION);
            if (create == JOptionPane.YES_OPTION) {
                Funcionario novo = new Funcionario(nome, 18, "cargo");
                c.addFuncionario(novo);
                JOptionPane.showMessageDialog(null, "Funcionário criado. Você será logado como: " + nome);
            } else {
                showEntryMenu();
                return;
            }
        }
        MainFrame f = new MainFrame(nome);
        f.setVisible(true);

    }
    private static void showClientFrame() {
        ClientFrame cf = new ClientFrame();
        cf.setVisible(true);
    }
}
