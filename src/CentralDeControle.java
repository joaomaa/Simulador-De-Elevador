public class CentralDeControle extends EntidadeSimulavel {
    private Lista<Elevador> elevadores;

    public CentralDeControle(int quantidadeElevadores) {
        elevadores = new Lista<>();
        for (int i = 0; i < quantidadeElevadores; i++) {
            elevadores.inserirFim(new Elevador(i + 1), 1);
        }
    }
        

    @Override
    public void atualizar(int minutoSimulado) {
        No<Elevador> p = elevadores.getInicio();
        while (p != null) {
            Elevador e = p.getElemento();
            e.atualizar(minutoSimulado);
            p = p.getProximo();
        }
    }

    public Lista<Elevador> getElevadores() {
        return elevadores;
    }
}