package br.com.streaming.servico;

import br.com.streaming.modelo.Musica;
import br.com.streaming.modelo.Playlist;
import br.com.streaming.modelo.Usuario;

import java.util.ArrayList;

/**
 * Gera recomendações de músicas com base no histórico do usuário.
 * Estratégia: identifica o gênero mais ouvido e sugere músicas do catálogo
 * que o usuário ainda não reproduziu.
 */
public class GeradorRecomendacoes {

    private static final String[] GENEROS_VALIDOS = {
        "Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"
    };

    /**
     * Retorna até {@code limite} músicas recomendadas para o usuário.
     * As recomendações priorizam o gênero mais ouvido no histórico.
     *
     * @param usuario  usuário alvo
     * @param catalogo catálogo completo de músicas do sistema
     * @param limite   quantidade máxima de recomendações
     * @return lista de músicas recomendadas
     */
    public static ArrayList<Musica> recomendar(Usuario usuario, ArrayList<Musica> catalogo, int limite) {
        ArrayList<Musica> historico     = usuario.getHistoricoReproducao();
        ArrayList<Musica> recomendacoes = new ArrayList<>();

        if (catalogo == null || catalogo.isEmpty()) return recomendacoes;

        String generoFavorito = identificarGeneroFavorito(historico);

        // 1ª passagem: músicas do gênero favorito ainda não ouvidas
        for (Musica m : catalogo) {
            if (recomendacoes.size() >= limite) break;
            if (generoFavorito != null
                    && m.getGenero().equalsIgnoreCase(generoFavorito)
                    && !jaOuviu(historico, m)) {
                recomendacoes.add(m);
            }
        }

        // 2ª passagem: completa com qualquer música não ouvida
        for (Musica m : catalogo) {
            if (recomendacoes.size() >= limite) break;
            if (!jaOuviu(historico, m) && !recomendacoes.contains(m)) {
                recomendacoes.add(m);
            }
        }

        return recomendacoes;
    }

    /** Exibe as recomendações no console. */
    public static void exibir(Usuario usuario, ArrayList<Musica> catalogo, int limite) {
        System.out.println("\n--- RECOMENDAÇÕES PARA " + usuario.getNome().toUpperCase() + " ---");

        ArrayList<Musica> lista = recomendar(usuario, catalogo, limite);

        if (lista.isEmpty()) {
            System.out.println("Sem recomendações no momento. Ouça mais músicas para personalizarmos!");
            return;
        }

        String favorito = identificarGeneroFavorito(usuario.getHistoricoReproducao());
        if (favorito != null) {
            System.out.println("Gênero favorito detectado: " + favorito);
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.println((i + 1) + ". " + lista.get(i));
        }
    }

    // ==================== HELPERS ====================

    private static String identificarGeneroFavorito(ArrayList<Musica> historico) {
        if (historico == null || historico.isEmpty()) return null;

        int[] contagens = new int[GENEROS_VALIDOS.length];

        for (Musica m : historico) {
            for (int i = 0; i < GENEROS_VALIDOS.length; i++) {
                if (GENEROS_VALIDOS[i].equalsIgnoreCase(m.getGenero())) {
                    contagens[i]++;
                    break;
                }
            }
        }

        int maxIdx = 0;
        for (int i = 1; i < contagens.length; i++) {
            if (contagens[i] > contagens[maxIdx]) maxIdx = i;
        }

        return contagens[maxIdx] > 0 ? GENEROS_VALIDOS[maxIdx] : null;
    }

    private static boolean jaOuviu(ArrayList<Musica> historico, Musica musica) {
        for (Musica m : historico) {
            if (m.getTitulo().equalsIgnoreCase(musica.getTitulo())) return true;
        }
        return false;
    }
}
