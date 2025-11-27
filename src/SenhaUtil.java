public class SenhaUtil {
    public static String gerarHash(String senha) {
        if (senha == null) return null;
        return Integer.toHexString(senha.hashCode());
    }
}
