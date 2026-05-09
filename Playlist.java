import java.util.ArrayList;

// Entrega CP5

public class Playlist {

    // =========================================================
    // Atributos protected — acessíveis nas subclasses
    // =========================================================
    protected String nome;
    protected ArrayList<Musica> musicas;
    protected String descricao;

    // =========================================================
    // Construtores
    // =========================================================

    public Playlist() {
        this("Nova Playlist", "Sem descricao");
    }

    public Playlist(String nome) {
        this(nome, "Sem descricao");
    }

    public Playlist(String nome, String descricao) {
        setNome(nome);
        this.descricao = (descricao == null) ? "Sem descricao" : descricao;
        this.musicas = new ArrayList<>();
    }

    // =========================================================
    // Getters
    // =========================================================

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTotalMusicas() {
        return musicas.size();
    }

    public ArrayList<Musica> getMusicas() {
        return new ArrayList<>(musicas);
    }

    // =========================================================
    // Setters com validacao
    // =========================================================

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da playlist nao pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
    }

    public void setDescricao(String descricao) {
        this.descricao = (descricao == null) ? "Sem descricao" : descricao;
    }

    // =========================================================
    // Metodos de negocio
    // =========================================================

    public void adicionarMusica(Musica musica) {
        if (musica == null) {
            throw new IllegalArgumentException("Nao e possivel adicionar uma musica nula.");
        }
        musicas.add(musica);
        System.out.println("\"" + musica.getTitulo() + "\" adicionada a playlist \"" + nome + "\"!");
    }

    public void removerMusica(int indice) {
        if (indice < 1 || indice > musicas.size()) {
            System.out.println("Indice invalido! A playlist possui " + musicas.size() + " musica(s).");
            return;
        }
        Musica removida = musicas.remove(indice - 1);
        System.out.println("\"" + removida.getTitulo() + "\" removida da playlist!");
    }

    /**
     * Reproduz todas as musicas da playlist.
     * Pode ser sobrescrito pelas subclasses com comportamento especifico.
     */
    public void reproduzir() {
        System.out.println("\nReproduzindo playlist: " + nome);
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica na playlist.");
            return;
        }
        for (int i = 0; i < musicas.size(); i++) {
            System.out.printf("  %d. %s - %s%n",
                i + 1, musicas.get(i).getTitulo(), musicas.get(i).getArtista());
        }
    }

    public void listarMusicas() {
        System.out.println("\nPlaylist: " + nome + " (" + musicas.size() + " musica(s))");
        if (descricao != null && !descricao.equals("Sem descricao")) {
            System.out.println("Descricao: " + descricao);
        }
        if (musicas.isEmpty()) {
            System.out.println("  Nenhuma musica adicionada ainda.");
            return;
        }
        for (int i = 0; i < musicas.size(); i++) {
            musicas.get(i).exibir(i + 1);
        }
    }

    public int getDuracaoTotal() {
        int total = 0;
        for (Musica m : musicas) {
            total += m.getDuracaoSegundos();
        }
        return total;
    }

    @Override
    public String toString() {
        return String.format("Playlist{nome='%s', musicas=%d}", nome, musicas.size());
    }
}