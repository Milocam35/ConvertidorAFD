import java.util.*;

public class AFD extends Automata {
    private Map<Nodo, Map<Character, Nodo>> transicionesAFD;

    public AFD(char[] alfabeto, Nodo[] estados, Nodo estadoInicial, List<Nodo> estadosFinales) {
        super(alfabeto, estados, estadoInicial, estadosFinales);
        this.transicionesAFD = new HashMap<>();
        inicializarTransiciones();
    }
    
    private void inicializarTransiciones() {
        for (Nodo estado : super.getEstados()) {
            transicionesAFD.put(estado, new HashMap<>());
        }
    }

    public void agregarTransicionAFD(Nodo origen, char simbolo, Nodo destino) {
        if (transicionesAFD.containsKey(origen)) {
            transicionesAFD.get(origen).put(simbolo, destino);
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

    public boolean verificarCadenaAFD(String cadena) {
        Nodo estadoActual = super.getEstadoInicial();
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

    public void mostrarTablaTransicionesAFD() {
        System.out.println("Tabla de Transiciones del AFD:");
    
        // Imprimir encabezados de columna
        System.out.print("Estado\t|");
        for (char simbolo : super.getAlfabeto()) {
            System.out.print("\t" + simbolo + "\t|");
        }
        System.out.println();
    
        // Imprimir separador
        int separadorLength = 8 + (super.getAlfabeto().length * 6); // Longitud de separador = longitud del estado + longitud de cada símbolo de alfabeto + separadores
        for (int i = 0; i < separadorLength; i++) {
            System.out.print("-");
        }
        System.out.println();
    
        // Imprimir filas de la tabla de transiciones
        for (Nodo estado : super.getEstados()) {
            Map<Character, Nodo> transicionesEstado = transicionesAFD.get(estado);
            System.out.print(estado.getNombre() + "\t|");
            for (char simbolo : super.getAlfabeto()) {
                Nodo destino = transicionesEstado.get(simbolo);
                System.out.print("\t" + (destino != null ? destino.getNombre() : "-") + "\t|");
            }
            System.out.println();
        }
    }

    public Map<Nodo, Map<Character, Nodo>> getTransicionesAFD() {
        return transicionesAFD;
    }

    public void setTransicionesAFD(Map<Nodo, Map<Character, Nodo>> transicionesAFD) {
        this.transicionesAFD = transicionesAFD;
    }
    
}
