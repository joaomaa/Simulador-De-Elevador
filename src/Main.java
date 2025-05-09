public class Main {
    public static void main(String[] args) {
        // Cria um simulador com 10 andares, 2 elevadores e 1000ms (1 segundo) por minuto simulado
        Simulador simulador = new Simulador(10, 2, 1000);

        // Inicia a simulação
        simulador.iniciar();

        // Aguarda 5 segundos para demonstrar a simulação rodando

        // Pausa a simulação
        simulador.pausar();

        // Grava o estado da simulação em um arquivo
        simulador.gravar("simulacao.dat");

        // Carrega a simulação do arquivo
        Simulador simuladorCarregado = Simulador.carregar("simulacao.dat");

        // Aguarda mais 5 segundos para mostrar a simulação retomada
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Encerra a simulação
        if (simuladorCarregado != null) {
            simuladorCarregado.encerrar();
        }
    }
}