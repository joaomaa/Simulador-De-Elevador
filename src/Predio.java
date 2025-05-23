public class Predio extends EntidadeSimulavel {
    private CentralDeControle central;
    private Lista<Andar> andares;

    public Predio(int quantidadeAndares, int quantidadeElevadores, int capacidadeElevador) {
        andares = new Lista<>();
        for (int i = 0; i < quantidadeAndares; i++) {
            andares.inserirFim(new Andar(i), i);
        }
        central = new CentralDeControle(quantidadeElevadores, capacidadeElevador, andares);
    }

    public void adicionarPessoa(Pessoa p) {
        No<Andar> cursor = andares.getInicio();
        while (cursor != null) {
            Andar a = cursor.getElemento();
            if (a.getNumero() == p.getAndarOrigem()) {
                a.adicionarPessoaNaFila(p);
                return;
            }
            cursor = cursor.getProximo();
        }
        throw new IllegalArgumentException("Andar inválido: " + p.getAndarOrigem());
    }

    public void exibirEstado() {
        System.out.println("============= PREDIO ==============");
        No<Andar> cursorAndar = andares.getInicio();
        while (cursorAndar != null) {
            cursorAndar.getElemento().exibirEstado();
            cursorAndar = cursorAndar.getProximo();
        }
        central.exibirEstado();
        System.out.println("==================================\n");
    }

    @Override
    public void atualizar(int minutoSimulado) {
        central.atualizar(minutoSimulado);
    }

    public CentralDeControle getCentral() {
        return central;
    }

    public Lista<Andar> getAndares() {
        return andares;
    }
}