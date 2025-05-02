class No {
    int valor;
    No proximo;

    public No(int valor) {
        this.valor = valor;
        this.proximo = null;
    }

    public int getElemento() { return this.valor; }
    public void setElemento(int valor) { this.valor = valor; }
    public No getProximo() { return this.proximo; }
    public void setProximo(No proximo) { this.proximo = proximo; }
}

public class Lista {
    private No inicio;

    public Lista() { this.inicio = null; }

    public boolean inserirFim(int elemento, int p) {
        No novoNo = new No(elemento);
        if (p < 0) {
            return false;
        }

        if (p == 0) {
            novoNo.proximo = inicio;
            inicio = novoNo;
            return true;
        }

        No atual = inicio;
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

    public No getInicio() { return this.inicio; }
    public void setInicio(No inicio) { this.inicio = inicio; }
}
