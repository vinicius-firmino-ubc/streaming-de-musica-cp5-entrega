package br.com.streaming.modelo;

import br.com.streaming.util.FormatadorTempo;

import java.util.ArrayList;

/**
 * Representa uma playlist de músicas.
 * Estende ItemReproducao — uma playlist também é reproduzível.
 * getDuracaoTotal() retorna a soma das durações de todas as músicas.
 */
public class Playlist extends ItemReproducao {

    private ArrayList<Musica> musicas;
    private static final int MAX_MUSICAS = 100;

    public Playlist(String nome) {
        super(nome);
        this.musicas = new ArrayList<>();
    }

    public Playlist() {
        this("Sem nome");
    }

    // ==================== IMPLEMENTAÇÃO DE ItemReproducao ====================

    @Override
    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) total += m.getDuracao();
        return total;
    }

    @Override
    public void reproduzir() {
        System.out.println("▶ Playlist: " + nome + " | " + musicas.size() +
                           " música(s) | " + FormatadorTempo.formatar(getDuracaoTotal()));
    }

    // ==================== GERENCIAMENTO DE MÚSICAS ====================

    public void adicionarMusica(Musica m) {
        if (m == null) {
            System.out.println("❌ Música inválida.");
            return;
        }
        if (musicas.size() >= MAX_MUSICAS) {
            System.out.println("❌ Limite de " + MAX_MUSICAS + " músicas atingido nesta playlist.");
            return;
        }
        for (Musica musica : musicas) {
            if (musica.getTitulo().equalsIgnoreCase(m.getTitulo())
                    && musica.getArtista().equalsIgnoreCase(m.getArtista())) {
                System.out.println("⚠ \"" + m.getTitulo() + "\" já está na playlist.");
                return;
            }
        }
        musicas.add(m);
    }

    public void removerMusica(String titulo) {
        boolean removido = musicas.removeIf(m -> m.getTitulo().equalsIgnoreCase(titulo));
        if (removido) System.out.println("✅ \"" + titulo + "\" removida da playlist.");
        else          System.out.println("❌ \"" + titulo + "\" não encontrada na playlist.");
    }

    public void listarMusicas() {
        if (musicas.isEmpty()) {
            System.out.println("   Playlist vazia.");
            return;
        }
        for (Musica m : musicas) {
            System.out.println("   " + m);
        }
    }

    // ==================== GETTERS ====================

    public ArrayList<Musica> getMusicas()   { return new ArrayList<>(musicas); }
    public int getTotalMusicas()            { return musicas.size(); }
    public String getPlayNome()             { return nome; }

    @Override
    public String toString() {
        return "📋 " + nome + " | " + musicas.size() + " música(s)" +
               " | " + FormatadorTempo.formatar(getDuracaoTotal());
    }
}
