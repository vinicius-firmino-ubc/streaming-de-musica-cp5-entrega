package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;
import br.com.streaming.util.Validador;

/**
 * Representa uma música no sistema de streaming.
 * Estende ItemReproducao (herda reproduzir/pausar/parar)
 * e implementa getDuracaoTotal() de forma concreta.
 */
public class Musica extends ItemReproducao {

    private String artista;
    private int duracao; // em segundos
    private String genero;

    private static final String[] GENEROS_VALIDOS = {
        "Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"
    };

    public Musica(String titulo, String artista, int duracao, String genero) {
        super(titulo);
        setArtista(artista);
        setDuracao(duracao);
        setGenero(genero);
    }

    public Musica(String titulo, String artista) {
        this(titulo, artista, 180, "Pop");
    }

    // ==================== IMPLEMENTAÇÃO DE ItemReproducao ====================

    @Override
    public int getDuracaoTotal() {
        return duracao;
    }

    @Override
    public void reproduzir() {
        super.reproduzir();
        System.out.println("   🎵 " + artista + " | " + FormatadorTempo.formatar(duracao) + " | " + genero);
    }

    // ==================== GETTERS / SETTERS ====================

    public String getTitulo()  { return nome; }
    public String getArtista() { return artista; }
    public int    getDuracao() { return duracao; }
    public String getGenero()  { return genero; }

    public void setArtista(String artista) {
        if (!Validador.naoVazio(artista))
            throw new IllegalArgumentException("Artista inválido.");
        this.artista = artista.trim();
    }

    public void setDuracao(int duracao) {
        if (!Validador.duracaoValida(duracao))
            throw new IllegalArgumentException("Duração deve ser entre 1 e 3599 segundos.");
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        if (genero == null || genero.trim().isEmpty())
            throw new IllegalArgumentException("Gênero inválido.");

        String fmt = genero.trim();
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(fmt)) {
                this.genero = g;
                return;
            }
        }
        throw new IllegalArgumentException(
            "Gênero inválido. Use: Pop, Rock, Jazz, Eletrônica, Hip-Hop ou Clássica."
        );
    }

    @Override
    public String toString() {
        return "🎵 " + nome + " | " + artista +
               " | " + FormatadorTempo.formatar(duracao) + " | " + genero;
    }
}
