public class CentralDeControle extends EntidadeSimulavel {
    private Lista<Elevador> elevadores;
    Lista<Andar> andares;

    public CentralDeControle(int quantidadeElevadores, int capacidadeElevador, Lista<Andar> andares) {
        this.elevadores = new Lista<>();
        this.andares = andares;
        for (int i = 0; i < quantidadeElevadores; i++) {
            elevadores.inserirFim(new Elevador(i+1, capacidadeElevador, andares),i);
        }
    }

    public void exibirEstado() {
        No<Elevador> temp = elevadores.getInicio();
        while (temp != null) {
            temp.getElemento().exibirEstado();
            temp = temp.getProximo();
        }
    }

    @Override
    public void atualizar(int minutoSimulado) {
        System.out.println("----- Elevadores -----");
        No<Elevador> p = elevadores.getInicio();
        while (p != null) {
            p.getElemento().atualizar(minutoSimulado);
            p = p.getProximo();
        }
        System.out.println("---------------------------------");
    }

    public Lista<Elevador> getElevadores() {
        return elevadores;
    }
}