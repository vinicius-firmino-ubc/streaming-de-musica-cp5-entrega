import java.util.ArrayList;

// Entrega CP5

public class Usuario {

    // =========================================================
    // Atributos protected — visiveis nas subclasses
    // =========================================================
    protected String nome;
    protected String email;
    protected ArrayList<Playlist> playlists;
    protected ArrayList<Musica> historicoReproducao;

    // =========================================================
    // Construtor
    // =========================================================

    public Usuario(String nome, String email) {
        setNome(nome);
        setEmail(email);
        this.playlists = new ArrayList<>();
        this.historicoReproducao = new ArrayList<>();
    }

    // =========================================================
    // Metodos principais
    // =========================================================

    /**
     * Reproduz uma musica adicionando-a ao historico.
     * Sobrescrito pelas subclasses com @Override.
     */
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida!");
            return;
        }
        System.out.println("Reproduzindo: " + musica.getTitulo() + " - " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    public void exibirHistorico() {
        System.out.println("\n--- HISTORICO DE REPRODUCAO de " + nome + " ---");
        if (historicoReproducao.isEmpty()) {
            System.out.println("  Nenhuma musica reproduzida ainda.");
            return;
        }
        for (int i = 0; i < historicoReproducao.size(); i++) {
            historicoReproducao.get(i).exibir(i + 1);
        }
    }

    /** Sobrescrito nas subclasses para aplicar regras especificas */
    public void criarPlaylist(String nome) {
        Playlist nova = new Playlist(nome);
        playlists.add(nova);
        System.out.println("Playlist \"" + nome + "\" criada com sucesso!");
    }

    public void listarPlaylists() {
        System.out.println("\nPlaylists de " + nome + ":");
        if (playlists.isEmpty()) {
            System.out.println("  Nenhuma playlist criada ainda.");
            return;
        }
        for (int i = 0; i < playlists.size(); i++) {
            Playlist p = playlists.get(i);
            // Identifica se e automatica para exibir o tipo
            if (p instanceof PlaylistAutomatica) {
                PlaylistAutomatica pa = (PlaylistAutomatica) p;
                System.out.printf("  %d. [AUTO] %s (%d musica(s)) - criterio: %s%n",
                    i + 1, p.getNome(), p.getTotalMusicas(), pa.getCriterio());
            } else {
                System.out.printf("  %d. %s (%d musica(s))%n",
                    i + 1, p.getNome(), p.getTotalMusicas());
            }
        }
    }

    public Playlist getPlaylist(int indice) {
        if (indice < 1 || indice > playlists.size()) {
            System.out.println("Playlist invalida!");
            return null;
        }
        return playlists.get(indice - 1);
    }

    public int getTotalPlaylists() {
        return playlists.size();
    }

    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public int getTotalReproducoes() {
        return historicoReproducao.size();
    }

    /**
     * Metodo final — nao pode ser sobrescrito pelas subclasses.
     * Validacao critica de email que nao deve ser alterada.
     */
    public final boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    /**
     * Exibe informacoes do usuario — sobrescrito nas subclasses para
     * mostrar detalhes especificos de cada tipo.
     */
    public void exibirDetalhes() {
        System.out.println("\n--- DETALHES DO USUARIO ---");
        System.out.println("Nome:         " + nome);
        System.out.println("Email:        " + email);
        System.out.println("Reproducoes:  " + historicoReproducao.size());
        System.out.println("Playlists:    " + playlists.size());
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getEmail() { return email; }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email nao pode ser vazio.");
        }
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return String.format("Usuario{nome='%s', email='%s'}", nome, email);
    }
}