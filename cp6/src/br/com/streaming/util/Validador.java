package br.com.streaming.util;

/**
 * Utilitário centralizado de validação.
 * Segue o princípio de responsabilidade única (SRP).
 */
public class Validador {

    private Validador() {} // impede instanciação — classe utilitária

    /** Verifica se a string não é nula e não está vazia. */
    public static boolean naoVazio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }

    /** Verifica se a duração está no intervalo permitido (1 a 3599 segundos). */
    public static boolean duracaoValida(int segundos) {
        return segundos > 0 && segundos < 3600;
    }

    /**
     * Validação básica de e-mail: deve conter '@' e pelo menos um '.' após o '@'.
     */
    public static boolean emailValido(String email) {
        if (!naoVazio(email)) return false;
        int arroba = email.indexOf('@');
        if (arroba < 1) return false;
        int ponto = email.indexOf('.', arroba);
        return ponto > arroba + 1 && ponto < email.length() - 1;
    }
}
