# 🎵 Sistema de Streaming de Música

Sistema de streaming de música em console desenvolvido em Java, demonstrando domínio completo de Programação Orientada a Objetos.

---

## 📋 Funcionalidades

### 🎵 Músicas
- Cadastrar músicas (título, artista, duração, gênero)
- Listar e apagar músicas do catálogo
- Buscar música por título
- Listar músicas por gênero (via playlists automáticas)

### 👤 Usuários
- Cadastrar usuários do tipo **Free** ou **Premium**
- Exibir informações detalhadas do usuário
- Listar músicas associadas ao usuário
- Listar todos os usuários cadastrados

### ▶️ Reprodução
- Reproduzir música individual (comportamento difere por tipo de usuário)
- Reproduzir playlist completa por gênero
- Visualizar histórico de reprodução

### ⬇️ Downloads (exclusivo Premium)
- Baixar músicas para uso offline
- Remover músicas baixadas
- Listar músicas baixadas

### 💡 Extras
- **Recomendações**: sugere músicas com base no gênero mais ouvido
- **Estatísticas**: total de músicas, playlists, usuários, duração total

---

## 🏗️ Arquitetura

### Estrutura de Pacotes

```
src/
└── br/com/streaming/
    ├── modelo/          → Entidades do domínio
    │   ├── ItemReproducao.java   (abstrata — implementa Reproduzivel)
    │   ├── Musica.java           (estende ItemReproducao)
    │   ├── Playlist.java         (estende ItemReproducao)
    │   ├── Usuario.java          (abstrata)
    │   ├── UsuarioFree.java      (estende Usuario)
    │   └── UsuarioPremium.java   (estende Usuario, implementa Baixavel)
    ├── servico/         → Interfaces e serviços
    │   ├── Reproduzivel.java     (interface)
    │   ├── Baixavel.java         (interface)
    │   └── GeradorRecomendacoes.java
    ├── util/            → Utilitários
    │   ├── Validador.java
    │   └── FormatadorTempo.java
    └── principal/       → Ponto de entrada
        └── StreamingMusica.java
```

### Conceitos de POO Aplicados

| Conceito | Onde é aplicado |
|---|---|
| **Abstração** | `ItemReproducao` e `Usuario` definem contratos sem implementação completa |
| **Encapsulamento** | Todos os atributos são `private`/`protected` com getters/setters validados |
| **Herança** | `Musica` e `Playlist` → `ItemReproducao`; `UsuarioFree` e `UsuarioPremium` → `Usuario` |
| **Polimorfismo** | Lista `ArrayList<Usuario>` trata Free e Premium de forma genérica; `reproduzirMusica()` tem comportamento diferente em cada subclasse |
| **Interfaces** | `Reproduzivel` implementada por `Musica` e `Playlist`; `Baixavel` implementada exclusivamente por `UsuarioPremium` |

### Interface vs Classe Abstrata — decisões de design

- **`Reproduzivel`** é interface: `Musica` e `Playlist` não têm relação de herança entre si, mas ambas precisam do mesmo contrato de reprodução.
- **`Baixavel`** é interface: apenas `UsuarioPremium` a implementa, modelando a ideia de "capacidade opcional" sem forçar herança.
- **`ItemReproducao`** é classe abstrata: `Musica` e `Playlist` compartilham estado (`nome`, `emReproducao`, `pausado`) e implementações concretas de `reproduzir()`, `pausar()` e `parar()`.
- **`Usuario`** é classe abstrata: `UsuarioFree` e `UsuarioPremium` compartilham `historicoReproducao`, `playlists`, e métodos como `reproduzirPlaylist()` e `exibirHistorico()`.

---

## 🚀 Como Executar

### Pré-requisitos
- Java 11 ou superior instalado
- Terminal / Prompt de Comando

### Compilação

```bash
# Na raiz do projeto
mkdir -p out
find src -name "*.java" > sources.txt
javac -encoding UTF-8 -d out @sources.txt
```

### Execução

```bash
java -cp out br.com.streaming.principal.StreamingMusica
```

### Fluxo básico de uso

1. Cadastre um usuário (opção 5)
2. Cadastre músicas vinculadas a ele (opção 1)
3. Reproduza músicas ou playlists (opções 9 / 10)
4. Consulte histórico e recomendações (opções 11 / 15)
5. Usuários Premium podem baixar músicas (opção 12)

---

## 👤 Autor

- **Nome:** Alexsandro Vasconcelos  
- **RA:** [seu RA aqui]

---

## 📅 Histórico de Checkpoints

| Checkpoint | Conteúdo |
|---|---|
| CP1 | Introdução a Java, variáveis e estruturas básicas |
| CP2 | Classes, objetos, construtores e encapsulamento |
| CP3 | Herança, polimorfismo e classes abstratas |
| CP4 | Coleções, ArrayList e arrays |
| CP5 | Polimorfismo avançado e organização em pacotes iniciais |
| **CP6** | **Interfaces, pacotes profissionais e sistema completo** |
