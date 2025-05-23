import java.io.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Simulador implements Serializable {
    private int minutoSimulado;
    private int velocidadeEmMs;
    private transient Timer timer;
    private boolean emExecucao;
    private Predio predio;
    private int maxPessoas;
    private int pessoasGeradas = 0;
    private int picoInicio, picoFim, chamadasPico, chamadasFora;
    private int totalAndares, nextPessoaId = 1;
    private Random random = new Random();
    private int totalChamadasGeradas = 0;
    private int duracaoSimulacao = 0;
    private Lista<Integer> minutos = new Lista<>();
    private Lista<Double> consumos = new Lista<>();

    public Simulador(int andares, int elevadores, int velocidadeMs,
                     int capacidade, int picoInicio, int picoFim,
                     int chamadasPico, int chamadasFora, int maxPessoas) {
        this.minutoSimulado = 0;
        this.velocidadeEmMs = velocidadeMs;
        this.predio = new Predio(andares, elevadores, capacidade);
        this.totalAndares = andares;
        this.picoInicio = picoInicio;
        this.picoFim = picoFim;
        this.chamadasPico = chamadasPico;
        this.chamadasFora = chamadasFora;
        this.maxPessoas = maxPessoas;
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
                relatorioConsumo();
            }
        }, 0, velocidadeEmMs);
    }

    private void relatorioConsumo() {
        double consumoMinuto = 0;
        Lista<Elevador> elevs = predio.getCentral().getElevadores();
        No<Elevador> temp = elevs.getInicio();
        while (temp != null) {
            consumoMinuto += temp.getElemento().getEnergiaUltimoMinuto();
            temp = temp.getProximo();
        }
        System.out.printf("Energia consumida no minuto %d: %.2f kWh%n", minutoSimulado, consumoMinuto);
        // grava dados em listas próprias
        minutos.inserirFim(minutoSimulado, minutos.getTamanho());
        consumos.inserirFim(consumoMinuto, consumos.getTamanho());
        minutoSimulado++;
    }

    private void gerarChamadasAutomatica(int minuto) {
        if (pessoasGeradas >= maxPessoas) return;
        int minutoDoDia = minuto % 1440;
        int chamadas = (minutoDoDia >= picoInicio && minutoDoDia < picoFim)
                        ? chamadasPico : chamadasFora;
        for (int i = 0; i < chamadas && pessoasGeradas < maxPessoas; i++) {
            int origem = random.nextInt(totalAndares);
            int destino;
            do { destino = random.nextInt(totalAndares); }
            while (destino == origem);
            int prioridade = random.nextInt(3);
            Pessoa p = new Pessoa(nextPessoaId++, origem, destino, prioridade);
            p.setTempoChamada(minuto);
            predio.adicionarPessoa(p);
            pessoasGeradas++;
            totalChamadasGeradas++;
        }
    }

    public void encerrar() {
        if (timer != null) timer.cancel();
        emExecucao = false;
        duracaoSimulacao = minutoSimulado;
        System.out.println("-> Simulação encerrada.");
        gerarRelatorio();
    }

    private void gerarRelatorio() {
        System.out.println("\n===== RELATÓRIO FINAL =====");
        System.out.printf("Duração: %d minutos simulados%n", duracaoSimulacao);
        System.out.printf("Total chamadas: %d passageiros%n", totalChamadasGeradas);

        double energiaTotal = 0;
        Lista<Elevador> elevador = predio.getCentral().getElevadores();
        No<Elevador> temp = elevador.getInicio();
        while (temp != null) {
            Elevador e = temp.getElemento();
            System.out.printf("Elevador %d: %.2f kWh total%n", e.getId(), e.getEnergiaConsumida());
            energiaTotal += e.getEnergiaConsumida();
            temp = temp.getProximo();
        }
        System.out.printf("Energia total consumida: %.2f kWh%n", energiaTotal);
        System.out.printf("Tempo médio de espera: %.2f minutos%n", Estatisticas.getMediaEspera());

        // grava arquivos CSV sem usar Map
        try (PrintWriter writer = new PrintWriter(new FileWriter("espera.csv"))) {
            writer.println("Pessoa,TempoEspera");
            int i = 1;
            Lista<Integer> tempos = Estatisticas.getTodosTempos();
            No<Integer> atual = tempos.getInicio();
            while (atual != null) {
                writer.printf("%d,%d%n", i++, atual.getElemento());
                atual = atual.getProximo();
            }
            System.out.println("Relatório de espera salvo em 'espera.csv'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter("consumo.csv"))) {
            writer.println("Minuto,kWh");
            DecimalFormat df = new DecimalFormat("0.00");
            df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

            No<Integer> minNo = minutos.getInicio();
            No<Double> consNo = consumos.getInicio();
            while (minNo != null && consNo != null) {
                writer.printf("%d,%s%n", minNo.getElemento(), df.format(consNo.getElemento()));
                minNo = minNo.getProximo();
                consNo = consNo.getProximo();
            }
            System.out.println("Dados de consumo salvos em 'consumo.csv'");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("================================");
    }

    public int getPessoasGeradas() { return pessoasGeradas; }

    public boolean todasPessoasAtendidas() {
        Lista<Andar> andares = predio.getAndares();
        No<Andar> noAndar = andares.getInicio();
        while (noAndar != null) {
            if (noAndar.getElemento().getPessoasAguardando().getTamanho() > 0) return false;
            noAndar = noAndar.getProximo();
        }
        Lista<Elevador> elevadores = predio.getCentral().getElevadores();
        No<Elevador> noElevador = elevadores.getInicio();
        while (noElevador != null) {
            if (noElevador.getElemento().getLotacaoAtual() > 0) return false;
            noElevador = noElevador.getProximo();
        }
        return true;
    }

    public void continuar() {
        if (!emExecucao) {
            iniciarTimer();
            emExecucao = true;
            System.out.println("Simulação retomada.");
        }
    }

    public void gravar(String nomeArquivo) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            out.writeObject(this);
            System.out.println("Simulação gravada em: " + nomeArquivo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Simulador carregar(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            Simulador sim = (Simulador) in.readObject();
            sim.continuar();
            return sim;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
