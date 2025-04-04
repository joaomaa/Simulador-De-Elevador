public class CentralControle {
    int elevadoresDisponiveis;

    public void controlador() {}

    public class Elevador {
        private class Botao {
            Botao subir, descer;
            Botao andar, emergencia;
        }

        public class PainelExterno {
            public void chamarElevador() {}
        }

        public class PainelInterno {
            public void selecionarAndar() {}
        }
    }

    public class FilaElevador {
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

        public FilaElevador() {
            No head = null;
            No tail = null;
        }

        public void enqueue(int valor) {}

        public int dequeue() {
            return 0;
        }
    }
}
