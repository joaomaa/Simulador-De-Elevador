import java.io.Serializable;

public class Fila implements Serializable{
    private static class No {
        int valor;
        No proximo;

        No(int valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private No head;
    private No tail;
    private int tamanho;

    public Fila() {
        this.head = null;
        this.tail = null;
        this.tamanho = 0;
    }

}
