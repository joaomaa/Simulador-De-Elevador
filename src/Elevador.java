public class Elevador extends EntidadeSimulavel {
    private int id;
    private int andarAtual;
    private char direcao;
    private Fila<Integer> destinos;
    private Fila<Pessoa> pessoas;
    private Lista<Andar> andares;
    private final int capacidadeMaxima;
    private double energiaConsumida = 0.0;
    private double energiaUltimoMinuto = 0.0;
    private static final double ENERGIA_POR_ANDAR = 0.75; // kWh por andar

    public Elevador(int id, int capacidadeMaxima, Lista<Andar> andares) {
        this.id = id;
        this.andarAtual = 0;
        this.direcao = 'P';
        this.destinos = new Fila<>();
        this.pessoas  = new Fila<>();
        this.andares = andares;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    public void desembarcarAtual() {
        Fila<Pessoa> temp = new Fila<>();
        while (!pessoas.estaVazia()) {
            Pessoa p = pessoas.dequeue();
            if (p.getAndarDestino() == andarAtual) {
                System.out.println(" -> Pessoa " + p.getId() + " desembarcou");
                p.sairElevador();
            } else {
                temp.enqueue(p);
            }
        }
        pessoas = temp;
    }

    public void embarcar() {
        No<Andar> noAndar = andares.getInicio();
        while (noAndar != null && noAndar.getElemento().getNumero() != andarAtual) {
            noAndar = noAndar.getProximo();
        }

        if (noAndar != null) {
            FilaPrioridade<Pessoa> filaEspera = noAndar.getElemento().getPessoasAguardando();
            while (!filaEspera.estaVazia() && pessoas.getTamanho() < capacidadeMaxima) {
                Pessoa p = filaEspera.dequeue();
                p.entrarElevador();
                pessoas.enqueue(p);
                destinos.enqueue(p.getAndarDestino());
                System.out.printf("   -> Pessoa %d (prio %d) embarcou%n", p.getId(), p.getPrioridade());
            }

            if (pessoas.getTamanho() >= capacidadeMaxima && !filaEspera.estaVazia()) {
                System.out.printf("   -> Lotado (%d/%d). %d aguardam. Voltarei ao andar %d.%n", pessoas.getTamanho(), capacidadeMaxima, filaEspera.getTamanho(), andarAtual);
                destinos.enqueue(andarAtual);
            }
        } 
    }

    public void movimentacao() {
        energiaUltimoMinuto = 0.0;
        int andarAnterior = andarAtual;
        
        if (!destinos.estaVazia()) {
            int prox = destinos.primeiro();
            if (andarAtual < prox) {
                andarAtual++;
                direcao = 'S';
            } else if (andarAtual > prox) {
                andarAtual--;
                direcao = 'D';
            } else {
                destinos.dequeue();
                direcao = 'P';
            }
            
            int deslocamento = Math.abs(andarAtual - andarAnterior);
            if (deslocamento > 0) {
                double gasto = deslocamento * ENERGIA_POR_ANDAR;
                energiaConsumida += gasto;
                energiaUltimoMinuto += gasto;
            }
        } else {
            direcao = 'P';
        }
    }

     @Override
    public void atualizar(int minutoSimulado) {
        System.out.println("Elevador " + id + " no andar " + andarAtual + " dir=" + direcao + " min=" + minutoSimulado);
        desembarcarAtual();
        embarcar();
        movimentacao();
    }

    public void exibirEstado() {
        System.out.printf("  Elevador %d -> Andar atual: %d | Direção: %c | Passageiros: %d%n", id, andarAtual, direcao, pessoas.getTamanho());
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getLotacaoAtual() {
        return pessoas.getTamanho();
    }

    public double getEnergiaUltimoMinuto() {
        return energiaUltimoMinuto;
    }

    public double getEnergiaConsumida() {
        return energiaConsumida;
    }

    public int getId() { return id; }
}