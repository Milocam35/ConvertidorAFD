import java.util.*;

public class AFN extends Automata {

    private Map<Nodo, Map<Character, Set<Nodo>>> transicionesAFND;

    public AFN(char[] alfabeto, Nodo[] estados, Nodo estadoInicial, List<Nodo> estadosFinales) {
        super(alfabeto, estados, estadoInicial, estadosFinales);
        this.transicionesAFND = new HashMap<>();
        inicializarTransiciones();
    }

    private void inicializarTransiciones() {
        for (Nodo estado : super.getEstados()) {
            transicionesAFND.put(estado, new HashMap<>());
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

    public boolean verificarCadenaAFND(String cadena) {
        Set<Nodo> estadosActuales = new HashSet<>();
        estadosActuales.add(super.getEstadoInicial());
    
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

    public void mostrarTablaTransicionesAFND() {
        System.out.println("Tabla de Transiciones del AFND:");
    
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
            System.out.print(estado.getNombre() + "\t|");
            Map<Character, Set<Nodo>> transicionesEstado = transicionesAFND.get(estado);
            for (char simbolo : super.getAlfabeto()) {
                Set<Nodo> destinos = transicionesEstado.get(simbolo);
                System.out.print("\t" + (destinos != null ? destinos : "-") + "\t|");
            }
            System.out.println();
        }
    }

    public boolean verificarCadenaAFNDLambda(String cadena) {
        Set<Nodo> estadosActuales = new HashSet<>();
        estadosActuales.add(super.getEstadoInicial());
    
        // Agregar todos los estados alcanzables mediante transiciones epsilon desde el estado inicial
        estadosActuales.addAll(obtenerClausuraEpsilon(super.getEstadoInicial()));
    
        for (char simbolo : cadena.toCharArray()) {
            if (!esSimboloValido(simbolo)) {
                return false;
            }
    
            Set<Nodo> nuevosEstados = new HashSet<>();
            for (Nodo estadoActual : estadosActuales) {
                // Obtener los estados alcanzables desde el estado actual con el símbolo actual
                Set<Nodo> destinos = transicionAFND(estadoActual, simbolo);
                // Agregar los estados alcanzables mediante transiciones epsilon desde los nuevos estados
                for (Nodo destino : destinos) {
                    nuevosEstados.addAll(obtenerClausuraEpsilon(destino));
                }
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

    private Set<Nodo> obtenerClausuraEpsilon(Nodo estado) {
        Set<Nodo> clausuraEpsilon = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();
        cola.add(estado);
        clausuraEpsilon.add(estado);
    
        while (!cola.isEmpty()) {
            Nodo actual = cola.poll();
            // Obtener los destinos de las transiciones epsilon desde el estado actual
            Set<Nodo> destinosEpsilon = transicionAFND(actual, '\0');
            for (Nodo destino : destinosEpsilon) {
                if (!clausuraEpsilon.contains(destino)) {
                    clausuraEpsilon.add(destino);
                    cola.add(destino);
                }
            }
        }
        return clausuraEpsilon;
    }

    public void agregarTransicionEpsilon(Nodo origen, Nodo destino) {
        if (transicionesAFND.containsKey(origen)) {
            Map<Character, Set<Nodo>> transicionesDesdeEstado = transicionesAFND.get(origen);
            // Las transiciones epsilon se representan con el caracter especial '\0'
            transicionesDesdeEstado.computeIfAbsent('\0', k -> new HashSet<>()).add(destino);
        } else {
            Map<Character, Set<Nodo>> transiciones = new HashMap<>();
            Set<Nodo> destinos = new HashSet<>();
            destinos.add(destino);
            transiciones.put('\0', destinos);
            transicionesAFND.put(origen, transiciones);
        }
    }

    public Map<Nodo, Map<Character, Set<Nodo>>> getTransicionesAFND() {
        return transicionesAFND;
    }

    public void setTransicionesAFND(Map<Nodo, Map<Character, Set<Nodo>>> transicionesAFND) {
        this.transicionesAFND = transicionesAFND;
    }
    
}
