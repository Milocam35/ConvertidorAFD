import java.util.HashMap;
import java.util.Map;

public class Automata {
    private char[] alfabeto;
    private Nodo[] estados;
    private Nodo estadoInicial;
    private Nodo[] estadosFinales;
    private Map<Nodo, Map<Character, Nodo>> transiciones;

    public Automata(char[] alfabeto, Nodo[] estados, Nodo estadoInicial, Nodo[] estadosFinales) {
        this.alfabeto = alfabeto;
        this.estados = estados;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.transiciones = new HashMap<>();
        inicializarTransiciones();
    }

    private void inicializarTransiciones() {
        for (Nodo estado : estados) {
            transiciones.put(estado, new HashMap<>());
        }
    }

    public void agregarTransicion(Nodo origen, char simbolo, Nodo destino) {
        if (transiciones.containsKey(origen)) {
            transiciones.get(origen).put(simbolo, destino);
        }
    }

    public Nodo transicion(Nodo estadoActual, char simbolo) {
        if (transiciones.containsKey(estadoActual)) {
            return transiciones.get(estadoActual).get(simbolo);
        }
        return null;
    }

    public boolean verificarCadena(String cadena) {
        Nodo estadoActual = estadoInicial;

        for (char simbolo : cadena.toCharArray()) {
            if (!esSimboloValido(simbolo)) {
                return false; // El símbolo no está en el alfabeto
            }

            estadoActual = transicion(estadoActual, simbolo);

            if (estadoActual == null) {
                return false; // No hay transición para el símbolo en el estado actual
            }
        }

        return esEstadoAceptacion(estadoActual);
    }

    private boolean esSimboloValido(char simbolo) {
        for (char c : alfabeto) {
            if (c == simbolo) {
                return true;
            }
        }
        return false;
    }

    private boolean esEstadoAceptacion(Nodo estado) {
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

    public Nodo[] getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(Nodo[] estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public Map<Nodo, Map<Character, Nodo>> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Map<Nodo, Map<Character, Nodo>> transiciones) {
        this.transiciones = transiciones;
    }
    
    
    
}