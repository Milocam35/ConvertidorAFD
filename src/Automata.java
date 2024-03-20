import java.util.*;

public class Automata {
    private char[] alfabeto;
    private Nodo[] estados;
    private Nodo estadoInicial;
    private List<Nodo> estadosFinales;
    private Map<Nodo, Map<Character, Nodo>> transicionesAFD;
    private Map<Nodo, Map<Character, Set<Nodo>>> transicionesAFND;

    public Automata(char[] alfabeto, Nodo[] estados, Nodo estadoInicial, List<Nodo> estadosFinales) {
        this.alfabeto = alfabeto;
        this.estados = estados;
        this.estadoInicial = estadoInicial;
        this.estadosFinales = estadosFinales;
        this.transicionesAFD = new HashMap<>();
        this.transicionesAFND = new HashMap<>();
        inicializarTransiciones();
    }

    private void inicializarTransiciones() {
        for (Nodo estado : estados) {
            transicionesAFD.put(estado, new HashMap<>());
            transicionesAFND.put(estado, new HashMap<>());
        }
    }

    public void agregarTransicionAFD(Nodo origen, char simbolo, Nodo destino) {
        if (transicionesAFD.containsKey(origen)) {
            transicionesAFD.get(origen).put(simbolo, destino);
        }
    }

    public void agregarTransicionAFND(Nodo origen, char simbolo, Set<Nodo> destinos) {
        if (transicionesAFND.containsKey(origen)) {
            transicionesAFND.get(origen).put(simbolo, destinos);
        } else {
            Map<Character, Set<Nodo>> transiciones = new HashMap<>();
            transiciones.put(simbolo, destinos);
            transicionesAFND.put(origen, transiciones);
        }
    }
    


    public Nodo transicionAFD(Nodo estadoActual, char simbolo) {
        if (transicionesAFD.containsKey(estadoActual)) {
            Map<Character, Nodo> transicionesDesdeEstado = transicionesAFD.get(estadoActual);
            if (transicionesDesdeEstado.containsKey(simbolo)) {
                return transicionesDesdeEstado.get(simbolo);
            }
        }
        // Si no hay una transición definida para el símbolo, devolver el mismo estado actual
        return estadoActual;
    }

    public Set<Nodo> transicionAFND(Nodo estadoActual, char simbolo) {
        Set<Nodo> destinos = new HashSet<>();
        if (transicionesAFND.containsKey(estadoActual)) {
            Map<Character, Set<Nodo>> transicionesDesdeEstado = transicionesAFND.get(estadoActual);
            if (transicionesDesdeEstado.containsKey(simbolo)) {
                destinos.addAll(transicionesDesdeEstado.get(simbolo));
            }
        }
        return destinos;
    }
    

    public boolean verificarCadena(String cadena) {
        Nodo estadoActual = estadoInicial;

        for (char simbolo : cadena.toCharArray()) {
            if (!esSimboloValido(simbolo)) {
                return false;
            }

            estadoActual = transicionAFD(estadoActual, simbolo);

            if (estadoActual == null) {
                return false;
            }
        }
        return esEstadoAceptacion(estadoActual);
    }

    public boolean verificarCadenaAFND(String cadena) {
        Set<Nodo> estadosActuales = new HashSet<>();
        estadosActuales.add(estadoInicial);
    
        for (char simbolo : cadena.toCharArray()) {
            if (!esSimboloValido(simbolo)) {
                return false;
            }
    
            Set<Nodo> nuevosEstados = new HashSet<>();
            for (Nodo estadoActual : estadosActuales) {
                Set<Nodo> destinos = transicionAFND(estadoActual, simbolo);
                nuevosEstados.addAll(destinos);
            }
            estadosActuales = nuevosEstados;
    
            if (estadosActuales.isEmpty()) {
                return false;
            }
        }
    
        // Verificar si al menos uno de los estados finales es alcanzable
        for (Nodo estado : estadosActuales) {
            if (esEstadoAceptacion(estado)) {
                return true;
            }
        }
    
        return false;
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

    public List<Nodo> getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(List<Nodo> estadosFinales) {
        this.estadosFinales = estadosFinales;
    }

    public Map<Nodo, Map<Character, Nodo>> getTransicionesAFD() {
        return transicionesAFD;
    }

    public void setTransiciones(Map<Nodo, Map<Character, Nodo>> transiciones) {
        this.transicionesAFD = transiciones;
    }
    
    
    
}