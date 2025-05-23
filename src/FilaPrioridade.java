import java.io.Serializable;

public class FilaPrioridade<T> implements Serializable {
    private static class NoPri<T> {
        T valor;
        int prioridade;
        NoPri<T> proximo;

        NoPri(T valor, int prioridade) {
            this.valor = valor;
            this.prioridade = prioridade;
            this.proximo = null;
        }
    }

    private NoPri<T> inicio;
    private int tamanho;

    public FilaPrioridade() {
        inicio = null;
        tamanho = 0;
    }

    public void enqueue(T valor, int prioridade) {
        NoPri<T> novo = new NoPri<>(valor, prioridade);
        if (inicio == null || prioridade > inicio.prioridade) {
            novo.proximo = inicio;
            inicio = novo;
        } else {
            NoPri<T> atual = inicio;
            while (atual.proximo != null && atual.proximo.prioridade >= prioridade) {
                atual = atual.proximo;
            }
            novo.proximo = atual.proximo;
            atual.proximo = novo;
        }
        tamanho++;
    }

    public T dequeue() {
        if (inicio == null) throw new IllegalStateException("Fila vazia");
        T valor = inicio.valor;
        inicio = inicio.proximo;
        tamanho--;
        return valor;
    }

    public T primeiro() {
        if (inicio == null) throw new IllegalStateException("Fila vazia");
        return inicio.valor;
    }

    public boolean estaVazia() { return tamanho == 0; }

    public int getTamanho() { return tamanho; }
}