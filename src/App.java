import java.util.Scanner;

public class App {

    public static void definirTablaTransiciones(Automata automata, Scanner sc) {
        Nodo[] estados = automata.getEstados();
        char[] alfabeto = automata.getAlfabeto();

        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                boolean aceptaEstado;
                String estadoTemp;

                do {
                    aceptaEstado = false;

                    System.out.print(estados[i].getNombre() + "->" + alfabeto[j] + ": ");
                    estadoTemp = sc.next();

                    // Verificar si el estado ingresado es válido
                    for (Nodo nodo : estados) {
                        if (nodo.getNombre().equals(estadoTemp)) {
                            automata.agregarTransicion(estados[i], alfabeto[j], nodo);
                            System.out.println("Transición agregada");
                            aceptaEstado = true;
                            break;
                        }
                    }

                    if (!aceptaEstado) {
                        System.out.println("Estado inválido. Por favor, ingrese un estado válido.");
                    }
                } while (!aceptaEstado);
            }
        }
    }

    public static void main(String[] args) {
        // Crear nodos
        Nodo q0 = new Nodo("q0");
        Nodo q1 = new Nodo("q1");
        Nodo[] estados = {q0, q1};

        // Definir alfabeto
        char[] alfabeto = {'0', '1'};

        // Definir estados inicial y finales
        Nodo estadoInicial = q0;
        Nodo[] estadosFinales = {q1};

        // Crear autómata
        Automata automata = new Automata(alfabeto, estados, estadoInicial, estadosFinales);

        // Crear un Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Definir la tabla de transiciones
        definirTablaTransiciones(automata, scanner);

        // Verificar cadenas
        System.out.println("Ingrese la cadena a verificar:");
        String cadena = scanner.next();
        System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));

        // Cerrar el Scanner
        scanner.close();
    }

}
