public class Estatisticas {
    private static Lista<Integer> temposEspera = new Lista<>();

    public static void registrarEspera(int tempo) {
        int tamanhoAtual = 0;
        No<Integer> temp = temposEspera.getInicio();
        while (temp != null) {
            tamanhoAtual++;
            temp = temp.getProximo();
        }
        temposEspera.inserirFim(tempo, tamanhoAtual);
    }

    public static double getMediaEspera() {
        int soma = 0;
        int count = 0;
        No<Integer> temp = temposEspera.getInicio();
        while (temp != null) {
            soma += temp.getElemento();
            count++;
            temp = temp.getProximo();
        }
        return count == 0 ? 0 : (double) soma / count;
    }

    public static Lista<Integer> getTodosTempos() {
        Lista<Integer> copia = new Lista<>();
        No<Integer> temp = temposEspera.getInicio();
        int index = 0;
        while (temp != null) {
            copia.inserirFim(temp.getElemento(), index);
            temp = temp.getProximo();
            index++;
        }
        return copia;
    }
}
