package br.com.streaming.principal;

import br.com.streaming.modelo.*;
import br.com.streaming.servico.GeradorRecomendacoes;
import br.com.streaming.util.FormatadorTempo;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Ponto de entrada do Sistema de Streaming de Música v6.0.
 *
 * Demonstra todos os pilares de POO:
 *   - Abstração   : ItemReproducao, Usuario
 *   - Encapsulamento: getters/setters + Validador
 *   - Herança     : Musica/Playlist → ItemReproducao | Free/Premium → Usuario
 *   - Polimorfismo: listas de Usuario, chamadas a reproduzirMusica()
 *   - Interfaces  : Reproduzivel (Musica, Playlist), Baixavel (UsuarioPremium)
 */
public class StreamingMusica {

    private static final ArrayList<Usuario>  usuarios  = new ArrayList<>();
    private static final ArrayList<Playlist> playlists = new ArrayList<>();
    private static final ArrayList<Musica>   catalogo  = new ArrayList<>();

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerOpcao();
            processarOpcao(opcao);
        } while (opcao != 0);

        System.out.println("\n🎵 Encerrando o sistema. Até mais!");
        scanner.close();
    }

    // ==================== MENU ====================

    private static void exibirMenu() {
        System.out.println("\n╔════════════════════════════════╗");
        System.out.println("║  🎵 STREAMING MUSIC v6.0       ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  --- MÚSICAS ---               ║");
        System.out.println("║  1. Cadastrar música           ║");
        System.out.println("║  2. Listar / Apagar músicas    ║");
        System.out.println("║  3. Buscar por título          ║");
        System.out.println("║  4. Listar por gênero          ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  --- USUÁRIOS ---              ║");
        System.out.println("║  5. Cadastrar usuário          ║");
        System.out.println("║  6. Músicas do usuário         ║");
        System.out.println("║  7. Info do usuário            ║");
        System.out.println("║  8. Listar todos os usuários   ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  --- REPRODUÇÃO ---            ║");
        System.out.println("║  9. Reproduzir música          ║");
        System.out.println("║ 10. Reproduzir playlist        ║");
        System.out.println("║ 11. Ver histórico              ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  --- PREMIUM ---               ║");
        System.out.println("║ 12. Baixar música              ║");
        System.out.println("║ 13. Remover download           ║");
        System.out.println("║ 14. Listar downloads           ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  --- EXTRAS ---                ║");
        System.out.println("║ 15. Recomendações              ║");
        System.out.println("║ 16. Estatísticas               ║");
        System.out.println("╠════════════════════════════════╣");
        System.out.println("║  0. Sair                       ║");
        System.out.println("╚════════════════════════════════╝");
        System.out.print("Escolha: ");
    }

    private static int lerOpcao() {
        try { return Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { return -1; }
    }

    private static void processarOpcao(int opcao) {
        switch (opcao) {
            case 1  -> cadastrarMusica();
            case 2  -> menuListarApagar();
            case 3  -> buscarPorTitulo();
            case 4  -> listarPorGenero();
            case 5  -> cadastrarUsuario();
            case 6  -> menuMusicasUsuario();
            case 7  -> menuInfoUsuario();
            case 8  -> listarTodosUsuarios();
            case 9  -> menuReproduzirMusica();
            case 10 -> menuReproduzirPlaylist();
            case 11 -> menuVerHistorico();
            case 12 -> menuBaixarMusica();
            case 13 -> menuRemoverDownload();
            case 14 -> menuListarDownloads();
            case 15 -> menuRecomendacoes();
            case 16 -> exibirEstatisticas();
            case 0  -> {}
            default -> System.out.println("❌ Opção inválida.");
        }
    }

    // ==================== MÚSICAS ====================

    private static void cadastrarMusica() {
        System.out.println("\n--- CADASTRAR MÚSICA ---");

        System.out.print("Nome do usuário (dono da música): ");
        Usuario usuario = buscarUsuario(scanner.nextLine());
        if (usuario == null) { System.out.println("❌ Usuário não encontrado."); return; }

        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Artista: ");
        String artista = scanner.nextLine();
        System.out.print("Duração (segundos): ");
        int duracao;
        try { duracao = Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { System.out.println("❌ Duração inválida."); return; }
        System.out.println("Gênero (Pop, Rock, Jazz, Eletrônica, Hip-Hop, Clássica): ");
        String genero = scanner.nextLine();

        Musica musica;
        try { musica = new Musica(titulo, artista, duracao, genero); }
        catch (IllegalArgumentException e) { System.out.println("❌ " + e.getMessage()); return; }

        catalogo.add(musica);

        // Adiciona à playlist global por gênero
        Playlist play = buscarPlaylistPorGenero(genero);
        if (play == null) { play = new Playlist(genero); playlists.add(play); }
        play.adicionarMusica(musica);

        usuario.adicionarMusica(musica);
        System.out.println("✅ Música cadastrada com sucesso!");
    }

    private static void menuListarApagar() {
        listarMusicas();
        System.out.print("\nDeseja deletar alguma música? (1 = Sim | 0 = Não): ");
        try {
            int opc = Integer.parseInt(scanner.nextLine());
            if (opc == 1) {
                System.out.print("Índice: ");
                int idx = Integer.parseInt(scanner.nextLine());
                deletarMusica(idx);
            }
        } catch (NumberFormatException e) { System.out.println("❌ Entrada inválida."); }
    }

    private static void listarMusicas() {
        System.out.println("\n--- CATÁLOGO DE MÚSICAS ---");
        if (catalogo.isEmpty()) { System.out.println("Nenhuma música cadastrada."); return; }
        for (int i = 0; i < catalogo.size(); i++) {
            System.out.println("[" + i + "] " + catalogo.get(i));
        }
    }

    private static void deletarMusica(int indice) {
        if (indice < 0 || indice >= catalogo.size()) { System.out.println("❌ Índice inválido."); return; }
        System.out.println("✅ \"" + catalogo.get(indice).getTitulo() + "\" removida.");
        catalogo.remove(indice);
    }

    private static void buscarPorTitulo() {
        System.out.print("\nTítulo: ");
        String busca = scanner.nextLine();
        for (Musica m : catalogo) {
            if (m.getTitulo().equalsIgnoreCase(busca)) { System.out.println(m); return; }
        }
        System.out.println("❌ Música não encontrada.");
    }

    private static void listarPorGenero() {
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        Playlist p = buscarPlaylistPorGenero(genero);
        if (p != null) { System.out.println("\n" + p); p.listarMusicas(); }
        else            System.out.println("❌ Nenhuma música desse gênero.");
    }

    // ==================== USUÁRIOS ====================

    private static void cadastrarUsuario() {
        System.out.println("\n--- CADASTRAR USUÁRIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        if (buscarUsuario(nome) != null) { System.out.println("❌ Usuário já existe."); return; }
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.println("Tipo de conta:\n  1 - Free\n  2 - Premium");
        int tipo;
        try { tipo = Integer.parseInt(scanner.nextLine()); }
        catch (NumberFormatException e) { System.out.println("❌ Opção inválida."); return; }

        Usuario usuario;
        try {
            if (tipo == 1) {
                usuario = new UsuarioFree(nome, email);
            } else if (tipo == 2) {
                System.out.println("Plano (Mensal / Anual / Familiar): ");
                String plano = scanner.nextLine();
                usuario = new UsuarioPremium(nome, email, plano);
            } else { System.out.println("❌ Tipo inválido."); return; }
        } catch (IllegalArgumentException e) { System.out.println("❌ " + e.getMessage()); return; }

        usuarios.add(usuario);
        System.out.println("✅ Usuário cadastrado com sucesso!");
        usuario.exibirInfo(); // POLIMORFISMO
    }

    private static void menuMusicasUsuario() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        u.listarMusicas();
    }

    private static void menuInfoUsuario() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        u.exibirInfo(); // POLIMORFISMO
    }

    private static void listarTodosUsuarios() {
        System.out.println("\n--- TODOS OS USUÁRIOS ---");
        if (usuarios.isEmpty()) { System.out.println("Nenhum usuário cadastrado."); return; }
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i)); // POLIMORFISMO: toString()
        }
    }

    // ==================== REPRODUÇÃO ====================

    private static void menuReproduzirMusica() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        System.out.print("Título da música: ");
        Musica musica = buscarMusicaPorTitulo(scanner.nextLine());
        if (musica == null) { System.out.println("❌ Música não encontrada."); return; }
        u.reproduzirMusica(musica); // POLIMORFISMO
    }

    private static void menuReproduzirPlaylist() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        System.out.print("Gênero da playlist: ");
        Playlist p = buscarPlaylistPorGenero(scanner.nextLine());
        if (p == null) { System.out.println("❌ Playlist não encontrada."); return; }
        u.reproduzirPlaylist(p); // POLIMORFISMO
    }

    private static void menuVerHistorico() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        u.exibirHistorico();
    }

    // ==================== PREMIUM ====================

    private static void menuBaixarMusica() {
        UsuarioPremium premium = getPremium();
        if (premium == null) return;
        System.out.print("Título da música: ");
        Musica musica = buscarMusicaPorTitulo(scanner.nextLine());
        if (musica == null) { System.out.println("❌ Música não encontrada."); return; }
        premium.baixar(musica); // interface Baixavel
    }

    private static void menuRemoverDownload() {
        UsuarioPremium premium = getPremium();
        if (premium == null) return;
        System.out.print("Título da música: ");
        Musica musica = buscarMusicaPorTitulo(scanner.nextLine());
        if (musica == null) { System.out.println("❌ Música não encontrada."); return; }
        premium.removerDownload(musica); // interface Baixavel
    }

    private static void menuListarDownloads() {
        UsuarioPremium premium = getPremium();
        if (premium == null) return;
        premium.listarMusicasBaixadas();
    }

    private static UsuarioPremium getPremium() {
        System.out.print("Nome do usuário Premium: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return null; }
        if (!(u instanceof UsuarioPremium)) {
            System.out.println("❌ Somente usuários Premium podem usar esta função.");
            System.out.println("💡 Faça upgrade para Premium e aproveite downloads ilimitados!");
            return null;
        }
        return (UsuarioPremium) u;
    }

    // ==================== EXTRAS ====================

    private static void menuRecomendacoes() {
        System.out.print("Nome do usuário: ");
        Usuario u = buscarUsuario(scanner.nextLine());
        if (u == null) { System.out.println("❌ Usuário não encontrado."); return; }
        GeradorRecomendacoes.exibir(u, catalogo, 5);
    }

    private static void exibirEstatisticas() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       ESTATÍSTICAS           ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║ Músicas no catálogo: " + catalogo.size());
        System.out.println("║ Playlists:           " + playlists.size());
        System.out.println("║ Usuários:            " + usuarios.size());

        int free = 0, premium = 0;
        for (Usuario u : usuarios) {
            if (u instanceof UsuarioPremium) premium++;
            else free++;
        }
        System.out.println("║   → Free:    " + free);
        System.out.println("║   → Premium: " + premium);

        int duracaoTotal = 0;
        for (Musica m : catalogo) duracaoTotal += m.getDuracao();
        System.out.println("║ Duração total: " + FormatadorTempo.formatarExtenso(duracaoTotal));
        System.out.println("╚══════════════════════════════╝");
    }

    // ==================== HELPERS ====================

    private static Usuario buscarUsuario(String nome) {
        for (Usuario u : usuarios) {
            if (u.getNome().equalsIgnoreCase(nome)) return u;
        }
        return null;
    }

    private static Playlist buscarPlaylistPorGenero(String genero) {
        for (Playlist p : playlists) {
            if (p.getPlayNome().equalsIgnoreCase(genero)) return p;
        }
        return null;
    }

    private static Musica buscarMusicaPorTitulo(String titulo) {
        for (Musica m : catalogo) {
            if (m.getTitulo().equalsIgnoreCase(titulo)) return m;
        }
        return null;
    }
}
