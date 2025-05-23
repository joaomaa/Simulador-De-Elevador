public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1) Cria simulação com 5 andares, 2 elevadores, e 1 segundo por "minuto simulado"
        // Configuração: 5 andares, 2 elevadores, 1 segundo por min, capacidade 6
        // Pico: 08:00–10:00 (8*60 a 10*60), 5 chamadas/min no pico, 2 fora do pico
        Simulador sim = new Simulador(
            5, 5, 1000, 6,
            8 * 60, 10 * 60,
            5, 2
        );
        sim.iniciar();

        // Executa 30 min simulados (30 segundos reais)
        Thread.sleep(1440 * 1000);
        sim.encerrar();
    }
}
