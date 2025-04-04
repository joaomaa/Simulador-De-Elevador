public class Simulation {
    class Predio {
        class Horario {
            double horaAtual;

            Horario(double horaAtual) {
                this.horaAtual = horaAtual;
            }

            public void horarioepico() {
                if (horaAtual >= 7 && horaAtual <= 9) {
                    System.out.println("É horário de pico!");
                } else if (horaAtual >= 17 && horaAtual <= 19) {
                    System.out.println("É horário de pico!");
                } else {
                    System.out.println("Não é horário de pico.");
                }
            }

        }
    }


}
