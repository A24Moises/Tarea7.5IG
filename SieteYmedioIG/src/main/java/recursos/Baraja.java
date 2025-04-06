package recursos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Baraja {
    private final int NUM_CARTAS = 40;
    private List<Carta> cartas;
    private int primeraMano = 0;

    public Baraja() {
        cartas = new ArrayList<>();
        for (Palo p : Palo.values()) {
            for (int j = 0; j < 12; j++) {
                if (j == 7 || j == 8) continue;
                cartas.add(new Carta(p, j + 1));
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public List<Carta> darCartas(int numCartasDar) {
        List<Carta> cartasParaDar = new ArrayList<>();
        for (int i = 0; i < numCartasDar; i++) {
            if (primeraMano < cartas.size()) {
                cartasParaDar.add(cartas.get(primeraMano++));
            }
        }
        return cartasParaDar;
    }

    public boolean estaVacia() {
        return primeraMano >= cartas.size();
    }
}
