package br.com.streaming.modelo;

import br.com.streaming.servico.Reproduzivel;

/**
 * Classe abstrata que representa qualquer item reproduzível no sistema.
 * Implementa a interface Reproduzivel e define o contrato base
 * para Musica e Playlist (Abstração + Polimorfismo).
 */
public abstract class ItemReproducao implements Reproduzivel {

    protected String nome;
    protected boolean emReproducao;
    protected boolean pausado;

    public ItemReproducao(String nome) {
        setNome(nome);
        this.emReproducao = false;
        this.pausado = false;
    }

    // ==================== IMPLEMENTAÇÃO PARCIAL DE Reproduzivel ====================

    @Override
    public void reproduzir() {
        emReproducao = true;
        pausado = false;
        System.out.println("▶ Reproduzindo: " + nome);
    }

    @Override
    public void pausar() {
        if (!emReproducao) {
            System.out.println("⚠ Nada está sendo reproduzido.");
            return;
        }
        pausado = true;
        emReproducao = false;
        System.out.println("⏸ Pausado: " + nome);
    }

    @Override
    public void parar() {
        emReproducao = false;
        pausado = false;
        System.out.println("⏹ Parado: " + nome);
    }

    // getDuracaoTotal() permanece abstrato — cada subclasse calcula do seu jeito

    // ==================== GETTERS / SETTERS ====================

    public String getNome() { return nome; }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome inválido.");
        this.nome = nome.trim();
    }

    public boolean isEmReproducao() { return emReproducao; }
    public boolean isPausado()      { return pausado; }

    @Override
    public String toString() {
        return nome + " (" + getDuracaoTotal() + "s)";
    }
}
