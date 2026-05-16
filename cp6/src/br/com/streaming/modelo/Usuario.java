package br.com.streaming.modelo;

import br.com.streaming.util.Validador;

import java.util.ArrayList;

/**
 * Classe abstrata que representa um usuário genérico do sistema.
 * Define o contrato que UsuarioFree e UsuarioPremium devem cumprir.
 */
public abstract class Usuario {

    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
        this.playlists           = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    // ==================== MÉTODOS ABSTRATOS ====================

    public abstract void reproduzirMusica(Musica musica);
    public abstract String getTipoUsuario();
    public abstract void exibirInfo();

    // ==================== MÉTODOS CONCRETOS ====================

    public void reproduzirPlaylist(Playlist playlist) {
        System.out.println("\n▶ Reproduzindo playlist: " + playlist.getPlayNome());
        System.out.println("Usuário: " + nome + " (" + getTipoUsuario() + ")");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

        ArrayList<Musica> musicas = playlist.getMusicas();

        if (musicas.isEmpty()) {
            System.out.println("Playlist vazia.");
            return;
        }

        for (Musica m : musicas) {
            reproduzirMusica(m); // POLIMORFISMO
        }

        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("✅ Playlist finalizada! " + musicas.size() + " música(s) reproduzida(s).");
    }

    public void exibirHistorico() {
        System.out.println("\n--- HISTÓRICO DE " + nome.toUpperCase() + " ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("Nenhuma música reproduzida ainda.");
            return;
        }
        for (int i = 0; i < historicoReproducao.size(); i++) {
            Musica m = historicoReproducao.get(i);
            System.out.println((i + 1) + ". " + m.getTitulo() + " - " + m.getArtista());
        }
        System.out.println("Total: " + historicoReproducao.size() + " música(s).");
    }

    public void adicionarMusica(Musica musica) {
        if (musica == null) return;

        Playlist playlist = null;
        for (Playlist p : playlists) {
            if (p.getPlayNome().equalsIgnoreCase(musica.getGenero())) {
                playlist = p;
                break;
            }
        }
        if (playlist == null) {
            playlist = criarPlaylistPorGenero(musica.getGenero());
            playlists.add(playlist);
        }
        playlist.adicionarMusica(musica);
    }

    protected Playlist criarPlaylistPorGenero(String genero) {
        return new Playlist(genero);
    }

    public void listarMusicas() {
        if (playlists.isEmpty()) {
            System.out.println("Usuário não possui músicas.");
            return;
        }
        for (Playlist p : playlists) {
            System.out.println("\n" + p);
            p.listarMusicas();
        }
    }

    // ==================== GETTERS / SETTERS ====================

    public String getNome()  { return nome; }
    public String getEmail() { return email; }
    public ArrayList<Playlist> getPlaylists()          { return playlists; }
    public ArrayList<Musica>  getHistoricoReproducao() { return historicoReproducao; }

    public void setNome(String nome) {
        if (!Validador.naoVazio(nome))
            throw new IllegalArgumentException("Nome inválido.");
        this.nome = nome.trim();
    }

    public void setEmail(String email) {
        if (!Validador.emailValido(email))
            throw new IllegalArgumentException("Email inválido.");
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return "👤 " + nome + " | " + getTipoUsuario() + " | " + email;
    }
}
