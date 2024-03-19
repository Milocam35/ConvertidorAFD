import java.util.ArrayList;
import java.util.List;
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
    
                    // Permitir transiciones nulas si el estado ingresado es "null"
                    if (estadoTemp.equalsIgnoreCase("null")) {
                        automata.agregarTransicion(estados[i], alfabeto[j], null);
                        System.out.println("Transición nula agregada");
                        aceptaEstado = true;
                    } else {
                        // Verificar si el estado ingresado es válido
                        for (Nodo nodo : estados) {
                            if (nodo.getNombre().equals(estadoTemp)) {
                                automata.agregarTransicion(estados[i], alfabeto[j], nodo);
                                System.out.println("Transición agregada");
                                aceptaEstado = true;
                                break;
                            }
                        }
                    }
    
                    if (!aceptaEstado) {
                        System.out.println("Estado inválido. Por favor, ingrese un estado válido o 'null'.");
                    }
                } while (!aceptaEstado);
            }
        }
    }
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ingresar número de estados
        int nEstados;
        do {
            System.out.println("Ingrese el número de estados: ");
            nEstados = scanner.nextInt();
        } while (nEstados <= 0);

        // Crear nodos
        Nodo[] estados = new Nodo[nEstados];
        for (int i = 0; i < nEstados; i++) {
            estados[i] = new Nodo("q" + i);
            System.out.println("Estado q" + i + " creado");
        }

        // Ingresar alfabeto
        System.out.println("Ingrese el alfabeto (ejemplo: 01)");
        String alfabetoStr = scanner.next();
        char[] alfabeto = alfabetoStr.replaceAll("\\s","").toCharArray();

        // Definir estados inicial y finales
        Nodo estadoInicial = estados[0];
        List<Nodo> estadosFinales = new ArrayList<>();
        do {
            System.out.println("Ingrese los estados de aceptacion separados por coma (ejemplo: q1,q2):");
            String inputFinales = scanner.next();
            String[] finalesArray = inputFinales.split(",");

            for (String nombreFinal : finalesArray) {
                for (Nodo estado : estados) {
                    if (estado.getNombre().equals(nombreFinal)) {
                        estadosFinales.add(estado);
                        break;
                    }
                }
            }

            // Verificar si se encontraron estados finales
            if (estadosFinales.isEmpty()) {
                System.out.println("No se encontraron estados finales válidos.");
            } else {
                System.out.println("Estados finales definidos correctamente.");
                
            }
        } while (estadosFinales.isEmpty());


        // Crear autómata
        Automata automata = new Automata(alfabeto, estados, estadoInicial, estadosFinales);

        // Definir la tabla de transiciones
        definirTablaTransiciones(automata, scanner);

        boolean repetirCadena = true;
        do {
        // Verificar cadenas
        System.out.println("Ingrese la cadena a verificar:");
        String cadena = scanner.next();
        System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));
        System.out.println("Desea ingresar otra cadena? s/n");
        String repetir = scanner.next();
        if(repetir.equals("s")){
            continue;
        }else{
            repetirCadena = false;
        }
        } while (repetirCadena);
        
        // Cerrar el Scanner
        scanner.close();
    }
}
