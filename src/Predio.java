public class Predio extends EntidadeSimulavel {
    private CentralDeControle central;
    private Lista<Andar> andares;

    public Predio(int quantidadeAndares, int quantidadeElevadores) {
        central = new CentralDeControle(quantidadeElevadores);
        andares = new Lista<>();
        for (int i = 0; i < quantidadeAndares; i++) {
            andares.inserirFim(new Andar(i), i); // Ajustado para dois parâmetros
        }
    }

    @Override
    public void atualizar(int minutoSimulado) {
        central.atualizar(minutoSimulado); // Mantém a delegação para a central
    }

    public CentralDeControle getCentral() {
        return central;
    }

    // Método adicional para acessar a lista de andares
    public Lista<Andar> getAndares() {
        return andares;
    }
}