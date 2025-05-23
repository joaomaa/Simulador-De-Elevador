import java.io.Serializable;

public class Fila<T> implements Serializable {
    private static class No<T> {
        T valor;
        No<T> proximo;
        No(T valor) { this.valor = valor; this.proximo = null; }
    }
    private No<T> head;
    private No<T> tail;
    private int tamanho;

    public Fila() {
        this.head = this.tail = null;
        this.tamanho = 0;
    }

        public boolean estaVazia() { return (head == null && tail == null) || tamanho == 0; }

    public void enqueue(T valor) {
        No<T> novo = new No<>(valor);
        if (tail != null) tail.proximo = novo;
        tail = novo;
        if (head == null) head = novo;
        tamanho++;
    }

    public T dequeue() {
        if (head == null) throw new IllegalStateException("Fila vazia");
        T valor = head.valor;
        head = head.proximo;
        if (head == null) tail = null;
        tamanho--;
        return valor;
    }

    public T primeiro() {
        if (head == null) throw new IllegalStateException("Fila vazia");
        return head.valor;
    }

    public int getTamanho() { return tamanho; }
}
