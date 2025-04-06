package recursos;

public class Carta {
    private final Palo palo;
    private final int numero;
/*----------------------------------------------------------*/
    public Carta(Palo palo, int numero) {
        this.palo = palo;
        this.numero = numero;
    }
/*----------------------------------------------------------*/
    public String getPalo() {
        return palo.toString();
    }
/*----------------------------------------------------------*/
    public int getNumero() {
        return numero;
    }
/*----------------------------------------------------------*/
    @Override
    public String toString() {
        return "(" + palo + ", " + numero + ')';
    }
}
