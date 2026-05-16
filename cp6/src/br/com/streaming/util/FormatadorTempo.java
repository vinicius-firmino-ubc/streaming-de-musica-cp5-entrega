package br.com.streaming.util;

/**
 * Utilitário para formatação de tempo em segundos.
 * Segue o princípio de responsabilidade única (SRP).
 */
public class FormatadorTempo {

    private FormatadorTempo() {} // impede instanciação — classe utilitária

    /**
     * Converte segundos para o formato mm:ss.
     * Exemplos: 90 → "1:30" | 3599 → "59:59"
     */
    public static String formatar(int segundos) {
        if (segundos < 0) return "0:00";
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%d:%02d", min, seg);
    }

    /**
     * Converte segundos para formato legível por extenso.
     * Exemplos: 90 → "1 min 30 s" | 3600 → "1 h 0 min 0 s"
     */
    public static String formatarExtenso(int segundos) {
        if (segundos < 0) return "0 s";
        int horas = segundos / 3600;
        int min   = (segundos % 3600) / 60;
        int seg   = segundos % 60;

        if (horas > 0) return horas + " h " + min + " min " + seg + " s";
        if (min  > 0) return min  + " min " + seg + " s";
        return seg + " s";
    }
}
