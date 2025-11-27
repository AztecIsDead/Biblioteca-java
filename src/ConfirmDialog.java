import javax.swing.*;

public class ConfirmDialog {
    public static boolean confirm(JFrame owner, String mensagem) {
        int r = JOptionPane.showConfirmDialog(owner, mensagem, "Confirmar", JOptionPane.YES_NO_OPTION);
        return r == JOptionPane.YES_OPTION;
    }
    public static boolean confirm(java.awt.Component owner, String mensagem) {
        int r = JOptionPane.showConfirmDialog(owner, mensagem, "Confirmar", JOptionPane.YES_NO_OPTION);
        return r == JOptionPane.YES_OPTION;
    }
}
