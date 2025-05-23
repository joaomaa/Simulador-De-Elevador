import java.io.Serializable;

public class Pessoa implements Serializable {
    private int id;
    private int tempoChamada;
    private int tempoEntrada;
    private int andarOrigem;
    private int andarDestino;
    private boolean dentroElevador;
    private int prioridade;  // 0 = normal, 1 = idoso, 2 = cadeirante

    public Pessoa(int id, int origem, int destino, int prioridade) {
        this.id = id;
        this.andarOrigem = origem;
        this.andarDestino = destino;
        this.dentroElevador = false;
        this.prioridade = prioridade;
    }

    
    public void setTempoChamada(int minuto) {
        this.tempoChamada = minuto;
    }

    public void setTempoEntrada(int minuto) {
        this.tempoEntrada = minuto;
    }

    public void entrarElevador() {
        this.dentroElevador = true;
    }

    public void sairElevador() {
        this.dentroElevador = false;
    }

    public int getId() {
        return id;
    }

    public int getAndarOrigem() {
        return andarOrigem;
    }

    public int getAndarDestino() {
        return andarDestino;
    }

    public boolean estaDentroDoElevador() {
        return dentroElevador;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public int getTempoDeEspera() {
        return tempoEntrada - tempoChamada;
    }
}
