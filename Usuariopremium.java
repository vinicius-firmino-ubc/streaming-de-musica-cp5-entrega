import java.util.ArrayList;

// Entrega CP5

public class UsuarioPremium extends Usuario {

    // =========================================================
    // Atributos privados — especificos da conta Premium
    // =========================================================
    private String plano; // Mensal, Anual, Familiar
    private ArrayList<Musica> musicasBaixadas;

    // =========================================================
    // Construtores — sobrecarga
    // =========================================================

    public UsuarioPremium(String nome, String email) {
        this(nome, email, "Mensal");
    }

    public UsuarioPremium(String nome, String email, String plano) {
        super(nome, email);
        setPlano(plano);
        this.musicasBaixadas = new ArrayList<>();
    }

    // =========================================================
    // Sobrescrita de metodos (@Override)
    // =========================================================

    /**
     * Reproduz musica em alta qualidade sem anuncios.
     */
    @Override
    public void reproduzirMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida!");
            return;
        }
        System.out.println("Reproduzindo em ALTA QUALIDADE: "
            + musica.getTitulo() + " - " + musica.getArtista());
        historicoReproducao.add(musica);
    }

    /**
     * Cria playlist sem limite de quantidade.
     */
    @Override
    public void criarPlaylist(String nome) {
        super.criarPlaylist(nome);
    }

    /**
     * Exibe detalhes especificos do usuario Premium.
     * Sobrescreve exibirDetalhes() da superclasse.
     */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Tipo:         Premium");
        System.out.println("Plano:        " + plano);
        System.out.println("Baixadas:     " + musicasBaixadas.size() + " musica(s)");
    }

    // =========================================================
    // Metodos especificos de UsuarioPremium
    // =========================================================

    public void baixarMusica(Musica musica) {
        if (musica == null) {
            System.out.println("Musica invalida!");
            return;
        }
        for (Musica m : musicasBaixadas) {
            if (m.getTitulo().equals(musica.getTitulo())
                    && m.getArtista().equals(musica.getArtista())) {
                System.out.println("\"" + musica.getTitulo() + "\" ja esta baixada!");
                return;
            }
        }
        musicasBaixadas.add(musica);
        System.out.println("Musica baixada: " + musica.getTitulo() + " - " + musica.getArtista());
    }

    public void listarMusicasBaixadas() {
        System.out.println("\n--- MUSICAS BAIXADAS por " + nome + " ---");
        if (musicasBaixadas.isEmpty()) {
            System.out.println("  Nenhuma musica baixada ainda.");
            return;
        }
        for (int i = 0; i < musicasBaixadas.size(); i++) {
            musicasBaixadas.get(i).exibir(i + 1);
        }
    }

    public ArrayList<Musica> getMusicasBaixadas() {
        return new ArrayList<>(musicasBaixadas);
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public String getPlano() { return plano; }

    public void setPlano(String plano) {
        if (plano == null || plano.trim().isEmpty()) {
            throw new IllegalArgumentException("Plano nao pode ser vazio.");
        }
        String[] planosValidos = {"Mensal", "Anual", "Familiar"};
        for (String p : planosValidos) {
            if (p.equalsIgnoreCase(plano.trim())) {
                this.plano = p;
                return;
            }
        }
        throw new IllegalArgumentException("Plano invalido. Use: Mensal, Anual ou Familiar.");
    }

    public int getTotalBaixadas() { return musicasBaixadas.size(); }

    @Override
    public String toString() {
        return String.format("UsuarioPremium{nome='%s', email='%s', plano='%s', baixadas=%d}",
            nome, email, plano, musicasBaixadas.size());
    }
}