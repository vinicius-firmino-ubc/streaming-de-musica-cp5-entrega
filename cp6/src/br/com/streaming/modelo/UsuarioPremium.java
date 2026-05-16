package br.com.streaming.modelo;

import br.com.streaming.servico.Baixavel;

import java.util.ArrayList;

/**
 * Usuário do plano premium.
 * Vantagens: playlists ilimitadas, alta qualidade 320kbps, sem anúncios, downloads.
 * Implementa a interface Baixavel — único tipo de usuário com acesso a downloads.
 */
public class UsuarioPremium extends Usuario implements Baixavel {

    private String plano;
    private ArrayList<Musica> musicasBaixadas;

    private static final String[] PLANOS_VALIDOS = {"Mensal", "Anual", "Familiar"};

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email);
        setPlano(plano);
        this.musicasBaixadas = new ArrayList<>();
    }

    // ==================== IMPLEMENTAÇÃO DOS MÉTODOS ABSTRATOS ====================

    @Override
    public void reproduzirMusica(Musica musica) {
        System.out.println("🎵 [320kbps ⭐] " + musica.getTitulo()
                + " - " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    @Override
    public String getTipoUsuario() {
        return "Premium (" + plano + ")";
    }

    @Override
    public void exibirInfo() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║    USUÁRIO PREMIUM ⭐         ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ Nome:             " + nome);
        System.out.println("║ Email:            " + email);
        System.out.println("║ Plano:            " + plano);
        System.out.println("║ Playlists:        " + playlists.size() + " (ilimitadas)");
        System.out.println("║ Músicas baixadas: " + musicasBaixadas.size());
        System.out.println("║ Músicas ouvidas:  " + historicoReproducao.size());
        System.out.println("║ ✅ Download disponível");
        System.out.println("║ ✅ Alta qualidade 320kbps");
        System.out.println("║ ✅ Playlists ilimitadas");
        System.out.println("║ ✅ Sem anúncios");
        System.out.println("╚══════════════════════════════╝");
    }

    // ==================== IMPLEMENTAÇÃO DA INTERFACE Baixavel ====================

    @Override
    public void baixar(Musica musica) {
        if (musica == null) {
            System.out.println("❌ Música inválida.");
            return;
        }
        if (estaBaixada(musica)) {
            System.out.println("⚠ \"" + musica.getTitulo() + "\" já está baixada.");
            return;
        }
        musicasBaixadas.add(musica);
        System.out.println("⬇ \"" + musica.getTitulo() + "\" baixada com sucesso!");
    }

    @Override
    public void removerDownload(Musica musica) {
        if (musica == null) return;
        boolean removido = musicasBaixadas.removeIf(
            m -> m.getTitulo().equalsIgnoreCase(musica.getTitulo())
        );
        if (removido) System.out.println("🗑 \"" + musica.getTitulo() + "\" removida dos downloads.");
        else          System.out.println("❌ \"" + musica.getTitulo() + "\" não estava baixada.");
    }

    @Override
    public boolean estaBaixada(Musica musica) {
        for (Musica m : musicasBaixadas) {
            if (m.getTitulo().equalsIgnoreCase(musica.getTitulo())) return true;
        }
        return false;
    }

    @Override
    public int getTamanhoBaixados() {
        return musicasBaixadas.size();
    }

    // ==================== COMPORTAMENTOS EXCLUSIVOS ====================

    public void listarMusicasBaixadas() {
        System.out.println("\n--- MÚSICAS BAIXADAS ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("Nenhuma música baixada ainda.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            System.out.println((i + 1) + ". " + musicasBaixadas.get(i));
        }
    }

    // ==================== GETTERS / SETTERS ====================

    public String getPlano() { return plano; }

    public void setPlano(String plano) {
        if (plano == null || plano.trim().isEmpty())
            throw new IllegalArgumentException("Plano inválido.");
        String fmt = plano.trim();
        for (String p : PLANOS_VALIDOS) {
            if (p.equalsIgnoreCase(fmt)) {
                this.plano = p;
                return;
            }
        }
        throw new IllegalArgumentException("Plano inválido. Use: Mensal, Anual ou Familiar.");
    }

    public ArrayList<Musica> getMusicasBaixadas() { return musicasBaixadas; }
}
