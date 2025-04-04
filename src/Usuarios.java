public class Usuarios {
    int peso;
    int consumoDeEnergia;

    Usuarios() {
        peso = 1;
        consumoDeEnergia = 1;
    }

    public class Comum extends Usuarios {}
    public class Idosos extends Usuarios {}
    public class Pcd extends Usuarios {}
}
