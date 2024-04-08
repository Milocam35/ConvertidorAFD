import java.util.*;

public class Automata {
    private char[] alfabeto;
    private Nodo[] estados;
    private Nodo estadoInicial;
    private List<Nodo> estadosFinales;
    private boolean vida;

    public Automata(char[] alfabeto, Nodo[] estados, Nodo estadoInicial, List<Nodo> estadosFinales) {
        this.alfabeto = alfabeto;
        this.estados = estados;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.vida = true;
    }
    
    public boolean esSimboloValido(char simbolo) {
        for (char c : alfabeto) {
            if (c == simbolo) {
                return true;
            }
        }
        return false;
    }

    public boolean esEstadoAceptacion(Nodo estado) {
        for (Nodo nodo : estadosFinales) {
            if (nodo == estado) {
                return true;
            }
        }
        return false;
    }

    /*
     * Getters y Setters
     */

    public char[] getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(char[] alfabeto) {
        this.alfabeto = alfabeto;
    }

    public Nodo[] getEstados() {
        return estados;
    }

    public void setEstados(Nodo[] estados) {
        this.estados = estados;
    }

    public Nodo getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(Nodo estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public List<Nodo> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(List<Nodo> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }
    
    public boolean getVida() {
        return vida;
    }

    public void setVida(boolean vida) {
        this.vida = vida;
    }
}