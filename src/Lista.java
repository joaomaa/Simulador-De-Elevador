import java.io.Serializable;

class No<T> implements Serializable{
    T elemento;
    No<T> proximo;

    public No(T elemento) {
        this.elemento = elemento;
        this.proximo = null;
    }

    public T getElemento() { return this.elemento; }
    public void setElemento(T elemento) { this.elemento = elemento; }
    public No<T> getProximo() { return this.proximo; }
    public void setProximo(No<T> proximo) { this.proximo = proximo; }
}

public class Lista<T> implements Serializable{
    private No<T> inicio;

    public Lista() { this.inicio = null; }

    public boolean inserirFim(T elemento, int p) {
        No<T> novoNo = new No<>(elemento);
        if (p < 0) {
            return false;
        }

        if (p == 0) {
            novoNo.proximo = inicio;
            inicio = novoNo;
            return true;
        }

        No<T> atual = inicio;
        int i = 0;

        while (atual != null && i < p - 1) {
            atual = atual.proximo;
            i++;
        }

        if (atual == null) {
            return false;
        }

        novoNo.proximo = atual.proximo;
        atual.proximo = novoNo;
        return true;
    }

    public No<T> getInicio() { return this.inicio; }
    public void setInicio(No<T> inicio) { this.inicio = inicio; }
}