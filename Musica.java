// Entrega CP5

public class Musica {

    // =========================================================
    // Atributos privados — nenhum acesso externo direto
    // =========================================================
    private String titulo;
    private String artista;
    private int duracaoSegundos;
    private String genero;

    /** Gêneros aceitos pelo sistema (validação case-insensitive) */
    private static final String[] GENEROS_VALIDOS = {
        "Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"
    };

    // =========================================================
    // Construtores
    // =========================================================

    /**
     * Construtor padrão — cria uma música com valores padrão.
     * Usa this() para delegar ao construtor parametrizado.
     */
    public Musica() {
        this("Sem título", "Desconhecido", 1, "Pop");
    }

    /**
     * Construtor parametrizado — garante que o objeto nasce em
     * estado válido. Lança IllegalArgumentException se algum dado
     * for inválido.
     *
     * @param titulo          título da música
     * @param artista         nome do artista
     * @param duracaoSegundos duração em segundos (1–3599)
     * @param genero          gênero musical (Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica)
     */
    public Musica(String titulo, String artista, int duracaoSegundos, String genero) {
        // Cada setter já realiza a validação — não há duplicação de lógica
        setTitulo(titulo);
        setArtista(artista);
        setDuracaoSegundos(duracaoSegundos);
        setGenero(genero);
    }

    // =========================================================
    // Getters
    // =========================================================

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracaoSegundos() {
        return duracaoSegundos;
    }

    /** Mantém compatibilidade com código anterior que usava getDuracao() */
    public int getDuracao() {
        return duracaoSegundos;
    }

    public String getGenero() {
        return genero;
    }

    // =========================================================
    // Setters com validação
    // =========================================================

    /**
     * Define o título.
     * @throws IllegalArgumentException se nulo, vazio ou apenas espaços
     */
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título não pode ser nulo ou vazio.");
        }
        this.titulo = titulo.trim();
    }

    /**
     * Define o artista.
     * @throws IllegalArgumentException se nulo, vazio ou apenas espaços
     */
    public void setArtista(String artista) {
        if (artista == null || artista.trim().isEmpty()) {
            throw new IllegalArgumentException("Artista não pode ser nulo ou vazio.");
        }
        this.artista = artista.trim();
    }

    /**
     * Define a duração em segundos.
     * @throws IllegalArgumentException se <= 0 ou >= 3600
     */
    public void setDuracaoSegundos(int duracaoSegundos) {
        if (duracaoSegundos <= 0 || duracaoSegundos >= 3600) {
            throw new IllegalArgumentException(
                "Duração deve ser maior que 0 e menor que 3600 segundos. Recebido: " + duracaoSegundos
            );
        }
        this.duracaoSegundos = duracaoSegundos;
    }

    /**
     * Define o gênero (validação case-insensitive).
     * O valor é armazenado com a capitalização canônica (ex.: "rock" → "Rock").
     * @throws IllegalArgumentException se o gênero não estiver na lista válida
     */
    public void setGenero(String genero) {
        if (genero == null) {
            throw new IllegalArgumentException("Gênero não pode ser nulo.");
        }
        for (String g : GENEROS_VALIDOS) {
            if (g.equalsIgnoreCase(genero.trim())) {
                this.genero = g; // armazena a forma canônica
                return;
            }
        }
        throw new IllegalArgumentException(
            "Gênero inválido: \"" + genero + "\". Válidos: Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica."
        );
    }

    // =========================================================
    // Métodos utilitários
    // =========================================================

    /** Converte a duração para o formato MM:SS */
    public String getDuracaoFormatada() {
        return String.format("%d:%02d", duracaoSegundos / 60, duracaoSegundos % 60);
    }

    /** Busca case-insensitive no título */
    public boolean contemTitulo(String busca) {
        if (busca == null) return false;
        return titulo.toLowerCase().contains(busca.toLowerCase());
    }

    /** Busca case-insensitive no artista */
    public boolean contemArtista(String busca) {
        if (busca == null) return false;
        return artista.toLowerCase().contains(busca.toLowerCase());
    }

    /** Exibe os dados da música formatados */
    public void exibir(int numero) {
        System.out.printf("%d. %s | %s | %s | %s%n",
            numero, titulo, artista, getDuracaoFormatada(), genero);
    }

    @Override
    public String toString() {
        return String.format("Musica{titulo='%s', artista='%s', duracao=%s, genero='%s'}",
            titulo, artista, getDuracaoFormatada(), genero);
    }
}