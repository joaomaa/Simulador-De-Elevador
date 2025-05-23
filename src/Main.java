public class Main {
    public static void main(String[] args) throws InterruptedException {
        Simulador sim = new Simulador(
            5, 5, 1000, 6,
            8 * 60, 10 * 60,
            5, 2, 100
        );
        
        sim.iniciar();
        while (sim.getPessoasGeradas() < 100 || !sim.todasPessoasAtendidas()) { Thread.sleep(1000); }
        sim.encerrar();
    }
}