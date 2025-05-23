import java.io.Serializable;

public class Andar implements Serializable {
    private int numero;
    private FilaPrioridade<Pessoa> pessoasAguardando;
    private PainelElevador painel;

    public Andar(int numero) {
        this.numero = numero;
        this.pessoasAguardando = new FilaPrioridade<>();
        this.painel = new PainelElevador();
    }

    public void exibirEstado() {
        System.out.printf("  Andar %d -> Pessoas aguardando: %d%n",
            numero,
            pessoasAguardando.getTamanho());
    }

    public int getNumero() {
        return numero;
    }

    public void adicionarPessoaNaFila(Pessoa p) {
        pessoasAguardando.enqueue(p, p.getPrioridade());
        System.out.printf("Pessoa %d (prio %d) aguardando no andar %d%n",
                          p.getId(), p.getPrioridade(), numero);
    }
    public FilaPrioridade<Pessoa> getPessoasAguardando() {
        return pessoasAguardando;
    }

    public PainelElevador getPainel() {
        return painel;
    }
}