import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Simulador implements Serializable {
    private int minutoSimulado;
    private int velocidadeEmMs;
    private transient Timer timer;
    private boolean emExecucao;
    private Predio predio;

    private int picoInicio, picoFim, chamadasPico, chamadasFora;
    private int totalAndares, nextPessoaId = 1;
    private Random random = new Random();
    private int totalChamadasGeradas = 0;
    private int duracaoSimulacao = 0;

    public Simulador(int andares, int elevadores, int velocidadeMs,
                     int capacidade, int picoInicio, int picoFim,
                     int chamadasPico, int chamadasFora) {
        this.minutoSimulado = 0;
        this.velocidadeEmMs = velocidadeMs;
        this.predio = new Predio(andares, elevadores, capacidade);
        this.totalAndares = andares;
        this.picoInicio = picoInicio;
        this.picoFim = picoFim;
        this.chamadasPico = chamadasPico;
        this.chamadasFora = chamadasFora;
    }

    public void iniciar() {
        if (emExecucao) return;
        emExecucao = true;
        iniciarTimer();
        System.out.println("→ Simulação iniciada com geração automática e monitoramento de energia.");
    }

    private void iniciarTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                gerarChamadasAutomatica(minutoSimulado);
                predio.atualizar(minutoSimulado);
                predio.exibirEstado();

                // Relatório de consumo deste minuto
                double consumoMinuto = 0;
                Lista<Elevador> elevs = predio.getCentral().getElevadores();
                No<Elevador> cursor = elevs.getInicio();
                while (cursor != null) {
                    consumoMinuto += cursor.getElemento().getEnergiaUltimoMinuto();
                    cursor = cursor.getProximo();
                }
                System.out.printf("Energia consumida no minuto %d: %.2f kWh%n", minutoSimulado, consumoMinuto);

                minutoSimulado++;
            }
        }, 0, velocidadeEmMs);
    }

    private void gerarChamadasAutomatica(int minuto) {
        int minutoDoDia = minuto % 1440;
        int chamadas = (minutoDoDia >= picoInicio && minutoDoDia < picoFim)
                        ? chamadasPico : chamadasFora;
        for (int i = 0; i < chamadas; i++) {
            int origem = random.nextInt(totalAndares);
            int destino;
            do { destino = random.nextInt(totalAndares); }
            while (destino == origem);
            int prioridade = random.nextInt(3);
            Pessoa p = new Pessoa(nextPessoaId++, origem, destino, prioridade);
            predio.adicionarPessoa(p);
            totalChamadasGeradas++;
        }
    }

    public void encerrar() {
        if (timer != null) timer.cancel();
        emExecucao = false;
        duracaoSimulacao = minutoSimulado;
        System.out.println("→ Simulação encerrada.");
        gerarRelatorio();
    }

    private void gerarRelatorio() {
        System.out.println("\n===== RELATÓRIO FINAL =====");
        System.out.printf("Duração: %d minutos simulados%n", duracaoSimulacao);
        System.out.printf("Total chamadas: %d passageiros%n", totalChamadasGeradas);
        double energiaTotal = 0;
        Lista<Elevador> elevs = predio.getCentral().getElevadores();
        No<Elevador> cursor = elevs.getInicio();
        while (cursor != null) {
            Elevador e = cursor.getElemento();
            System.out.printf("Elevador %d: %.2f kWh total%n", e.getId(), e.getEnergiaConsumida());
            energiaTotal += e.getEnergiaConsumida();
            cursor = cursor.getProximo();
        }
        System.out.printf("Energia total consumida: %.2f kWh%n", energiaTotal);
        System.out.println("================================");
    }
}