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
        No<Andar> temp = andares.getInicio();
        while (temp != null) {
            Andar a = temp.getElemento();
            if (a.getNumero() == p.getAndarOrigem()) {
                a.adicionarPessoaNaFila(p);
                return;
            }
            temp = temp.getProximo();
        }
        throw new IllegalArgumentException("Andar inválido: " + p.getAndarOrigem());
    }

    public void exibirEstado() {
        System.out.println("============= PREDIO ==============");
        No<Andar> andarTemp = andares.getInicio();
        while (andarTemp != null) {
            andarTemp.getElemento().exibirEstado();
            andarTemp = andarTemp.getProximo();
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