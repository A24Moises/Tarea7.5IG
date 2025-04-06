import recursos.Baraja;
import recursos.Carta;
import java.util.ArrayList;
import java.util.List;

public class SieteyMedia {
    private Baraja baraja;
    private List<Carta> cartasJugador;
    private List<Carta> cartasBanca;
    private boolean turnoJugadorFinalizado;
    private double valorJugadorAlPlantarse; // Nuevo campo
    private boolean bancaPlantada; // Indicador si la banca se ha plantado

    public SieteyMedia() {
        baraja = new Baraja();
        baraja.barajar();
        cartasJugador = new ArrayList<>();
        cartasBanca = new ArrayList<>();
        turnoJugadorFinalizado = false;
        valorJugadorAlPlantarse = 0.0;
    }

    public void iniciarJuego() {
        recibirCartaJugador();
    }

    public void recibirCartaJugador() {
        if (!turnoJugadorFinalizado) {
            cartasJugador.add(baraja.darCartas(1).get(0));
        }
    }

    public void plantarse() {
        turnoJugadorFinalizado = true;
        valorJugadorAlPlantarse = calcularValor(cartasJugador); // Guardamos el valor
    }

    public void jugarBanca() {
        if (!turnoJugadorFinalizado || valorJugadorAlPlantarse > 7.5) return;

        double valorBanca = calcularValor(cartasBanca);
        if (valorBanca < valorJugadorAlPlantarse) {
            cartasBanca.add(baraja.darCartas(1).get(0)); // Solo una carta por llamada
        }
    }

    public String obtenerResultado() {
        double valorJugador = calcularValor(cartasJugador);
        double valorBanca = calcularValor(cartasBanca);

        if (valorJugador > 7.5) return "Te has pasado, gana la banca";
        if (valorBanca > 7.5) return "La banca se ha pasado, tú ganas";
        return valorBanca >= valorJugador ? "Gana la banca." : "Ganas tú.";
    }

    public List<Carta> getCartasJugador() {
        return new ArrayList<>(cartasJugador);
    }

    public List<Carta> getCartasBanca() {
        return new ArrayList<>(cartasBanca);
    }

    public double calcularValor(List<Carta> cartas) {
        return cartas.stream().mapToDouble(carta -> carta.getNumero() > 7 ? 0.5 : carta.getNumero()).sum();
    }

    // Método para que la banca tome una carta mientras su valor sea menor o igual a 7.5
    public void jugarBancaUnaCarta() {
        if (!baraja.estaVacia() && calcularValor(cartasBanca) <= 7.5) {
            cartasBanca.add(baraja.darCartas(1).get(0));
        }
    }

    // Verifica si el juego terminó (si alguno pasó los 7.5 o ambos se plantaron)
    public boolean verificarFinDelJuego() {
        double valorJugador = calcularValor(cartasJugador);
        double valorBanca = calcularValor(cartasBanca);

        // Si el jugador o la banca se pasan de 7.5, el juego termina
        if (valorJugador > 7.5 || valorBanca > 7.5) return true;

        // Si ambos jugadores se han plantado, el juego termina en empate
        if (turnoJugadorFinalizado && bancaPlantada) return true;

        return false;
    }
    public boolean isBancaPlantada() {
        return bancaPlantada;
    }
}

