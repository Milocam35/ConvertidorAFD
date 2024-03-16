public class App {
    public static void main(String[] args) {
        Nodo q0 = new Nodo("q0");
        Nodo q1 = new Nodo("q1");
        Nodo q2 = new Nodo("q2");

        q0.agregarTransicion('a', q1);
        q1.agregarTransicion('b', q2);
        q2.agregarTransicion('c', q0);

        char[] alfabeto = {'a', 'b', 'c'};
        Nodo[] nodosAceptacion = {q0};

        Automata automata = new Automata(alfabeto, q0, nodosAceptacion);

        String cadena1 = "abc";
        String cadena2 = "abca";
        
        System.out.println("¿La cadena \"" + cadena1 + "\" es aceptada? " + automata.verificarCadena(cadena1));
        System.out.println("¿La cadena \"" + cadena2 + "\" es aceptada? " + automata.verificarCadena(cadena2));
    }
}

