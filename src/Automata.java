public class Automata {
    private char[] alfabeto;
    private Nodo nodoInicial;
    private Nodo[] nodosAceptacion;

    public Automata(char[] alfabeto, Nodo nodoInicial, Nodo[] nodosAceptacion) {
        this.alfabeto = alfabeto;
        this.nodoInicial = nodoInicial;
        this.nodosAceptacion = nodosAceptacion;
    }

    public boolean verificarCadena(String cadena) {
        Nodo estadoActual = nodoInicial;

        for (char simbolo : cadena.toCharArray()) {
            if (!estaEnAlfabeto(simbolo)) {
                return false;  // El símbolo no está en el alfabeto, cadena no válida
            }

            estadoActual = estadoActual.transicion(simbolo);
            if (estadoActual == null) {
                return false;  // No hay transición para el símbolo en el estado actual
            }
        }

        for (Nodo nodoAceptacion : nodosAceptacion) {
            if (estadoActual == nodoAceptacion) {
                return true;  // La cadena es aceptada
            }
        }

        return false;  // La cadena no es aceptada
    }

    private boolean estaEnAlfabeto(char simbolo) {
        for (char c : alfabeto) {
            if (c == simbolo) {
                return true;
            }
        }
        return false;
    }
}