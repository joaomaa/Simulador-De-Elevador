public class Andar {
    int totalAndar;
    Andar andarAtual;
    Andar andarDestino;

    public class FilaUsuarios {
        private class No {
            int valor;
            No proximo;

            No(int valor) {
                this.valor = valor;
                this.proximo = null;
            }
        }

        private class NoPrior {
            int prioridade;
            NoPrior ant, prox;
            No head, tail;

            NoPrior(int prioridade) {
                this.prioridade = prioridade;
                this.prox = null;
                this.head = null;
            }
        }

        public FilaUsuarios() {
            No head = null;
            No tail = null;
        }

        public void enqueue(int valor) {}

        public int dequeue() {
            return 0;
        }
    }
}
