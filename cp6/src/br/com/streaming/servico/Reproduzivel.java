package br.com.streaming.servico;

public interface Reproduzivel {
    void reproduzir();
    void pausar();
    void parar();
    int getDuracaoTotal();
}
