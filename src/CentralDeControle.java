public class CentralDeControle extends EntidadeSimulavel {
    private Lista<Elevador> elevadores;
    Lista<Andar> andares;

    public CentralDeControle(int quantidadeElevadores, int capacidadeElevador, Lista<Andar> andares) {
        this.elevadores = new Lista<>();
        this.andares = andares;
        for (int i = 0; i < quantidadeElevadores; i++) {
            elevadores.inserirFim(
                new Elevador(i+1, capacidadeElevador, andares),
                i
            );
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

    public void exibirEstado() {
        No<Elevador> cursor = elevadores.getInicio();
        while (cursor != null) {
            cursor.getElemento().exibirEstado();
            cursor = cursor.getProximo();
        }
    }

    public Lista<Elevador> getElevadores() {
        return elevadores;
    }
}