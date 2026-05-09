import java.util.ArrayList;
import java.util.Scanner;

// Entrega CP5

/**
 * Classe principal - CP5
 * Polimorfismo em acao:
 * - ArrayList<Usuario> armazena Free e Premium
 * - instanceof e casting para acessar comportamentos especificos
 * - Sistema multi-usuario com login
 * - Playlists automaticas
 * - Estatisticas por tipo de usuario
 */
public class StreamingMusica {

    // Catalogo global de musicas
    static ArrayList<Musica> catalogo = new ArrayList<>();

    // Lista polimorfca de usuarios — armazena Free e Premium
    static ArrayList<Usuario> usuarios = new ArrayList<>();

    // Usuario atualmente logado
    static Usuario usuarioLogado = null;

    static final String[] GENEROS_VALIDOS = {"Pop", "Rock", "Jazz", "Eletronica", "Hip-Hop", "Classica"};
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        adicionarMusicasTeste();

        int opcao;
        do {
            exibirMenuPrincipal();
            opcao = lerOpcao();
            processarMenuPrincipal(opcao);
        } while (opcao != 0);

        System.out.println("\nObrigado por usar o Sistema de Streaming! Ate logo!");
        scanner.close();
    }

    // =========================================================
    // Menu principal (antes do login)
    // =========================================================

    public static void exibirMenuPrincipal() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         SISTEMA DE STREAMING DE MUSICA");
        System.out.println("=".repeat(50));
        if (usuarioLogado != null) {
            // Usa instanceof para identificar o tipo e exibir corretamente
            if (usuarioLogado instanceof UsuarioPremium) {
                UsuarioPremium p = (UsuarioPremium) usuarioLogado;
                System.out.println("Logado como: " + usuarioLogado.getNome()
                    + " [Premium - " + p.getPlano() + "]");
            } else if (usuarioLogado instanceof UsuarioFree) {
                System.out.println("Logado como: " + usuarioLogado.getNome() + " [Free]");
            }
        } else {
            System.out.println("Nenhum usuario logado.");
        }
        System.out.println("=".repeat(50));
        System.out.println("1. Criar novo usuario");
        System.out.println("2. Login");
        System.out.println("3. Listar usuarios");
        System.out.println("4. Estatisticas do sistema");
        if (usuarioLogado != null) {
            System.out.println("5. Acessar minha conta");
        }
        System.out.println("0. Sair");
        System.out.println("=".repeat(50));
        System.out.print("Escolha: ");
    }

    public static void processarMenuPrincipal(int opcao) {
        switch (opcao) {
            case 1: criarUsuario();          break;
            case 2: fazerLogin();            break;
            case 3: listarUsuarios();        break;
            case 4: exibirEstatisticas();    break;
            case 5:
                if (usuarioLogado != null) menuConta();
                else System.out.println("Faca login primeiro!");
                break;
            case 0: break;
            default: System.out.println("Opcao invalida!");
        }
    }

    // =========================================================
    // Sistema multi-usuario
    // =========================================================

    /**
     * Cria um novo usuario (Free ou Premium) e adiciona ao ArrayList polimorfco.
     */
    public static void criarUsuario() {
        System.out.println("\n--- CRIAR USUARIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.println("\nTipo de conta:");
        System.out.println("1. Free");
        System.out.println("2. Premium");
        System.out.print("Escolha: ");
        int tipo = lerOpcao();

        try {
            if (tipo == 2) {
                System.out.println("\nEscolha o plano:");
                System.out.println("1. Mensal  (R$ 19,90/mes)");
                System.out.println("2. Anual   (R$ 199,00/ano)");
                System.out.println("3. Familiar (R$ 29,90/mes)");
                System.out.print("Escolha: ");
                int opcaoPlano = lerOpcao();

                String plano;
                switch (opcaoPlano) {
                    case 2:  plano = "Anual";    break;
                    case 3:  plano = "Familiar"; break;
                    default: plano = "Mensal";   break;
                }

                // Upcasting: UsuarioPremium armazenado como Usuario na lista polimorfca
                usuarios.add(new UsuarioPremium(nome, email, plano));
                System.out.println("Usuario Premium (" + plano + ") criado com sucesso!");

            } else {
                // Upcasting: UsuarioFree armazenado como Usuario na lista polimorfca
                usuarios.add(new UsuarioFree(nome, email));
                System.out.println("Usuario Free criado com sucesso!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao criar usuario: " + e.getMessage());
        }
    }

    /**
     * Exibe lista de usuarios e permite selecionar um para login.
     * Demonstra polimorfismo: lista de Usuario exibe Free e Premium.
     */
    public static void fazerLogin() {
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado. Crie um primeiro!");
            return;
        }

        listarUsuarios();
        System.out.print("\nEscolha o numero do usuario para login: ");
        int escolha = lerOpcao();

        if (escolha < 1 || escolha > usuarios.size()) {
            System.out.println("Opcao invalida!");
            return;
        }

        // Polimorfismo: a variavel usuarioLogado recebe Free ou Premium
        usuarioLogado = usuarios.get(escolha - 1);
        System.out.println("Login realizado: " + usuarioLogado.getNome());
    }

    /**
     * Lista todos os usuarios do sistema.
     * Usa instanceof para exibir o tipo especifico de cada um.
     */
    public static void listarUsuarios() {
        System.out.println("\n--- USUARIOS CADASTRADOS ---");
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado.");
            return;
        }
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario u = usuarios.get(i);

            // instanceof + casting para acessar atributos especificos
            if (u instanceof UsuarioPremium) {
                UsuarioPremium premium = (UsuarioPremium) u;
                System.out.printf("  %d. %s (Premium - %s) | %d reproducoes%n",
                    i + 1, u.getNome(), premium.getPlano(), u.getTotalReproducoes());

            } else if (u instanceof UsuarioFree) {
                UsuarioFree free = (UsuarioFree) u;
                System.out.printf("  %d. %s (Free) | %d reproducoes | %d/%d playlists%n",
                    i + 1, u.getNome(), u.getTotalReproducoes(),
                    u.getTotalPlaylists(), free.getLimitePlaylists());
            }
        }
    }

    // =========================================================
    // Menu da conta do usuario logado
    // =========================================================

    public static void menuConta() {
        int opcao;
        do {
            exibirMenuConta();
            opcao = lerOpcao();
            processarMenuConta(opcao);
        } while (opcao != 0);
    }

    public static void exibirMenuConta() {
        System.out.println("\n" + "=".repeat(50));

        // Polimorfismo: instanceof define qual menu exibir
        if (usuarioLogado instanceof UsuarioPremium) {
            exibirMenuPremium();
        } else {
            exibirMenuFree();
        }

        System.out.println("=".repeat(50));
        System.out.print("Escolha: ");
    }

    private static void exibirMenuFree() {
        UsuarioFree free = (UsuarioFree) usuarioLogado;
        System.out.println("MENU FREE - Ola, " + free.getNome() + "!");
        System.out.println("-- CATALOGO --");
        System.out.println("1.  Cadastrar musica");
        System.out.println("2.  Listar musicas");
        System.out.println("3.  Buscar por titulo");
        System.out.println("4.  Buscar por artista");
        System.out.println("5.  Buscar por genero");
        System.out.println("-- MINHA CONTA --");
        System.out.println("6.  Reproduzir musica");
        System.out.println("7.  Ver historico");
        System.out.println("8.  Criar playlist (max. " + free.getLimitePlaylists() + ")");
        System.out.println("9.  Ver playlists");
        System.out.println("10. Adicionar musica a playlist");
        System.out.println("11. Remover musica da playlist");
        System.out.println("12. Reproduzir playlist");
        System.out.println("13. Meus detalhes");
        System.out.println("14. Ver planos Premium");
        System.out.println("-- PLAYLISTS AUTOMATICAS --");
        System.out.println("15. Gerar playlist automatica");
        System.out.println("0.  Voltar");
    }

    private static void exibirMenuPremium() {
        UsuarioPremium premium = (UsuarioPremium) usuarioLogado;
        System.out.println("MENU PREMIUM (" + premium.getPlano() + ") - Ola, " + premium.getNome() + "!");
        System.out.println("-- CATALOGO --");
        System.out.println("1.  Cadastrar musica");
        System.out.println("2.  Listar musicas");
        System.out.println("3.  Buscar por titulo");
        System.out.println("4.  Buscar por artista");
        System.out.println("5.  Buscar por genero");
        System.out.println("-- MINHA CONTA --");
        System.out.println("6.  Reproduzir musica (Alta Qualidade)");
        System.out.println("7.  Ver historico");
        System.out.println("8.  Criar playlist (ilimitado)");
        System.out.println("9.  Ver playlists");
        System.out.println("10. Adicionar musica a playlist");
        System.out.println("11. Remover musica da playlist");
        System.out.println("12. Reproduzir playlist");
        System.out.println("13. Meus detalhes");
        System.out.println("14. Baixar musica");
        System.out.println("15. Ver musicas baixadas");
        System.out.println("-- PLAYLISTS AUTOMATICAS --");
        System.out.println("16. Gerar playlist automatica");
        System.out.println("0.  Voltar");
    }

    public static void processarMenuConta(int opcao) {
        boolean isPremium = usuarioLogado instanceof UsuarioPremium;

        switch (opcao) {
            case 1:  cadastrarMusica();                        break;
            case 2:  listarMusicas();                          break;
            case 3:  buscarPorTitulo();                        break;
            case 4:  buscarPorArtista();                       break;
            case 5:  buscarPorGenero();                        break;
            case 6:  reproduzirMusica();                       break;
            case 7:  usuarioLogado.exibirHistorico();          break;
            case 8:  criarPlaylist();                          break;
            case 9:  usuarioLogado.listarPlaylists();          break;
            case 10: adicionarMusicaPlaylist();                break;
            case 11: removerMusicaPlaylist();                  break;
            case 12: reproduzirPlaylist();                     break;
            case 13: usuarioLogado.exibirDetalhes();           break;
            case 14:
                if (isPremium) baixarMusica();
                else ((UsuarioFree) usuarioLogado).exibirUpgrade();
                break;
            case 15:
                if (isPremium) ((UsuarioPremium) usuarioLogado).listarMusicasBaixadas();
                else gerarPlaylistAutomatica();
                break;
            case 16:
                if (isPremium) gerarPlaylistAutomatica();
                else System.out.println("Opcao invalida!");
                break;
            case 0: break;
            default: System.out.println("Opcao invalida!");
        }
    }

    // =========================================================
    // Acoes da conta
    // =========================================================

    /**
     * Reproduz uma musica usando o metodo polimorfico do usuario logado.
     * A versao correta (Free ou Premium) e executada automaticamente.
     */
    public static void reproduzirMusica() {
        listarMusicas();
        if (catalogo.isEmpty()) return;
        System.out.print("Escolha o numero da musica: ");
        int num = lerOpcao();
        if (num < 1 || num > catalogo.size()) {
            System.out.println("Indice invalido!"); return;
        }
        // Polimorfismo em acao: chama a versao correta de reproduzirMusica()
        usuarioLogado.reproduzirMusica(catalogo.get(num - 1));
    }

    public static void criarPlaylist() {
        System.out.print("\nNome da nova playlist: ");
        String nome = scanner.nextLine();
        try {
            // Polimorfismo: Free respeita limite, Premium nao
            usuarioLogado.criarPlaylist(nome);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void reproduzirPlaylist() {
        usuarioLogado.listarPlaylists();
        if (usuarioLogado.getTotalPlaylists() == 0) return;
        System.out.print("Numero da playlist: ");
        Playlist p = usuarioLogado.getPlaylist(lerOpcao());
        if (p == null) return;
        // Polimorfismo: chama reproduzir() — versao correta (Playlist ou PlaylistAutomatica)
        p.reproduzir();
    }

    public static void baixarMusica() {
        listarMusicas();
        if (catalogo.isEmpty()) return;
        System.out.print("Numero da musica para baixar: ");
        int num = lerOpcao();
        if (num < 1 || num > catalogo.size()) {
            System.out.println("Indice invalido!"); return;
        }
        // Downcasting seguro — ja verificamos com instanceof no chamador
        ((UsuarioPremium) usuarioLogado).baixarMusica(catalogo.get(num - 1));
    }

    /**
     * Gera uma PlaylistAutomatica e a adiciona as playlists do usuario logado.
     */
    public static void gerarPlaylistAutomatica() {
        System.out.println("\n--- PLAYLISTS AUTOMATICAS ---");
        System.out.println("1. Top 10 Mais Tocadas");
        System.out.println("2. Recomendadas para Voce");
        System.out.println("3. Adicionadas Recentemente");
        System.out.print("Escolha: ");
        int opcao = lerOpcao();

        String criterio;
        String nomePlaylist;

        switch (opcao) {
            case 2: criterio = "recomendadas"; nomePlaylist = "Recomendadas para Voce"; break;
            case 3: criterio = "recentes";     nomePlaylist = "Adicionadas Recentemente"; break;
            default: criterio = "top";         nomePlaylist = "Top 10 Mais Tocadas"; break;
        }

        // Cria uma PlaylistAutomatica e atualiza com o catalogo
        PlaylistAutomatica pa = new PlaylistAutomatica(nomePlaylist, criterio);
        pa.atualizar(catalogo);

        // Adiciona diretamente na lista interna do usuario
        usuarioLogado.getPlaylists().add(pa);
        System.out.println("Playlist automatica \"" + nomePlaylist + "\" adicionada com sucesso!");
    }

    // =========================================================
    // Estatisticas do sistema (polimorfismo com instanceof)
    // =========================================================

    /**
     * Percorre o ArrayList polimorfco de usuarios e calcula estatisticas
     * usando instanceof para diferenciar os tipos.
     */
    public static void exibirEstatisticas() {
        System.out.println("\n--- ESTATISTICAS DO SISTEMA ---");

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuario cadastrado ainda.");
            return;
        }

        int totalUsuarios   = usuarios.size();
        int totalFree       = 0;
        int totalPremium    = 0;
        int reproducoesFree = 0;
        int reproducoesPremium = 0;
        int totalAnuncios   = 0;
        int totalBaixadas   = 0;

        // Itera o ArrayList polimorfco e usa instanceof para extrair dados especificos
        for (Usuario u : usuarios) {
            if (u instanceof UsuarioPremium) {
                totalPremium++;
                UsuarioPremium premium = (UsuarioPremium) u;
                reproducoesPremium += u.getTotalReproducoes();
                totalBaixadas += premium.getTotalBaixadas();

            } else if (u instanceof UsuarioFree) {
                totalFree++;
                UsuarioFree free = (UsuarioFree) u;
                reproducoesFree += u.getTotalReproducoes();
                totalAnuncios += free.getTotalAnuncios();
            }
        }

        int totalReproducoes = reproducoesFree + reproducoesPremium;

        System.out.println("Total de usuarios: " + totalUsuarios);
        System.out.println("  Free:    " + totalFree + " usuario(s)");
        System.out.println("  Premium: " + totalPremium + " usuario(s)");
        System.out.println("\nReproducoes totais: " + totalReproducoes);

        if (totalReproducoes > 0) {
            int pctFree    = (reproducoesFree * 100) / totalReproducoes;
            int pctPremium = (reproducoesPremium * 100) / totalReproducoes;
            System.out.println("  Free:    " + reproducoesFree + " reproducoes (" + pctFree + "%)");
            System.out.println("  Premium: " + reproducoesPremium + " reproducoes (" + pctPremium + "%)");
        } else {
            System.out.println("  Free:    " + reproducoesFree + " reproducoes");
            System.out.println("  Premium: " + reproducoesPremium + " reproducoes");
        }

        System.out.println("\nAnuncios exibidos (Free): " + totalAnuncios);
        System.out.println("Musicas baixadas (Premium): " + totalBaixadas);
        System.out.println("Musicas no catalogo: " + catalogo.size());
    }

    // =========================================================
    // Operacoes do catalogo
    // =========================================================

    public static void cadastrarMusica() {
        System.out.println("\n--- CADASTRAR MUSICA ---");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();
        System.out.print("Artista: ");
        String artista = scanner.nextLine();
        System.out.print("Duracao (segundos, 1-3599): ");
        int duracao = lerOpcao();
        System.out.println("Generos: Pop, Rock, Jazz, Eletronica, Hip-Hop, Classica");
        System.out.print("Genero: ");
        String genero = scanner.nextLine();
        try {
            catalogo.add(new Musica(titulo, artista, duracao, genero));
            System.out.println("Musica cadastrada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    public static void listarMusicas() {
        System.out.println("\n--- MUSICAS CADASTRADAS ---");
        if (catalogo.isEmpty()) { System.out.println("Nenhuma musica cadastrada."); return; }
        for (int i = 0; i < catalogo.size(); i++) {
            catalogo.get(i).exibir(i + 1);
        }
    }

    public static void buscarPorTitulo() {
        System.out.print("\nDigite o titulo: ");
        String busca = scanner.nextLine().trim();
        boolean encontrou = false;
        for (Musica m : catalogo) {
            if (m.contemTitulo(busca)) {
                System.out.println("Encontrado: " + m.getTitulo() + " - " + m.getArtista());
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nada encontrado.");
    }

    public static void buscarPorArtista() {
        System.out.print("\nDigite o artista: ");
        String busca = scanner.nextLine().trim();
        boolean encontrou = false;
        for (Musica m : catalogo) {
            if (m.contemArtista(busca)) {
                System.out.println("Encontrado: " + m.getTitulo() + " [" + m.getArtista() + "]");
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Artista nao encontrado.");
    }

    public static void buscarPorGenero() {
        System.out.print("\nDigite o genero: ");
        String busca = scanner.nextLine().trim();
        boolean encontrou = false;
        for (Musica m : catalogo) {
            if (m.getGenero().equalsIgnoreCase(busca)) {
                System.out.println("Encontrado: " + m.getTitulo() + " (" + m.getGenero() + ")");
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhuma musica nesse genero.");
    }

    public static void adicionarMusicaPlaylist() {
        usuarioLogado.listarPlaylists();
        if (usuarioLogado.getTotalPlaylists() == 0) return;
        System.out.print("Numero da playlist: ");
        Playlist playlist = usuarioLogado.getPlaylist(lerOpcao());
        if (playlist == null) return;
        listarMusicas();
        if (catalogo.isEmpty()) return;
        System.out.print("Numero da musica: ");
        int num = lerOpcao();
        if (num < 1 || num > catalogo.size()) { System.out.println("Invalido!"); return; }
        try {
            playlist.adicionarMusica(catalogo.get(num - 1));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static void removerMusicaPlaylist() {
        usuarioLogado.listarPlaylists();
        if (usuarioLogado.getTotalPlaylists() == 0) return;
        System.out.print("Numero da playlist: ");
        Playlist playlist = usuarioLogado.getPlaylist(lerOpcao());
        if (playlist == null) return;
        playlist.listarMusicas();
        if (playlist.getTotalMusicas() == 0) return;
        System.out.print("Numero da musica para remover: ");
        playlist.removerMusica(lerOpcao());
    }

    // =========================================================
    // Utilitarios
    // =========================================================

    public static int lerOpcao() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void adicionarMusicasTeste() {
        try {
            catalogo.add(new Musica("Bohemian Rhapsody", "Queen", 354, "Rock"));
            catalogo.add(new Musica("Billie Jean", "Michael Jackson", 293, "Pop"));
            catalogo.add(new Musica("Smells Like Teen Spirit", "Nirvana", 301, "Rock"));
            catalogo.add(new Musica("So What", "Miles Davis", 545, "Jazz"));
            catalogo.add(new Musica("Lose Yourself", "Eminem", 326, "Hip-Hop"));
            catalogo.add(new Musica("Around the World", "Daft Punk", 428, "Eletrônica"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro nos dados de teste: " + e.getMessage());
        }
    }
}