import java.util.HashMap;

class Nodo {
    private String nombre;
    private HashMap<Character, Nodo> transiciones;

    public Nodo(String nombre) {
        this.nombre = nombre;
        this.transiciones = new HashMap<>();
    }

    public void agregarTransicion(char simbolo, Nodo nodoDestino) {
        transiciones.put(simbolo, nodoDestino);
    }

    public Nodo transicion(char simbolo) {
        return transiciones.get(simbolo);
    }

    public String getNombre() {
        return nombre;
    }
}
