import java.util.ArrayList;

// Entrega CP5

/**
 * Subclasse PlaylistAutomatica - CP5
 * Gerada automaticamente pelo sistema com base em um criterio.
 * Herda de Playlist e sobrescreve reproduzir() com comportamento especifico.
 *
 * Criterios validos: "top", "recomendadas", "recentes"
 */
public class PlaylistAutomatica extends Playlist {

    // =========================================================
    // Atributos privados — especificos de playlist automatica
    // =========================================================
    private String criterio; // "top", "recomendadas", "recentes"

    // =========================================================
    // Construtores
    // =========================================================

    /**
     * Construtor com nome e criterio.
     * Chama super() para inicializar os atributos da superclasse.
     *
     * @param nome     nome da playlist automatica
     * @param criterio criterio de selecao: "top", "recomendadas" ou "recentes"
     */
    public PlaylistAutomatica(String nome, String criterio) {
        super(nome, "Playlist gerada automaticamente pelo sistema");
        setCriterio(criterio);
    }

    // =========================================================
    // Sobrescrita de metodos (@Override)
    // =========================================================

    /**
     * Reproduz a playlist automatica exibindo informacoes do criterio.
     * Chama super.reproduzir() para listar as musicas.
     */
    @Override
    public void reproduzir() {
        System.out.println("\n[Playlist Automatica]: " + nome);
        System.out.println("Criterio: " + criterio);
        System.out.println("Total de musicas: " + musicas.size());
        super.reproduzir();
    }

    // =========================================================
    // Metodos especificos de PlaylistAutomatica
    // =========================================================

    /**
     * Atualiza as musicas da playlist com base no criterio e no catalogo completo.
     * - "top"         : primeiras 10 musicas do catalogo (simula as mais tocadas)
     * - "recomendadas": musicas com genero variado (uma de cada genero disponivel)
     * - "recentes"    : ultimas 5 musicas adicionadas ao catalogo
     *
     * @param todasMusicas catalogo completo de musicas do sistema
     */
    public void atualizar(ArrayList<Musica> todasMusicas) {
        musicas.clear();

        if (todasMusicas == null || todasMusicas.isEmpty()) {
            System.out.println("Catalogo vazio, nao foi possivel gerar a playlist.");
            return;
        }

        if (criterio.equals("top")) {
            // Simula as mais tocadas: pega ate 10 musicas do catalogo
            int limite = Math.min(10, todasMusicas.size());
            for (int i = 0; i < limite; i++) {
                musicas.add(todasMusicas.get(i));
            }

        } else if (criterio.equals("recomendadas")) {
            // Uma musica de cada genero disponivel
            String[] generos = {"Pop", "Rock", "Jazz", "Eletrônica", "Hip-Hop", "Clássica"};
            for (String genero : generos) {
                for (Musica m : todasMusicas) {
                    if (m.getGenero().equalsIgnoreCase(genero)) {
                        musicas.add(m);
                        break; // apenas uma por genero
                    }
                }
            }

        } else if (criterio.equals("recentes")) {
            // Ultimas 5 musicas adicionadas
            int inicio = Math.max(0, todasMusicas.size() - 5);
            for (int i = inicio; i < todasMusicas.size(); i++) {
                musicas.add(todasMusicas.get(i));
            }
        }

        System.out.println("Playlist \"" + nome + "\" atualizada com " + musicas.size() + " musica(s).");
    }

    // =========================================================
    // Getters e Setters
    // =========================================================

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            throw new IllegalArgumentException("Criterio nao pode ser vazio.");
        }
        String[] criteriosValidos = {"top", "recomendadas", "recentes"};
        for (String c : criteriosValidos) {
            if (c.equalsIgnoreCase(criterio.trim())) {
                this.criterio = c.toLowerCase();
                return;
            }
        }
        throw new IllegalArgumentException(
            "Criterio invalido: \"" + criterio + "\". Use: top, recomendadas ou recentes."
        );
    }

    @Override
    public String toString() {
        return String.format("PlaylistAutomatica{nome='%s', criterio='%s', musicas=%d}",
            nome, criterio, musicas.size());
    }
}