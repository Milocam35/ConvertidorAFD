import java.util.*;

public class App {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

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
        char[] alfabeto = ingresarAlfabeto(scanner);

        // Definir estados inicial y finales
        Nodo estadoInicial = estados[0];
        
        List<Nodo> estadosFinales = ingEstadosFinales(estados);

        // Crear autómata
        Automata automata = new Automata(alfabeto, estados, estadoInicial, estadosFinales);

        boolean repetirCadena = true;
        // Definir la tabla de transiciones
        System.out.println("Que clase de automata desea crear? AFD o AFND?");
        String clasificacionAutomata = scanner.next();

        if(clasificacionAutomata.equals("AFD")){
            System.out.println("AUTOMATA FINITO DETERMINISTA:");
            definirTablaTransiciones(automata, scanner);
            do {
                // Verificar cadenas
                System.out.println("Ingrese la cadena a verificar:");
                String cadena = scanner.next();
                //System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));
                System.out.println("La cadena es aceptada por el AFD: " + automata.verificarCadena(cadena));
                System.out.println("Desea ingresar otra cadena? s/n");
                String repetir = scanner.next();
                if(repetir.equals("s")){
                continue;
                }else{
                    repetirCadena = false;
                }
            } while (repetirCadena);
        }else if(clasificacionAutomata.equals("AFND")){
            System.out.println("AUTOMATA FINITO NO DETERMINISTA:");
            definirTablaDeTrancisionesAFND(automata, scanner);
            do {
                // Verificar cadenas
                System.out.println("Ingrese la cadena a verificar:");
                String cadena = scanner.next();
                //System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));
                System.out.println("La cadena es aceptada por el AFND: " + automata.verificarCadenaAFND(cadena));
                System.out.println("Desea ingresar otra cadena? s/n");
                String repetir = scanner.next();
                if(repetir.equals("s")){
                continue;
                }else{
                    repetirCadena = false;
                }
            } while (repetirCadena);
        }
        
        // Cerrar el Scanner
        scanner.close();
    }

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
                        automata.agregarTransicionAFD(estados[i], alfabeto[j], null);
                        System.out.println("Transición nula agregada");
                        aceptaEstado = true;
                    } else {
                        // Verificar si el estado ingresado es válido
                        for (Nodo nodo : estados) {
                            if (nodo.getNombre().equals(estadoTemp)) {
                                automata.agregarTransicionAFD(estados[i], alfabeto[j], nodo);
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

    public static void definirTablaDeTrancisionesAFND(Automata automata, Scanner sc) {
        Nodo[] estados = automata.getEstados();
        char[] alfabeto = automata.getAlfabeto();
    
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                boolean aceptaEstado = false; // Inicializar como true antes del bucle
    
                String[] estadosTemp;
                System.out.println("Puede ingresar los estados separados por comas, ejemplo: (q0,q1)");
                do {
                    System.out.print(estados[i].getNombre() + " -> " + alfabeto[j] + ": ");
                    String input = sc.nextLine();
                    estadosTemp = input.split(",");
    
                    Set<Nodo> estadosDestino = new HashSet<>();
                    for (String estadoNombre : estadosTemp) {
                        if (estadoNombre.equalsIgnoreCase("null")) {
                            estadosDestino.add(null);
                            aceptaEstado = true;
                            break;
                        }
                        for (Nodo nodo : estados) {
                            if (nodo.getNombre().equals(estadoNombre)) {
                                estadosDestino.add(nodo);
                                aceptaEstado = true;
                                break;
                            }
                        }
                    }
    
                    if (aceptaEstado) {
                        automata.agregarTransicionAFND(estados[i], alfabeto[j], estadosDestino);
                        System.out.println("Transición agregada");
                    }else{
                        System.out.println("Estados inválidos. Por favor, ingrese estados válidos separados por coma o 'null'."); 
                    }
    
                } while (!aceptaEstado);
            }
        }
    }

    public static char[] ingresarAlfabeto(Scanner scanner) {
        char[] alfabeto;
    
        while (true) {
            System.out.println("Ingrese el alfabeto separado por comas (ejemplo: a,b,c):");
            String alfabetoInput = scanner.next();
            String[] caracteres = alfabetoInput.split(",");
            alfabeto = new char[caracteres.length];
    
            // Verificar si hay caracteres repetidos
            boolean caracteresRepetidos = false;
            Set<Character> conjuntoCaracteres = new HashSet<>();
            for (int i = 0; i < caracteres.length; i++) {
                if (caracteres[i].length() != 1 || !conjuntoCaracteres.add(caracteres[i].charAt(0))) {
                    caracteresRepetidos = true;
                    break;
                }
                alfabeto[i] = caracteres[i].charAt(0);
            }
    
            if (caracteresRepetidos) {
                System.out.println("¡Error! El alfabeto no puede contener caracteres repetidos o inválidos. Intente de nuevo.");
            } else {
                break; // Salir del bucle si no hay caracteres repetidos
            }
        }
    
        return alfabeto;
    }
    
    public static List<Nodo> ingEstadosFinales(Nodo[] estados){
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
        return estadosFinales;
    }
    
    
}
