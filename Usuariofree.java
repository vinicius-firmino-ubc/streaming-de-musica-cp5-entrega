// Entrega CP5

public class UsuarioFree extends Usuario {

    // =========================================================
    // Atributos privados — especificos da conta Free
    // =========================================================
    private static final int MAX_PLAYLISTS = 3;
    private int contadorReproducoes;
    private int totalAnuncios;

    // =========================================================
    // Construtor
    // =========================================================

    public UsuarioFree(String nome, String email) {
        super(nome, email);
        this.contadorReproducoes = 0;
        this.totalAnuncios = 0;
    }

    // =========================================================
    // Sobrescrita de metodos (@Override)
    // =========================================================

    /**
     * Reproduz musica com regras do plano Free:
     * incrementa contador e exibe anuncio a cada 3 musicas.
     */
    @Override
    public void reproduzirMusica(Musica musica) {
        contadorReproducoes++;

        if (contadorReproducoes % 3 == 0) {
            exibirAnuncio();
        }

        super.reproduzirMusica(musica);
    }

    /**
     * Cria playlist respeitando o limite de 3.
     */
    @Override
    public void criarPlaylist(String nome) {
        if (playlists.size() >= MAX_PLAYLISTS) {
            System.out.println("Limite de " + MAX_PLAYLISTS + " playlists atingido!");
            System.out.println("Faca upgrade para Premium e tenha playlists ilimitadas!");
            return;
        }
        super.criarPlaylist(nome);
    }

    /**
     * Exibe detalhes especificos do usuario Free.
     * Usa upcasting — sobrescreve exibirDetalhes() da superclasse.
     */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes(); // chama a versao da superclasse primeiro
        System.out.println("Tipo:         Free (Gratuito)");
        System.out.println("Playlists:    " + playlists.size() + "/" + MAX_PLAYLISTS);
        System.out.println("Anuncios:     " + totalAnuncios);
    }

    // =========================================================
    // Metodos especificos de UsuarioFree
    // =========================================================

    private void exibirAnuncio() {
        totalAnuncios++;
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ANUNCIO: Assine Premium e ouca sem interrupcoes!");
        System.out.println("Planos a partir de R$ 19,90/mes");
        System.out.println("=".repeat(50) + "\n");
    }

    public void exibirUpgrade() {
        System.out.println("\nUPGRADE PARA PREMIUM");
        System.out.println("  Playlists ilimitadas | Sem anuncios | Downloads | Alta qualidade");
        System.out.println("  Planos: Mensal (R$ 19,90) | Anual (R$ 199,00) | Familiar (R$ 29,90)");
    }

    // =========================================================
    // Getters
    // =========================================================

    public int getContadorReproducoes() { return contadorReproducoes; }

    public int getLimitePlaylists() { return MAX_PLAYLISTS; }

    public int getTotalAnuncios() { return totalAnuncios; }

    @Override
    public String toString() {
        return String.format("UsuarioFree{nome='%s', email='%s', reproducoes=%d}",
            nome, email, contadorReproducoes);
    }
}