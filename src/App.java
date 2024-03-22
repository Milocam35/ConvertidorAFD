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


        
        boolean repetirEleccion = true;
        do {
            // Definir la tabla de transiciones
            System.out.println("Que clase de automata desea crear? AFD, AFND p AFNDL?");
            String clasificacionAutomata = scanner.next();
            if(clasificacionAutomata.equals("AFD")){
                System.out.println("AUTOMATA FINITO DETERMINISTA:");
                definirTablaTransiciones(automata, scanner);
                do {
                    // Verificar cadenas
                    System.out.println("Ingrese la cadena a verificar:");
                    String cadena = scanner.next();
                    //System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));
                    System.out.println("La cadena es aceptada por el AFD: " + automata.verificarCadenaAFD(cadena));
                    System.out.println("Desea ingresar otra cadena? s/n");
                    String repetir = scanner.next();
                    if(repetir.equals("s")){
                    continue;
                    }else if(repetir.equals("n")){
                        repetirCadena = false;
                    }else{
                        repetirCadena = true;
                    }
                } while (repetirCadena);
                automata.mostrarTablaTransicionesAFD();
                repetirEleccion = false;
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
                automata.mostrarTablaTransicionesAFND();
                automata = convertirAFNDaAFD(automata);
                automata.mostrarTablaTransicionesAFD();
                repetirEleccion = false;
            }else if(clasificacionAutomata.equals("AFNDL")){
                System.out.println("AUTOMATA FINITO NO DETERMINISTA LAMBDA:");
                definirTablaDeTrancisionesLambda(automata, scanner);
                do {
                    // Verificar cadenas
                    System.out.println("Ingrese la cadena a verificar:");
                    String cadena = scanner.next();
                    //System.out.println("La cadena es aceptada por el autómata: " + automata.verificarCadena(cadena));
                    System.out.println("La cadena es aceptada por el AFNDL: " + automata.verificarCadenaAFNDLambda(cadena));
                    System.out.println("Desea ingresar otra cadena? s/n");
                    String repetir = scanner.next();
                    
                    if(repetir.equals("s")){
                    continue;
                    }else{
                        repetirCadena = false;
                    }
                } while (repetirCadena);
            }
            else{
                System.out.println("Ingrese un automata valido");
                repetirEleccion = true;
            }
        } while (repetirEleccion);
        
        
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
                String[] estadosTemp;
                boolean aceptaEstado;
                System.out.println("Puede ingresar los estados separados por comas, ejemplo: (q0,q1)");
                System.out.print(estados[i].getNombre() + " -> " + alfabeto[j] + ": ");
                do {
                    aceptaEstado = false;
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
                    } else {
                        if (!estadosDestino.isEmpty()) {
                            System.out.println("Estados inválidos. Por favor, ingrese estados válidos separados por coma o 'null'."); 
                        }
                    }
                } while (!aceptaEstado);
            }
        }
    }
    
    public static void definirTablaDeTrancisionesLambda(Automata automata, Scanner sc) {
        // Obtener los estados del autómata
        Nodo[] estados = automata.getEstados();
        char[] alfabeto = automata.getAlfabeto();
    
        // Solicitar las transiciones entre estados y símbolos del alfabeto
        for (int i = 0; i < estados.length; i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                solicitarTransicion(automata, sc, estados[i], alfabeto[j]);
            }
        }
    
        // Solicitar las transiciones lambda
        for (int i = 0; i < estados.length; i++) {
            solicitarTransicionEpsilon(automata, sc, estados[i]);
        }
    }
    
    public static void solicitarTransicion(Automata automata, Scanner sc, Nodo origen, char simbolo) {
        String[] estadosTemp;
        boolean aceptaEstado;
    
        System.out.println("Puede ingresar los estados separados por comas, ejemplo: (q0,q1)");
        System.out.print(origen.getNombre() + " -> " + simbolo + ": ");
    
        do {
            aceptaEstado = false;
            String input = sc.nextLine();
            estadosTemp = input.split(",");
    
            Set<Nodo> estadosDestino = new HashSet<>();
            for (String estadoNombre : estadosTemp) {
                if (estadoNombre.equalsIgnoreCase("null")) {
                    estadosDestino.add(null);
                    aceptaEstado = true;
                    break;
                }
                for (Nodo nodo : automata.getEstados()) {
                    if (nodo.getNombre().equals(estadoNombre)) {
                        estadosDestino.add(nodo);
                        aceptaEstado = true;
                        break;
                    }
                }
            }
    
            if (aceptaEstado) {
                automata.agregarTransicionAFND(origen, simbolo, estadosDestino);
                System.out.println("Transición agregada");
            } else {
                if (!estadosDestino.isEmpty()) {
                    System.out.println("Estados inválidos. Por favor, ingrese estados válidos separados por coma o 'null'."); 
                }
            }
        } while (!aceptaEstado);
    }
    
    public static void solicitarTransicionEpsilon(Automata automata, Scanner sc, Nodo origen) {
        String[] estadosTemp;
        boolean aceptaEstado;
    
        System.out.println("Puede ingresar los estados de transición lambda separados por comas, ejemplo: (q0,q1)");
        System.out.print(origen.getNombre() + " -> ε: ");
    
        do {
            aceptaEstado = false;
            String input = sc.nextLine();
            estadosTemp = input.split(",");
    
            Set<Nodo> estadosDestino = new HashSet<>();
            for (String estadoNombre : estadosTemp) {
                if (estadoNombre.equalsIgnoreCase("null")) {
                    estadosDestino.add(null);
                    aceptaEstado = true;
                    break;
                }
                for (Nodo nodo : automata.getEstados()) {
                    if (nodo.getNombre().equals(estadoNombre)) {
                        estadosDestino.add(nodo);
                        aceptaEstado = true;
                        break;
                    }
                }
            }
    
            if (aceptaEstado) {
                for (Nodo destino : estadosDestino) {
                    automata.agregarTransicionEpsilon(origen, destino);
                }
                System.out.println("Transición lambda agregada");
            } else {
                if (!estadosDestino.isEmpty()) {
                    System.out.println("Estados inválidos. Por favor, ingrese estados válidos separados por coma o 'null'."); 
                }
            }
        } while (!aceptaEstado);
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

    public static Automata convertirAFNDaAFD(Automata afnd) {
        // Obtener los estados y el alfabeto del AFND
        Nodo[] estadosAFND = afnd.getEstados();
        char[] alfabeto = afnd.getAlfabeto();
        List<Nodo> estadosFinalesAFND = afnd.getEstadosFinales();
    
        // Crear una matriz para almacenar las transiciones entre estados
        String[][] matrizTransiciones = new String[estadosAFND.length][alfabeto.length];
    
        // Llenar la matriz con las transiciones
        for (int i = 0; i < estadosAFND.length; i++) {
            Map<Character, Set<Nodo>> transicionesEstado = afnd.getTransicionesAFND().get(estadosAFND[i]);
            for (int j = 0; j < alfabeto.length; j++) {
                char simbolo = alfabeto[j];
                Set<Nodo> destinos = transicionesEstado.get(simbolo);
                if (destinos != null && !destinos.isEmpty()) {
                    // Concatenar los nombres de los estados de destino
                    StringBuilder destinosConcatenados = new StringBuilder();
                    for (Nodo destino : destinos) {
                        if (destino != null) {
                            destinosConcatenados.append(destino.getNombre()).append(",");
                        }
                    }
                    // Eliminar la última coma
                    if (destinosConcatenados.length() > 0) {
                        destinosConcatenados.deleteCharAt(destinosConcatenados.length() - 1);
                    }                    
                    matrizTransiciones[i][j] = destinosConcatenados.toString();
                } else {
                    matrizTransiciones[i][j] = "-";
                }
            }
        }
    
        // Generar un nuevo conjunto de estados para el AFD
        List<Nodo> nuevosEstadosAFD = generarEstadosAFD(estadosAFND, alfabeto, matrizTransiciones, afnd.getEstadoInicial(), estadosFinalesAFND, afnd.getTransicionesAFND());
    
        // Generar el nuevo conjunto de estados finales para el AFD
        List<Nodo> nuevosEstadosFinalesAFD = generarEstadosFinalesAFD(estadosFinalesAFND, nuevosEstadosAFD);
    
        // Construir el objeto Automata correspondiente al AFD
        Automata afd = construirAutomataAFD(alfabeto, nuevosEstadosAFD, afnd.getEstadoInicial(), nuevosEstadosFinalesAFD, matrizTransiciones, estadosAFND);
        
    
        return afd;
    }
    
    private static List<Nodo> generarEstadosAFD(Nodo[] estadosAFND, char[] alfabeto, String[][] matrizTransiciones, Nodo estadoInicial, List<Nodo> estadosFinales, Map<Nodo, Map<Character, Set<Nodo>>> transicionesAFND) {
        // Realizar una búsqueda en anchura (BFS) para determinar qué estados son alcanzables desde el estado inicial
        Set<Nodo> alcanzables = new HashSet<>();
        Queue<Nodo> cola = new LinkedList<>();
        cola.offer(estadoInicial);
        alcanzables.add(estadoInicial);
        while (!cola.isEmpty()) {
            Nodo estadoActual = cola.poll();
            Map<Character, Set<Nodo>> transicionesEstado = transicionesAFND.get(estadoActual);
            if (transicionesEstado != null) {
                for (Set<Nodo> destinos : transicionesEstado.values()) {
                    for (Nodo estadoDestino : destinos) {
                        if (!alcanzables.contains(estadoDestino)) {
                            cola.offer(estadoDestino);
                            alcanzables.add(estadoDestino);
                        }
                    }
                }
            }
        }
        
        // Generar los nuevos estados del AFD a partir de la matriz de transiciones y eliminar los estados que no son alcanzables
        List<Nodo> nuevosEstadosAFD = new ArrayList<>();
        for (Nodo estado : estadosAFND) {
            if (alcanzables.contains(estado)) {
                nuevosEstadosAFD.add(estado);
            }
        }
        for (String[] fila : matrizTransiciones) {
            for (String transicion : fila) {
                if (!transicion.equals("-") && !transicion.isEmpty()) { // Verificar si la cadena no está vacía
                    String[] nombresEstados = transicion.split(",");
                    // Verificar si ya existe un estado con estos nombres
                    boolean encontrado = false;
                    String nombreEstado = String.join(",", nombresEstados);
                    for (Nodo estado : nuevosEstadosAFD) {
                        if (estado.getNombre().equals(nombreEstado)) {
                            encontrado = true;
                            break;
                        }
                    }
                    // Si no se encuentra un estado con estos nombres y es alcanzable, agregar uno nuevo
                    if (!encontrado && alcanzables.contains(estadoInicial)) {
                        nuevosEstadosAFD.add(new Nodo(nombreEstado));
                    }
                }
            }
        }
        
        // Eliminar estados que no son finales ni alcanzables
        List<Nodo> estadosFiltrados = new ArrayList<>();
        for (Nodo estado : nuevosEstadosAFD) {
            if (alcanzables.contains(estado) || estadosFinales.contains(estado)) {
                estadosFiltrados.add(estado);
            }
        }
    
        return estadosFiltrados;
    }
    
    
    
    
    
    private static List<Nodo> generarEstadosFinalesAFD(List<Nodo> estadosFinalesAFND, List<Nodo> nuevosEstadosAFD) {
        List<Nodo> nuevosEstadosFinalesAFD = new ArrayList<>();
    
        // Verificar qué nuevos estados del AFD contienen estados finales del AFND
        for (Nodo estadoFinalAFND : estadosFinalesAFND) {
            for (Nodo nuevoEstadoAFD : nuevosEstadosAFD) {
                if (nuevoEstadoAFD.getNombre().contains(estadoFinalAFND.getNombre())) {
                    nuevosEstadosFinalesAFD.add(nuevoEstadoAFD);
                    break;
                }
            }
        }
    
        return nuevosEstadosFinalesAFD;
    }
    
    private static String[][] actualizarMatrizTransicionesAFD(List<Nodo> nuevosEstadosAFD, char[] alfabeto, Nodo[] estadosAFND, String[][] matrizTransiciones) {
        String[][] matrizTransicionesAFD = new String[nuevosEstadosAFD.size()][alfabeto.length];
        
        for (int i = 0; i < nuevosEstadosAFD.size(); i++) {
            Nodo estadoActual = nuevosEstadosAFD.get(i);
            for (int j = 0; j < alfabeto.length; j++) {
                char simbolo = alfabeto[j];
                String transicion = obtenerTransicion(estadoActual, simbolo, estadosAFND, alfabeto, matrizTransiciones);
                matrizTransicionesAFD[i][j] = transicion;
            }
        }
        
        return matrizTransicionesAFD;
    }
    
    private static Automata construirAutomataAFD(char[] alfabeto, List<Nodo> nuevosEstadosAFD, Nodo estadoInicialAFD, List<Nodo> nuevosEstadosFinalesAFD, String[][] matrizTransiciones, Nodo[] estadosAFND) {
        Automata afd = new Automata(alfabeto, nuevosEstadosAFD.toArray(new Nodo[0]), estadoInicialAFD, nuevosEstadosFinalesAFD);
        String[][] matrizTransicionesAFD = actualizarMatrizTransicionesAFD(nuevosEstadosAFD, alfabeto, estadosAFND, matrizTransiciones);
        // Generar transiciones válidas para cada estado del AFD
        for (int i = 0; i < matrizTransicionesAFD.length; i++) {
            Nodo estadoOrigen = nuevosEstadosAFD.get(i);
            Map<Character, Nodo> transiciones = new HashMap<>();
            for (int j = 0; j < alfabeto.length; j++) {
                String[] destinos = matrizTransicionesAFD[i][j].split(",");
                StringBuilder estadoDestino = new StringBuilder();
                for (String destino : destinos) {
                    Nodo estado = buscarEstadoPorNombre(nuevosEstadosAFD, destino);
                    if (estado != null) {
                        estadoDestino.append(estado.getNombre()).append(",");
                    }
                }
                if (estadoDestino.length() > 0) {
                    estadoDestino.deleteCharAt(estadoDestino.length() - 1); // Eliminar la última coma
                    transiciones.put(alfabeto[j], buscarEstadoPorNombre(nuevosEstadosAFD, estadoDestino.toString()));
                }
            }
            afd.getTransicionesAFD().put(estadoOrigen, transiciones);
        }
    
        return afd;
    }
    
    
    private static Nodo buscarEstadoPorNombre(List<Nodo> estados, String nombre) {
        for (Nodo estado : estados) {
            if (estado.getNombre().equals(nombre)) {
                return estado;
            }
        }
        return null;
    }

    private static String obtenerTransicion(Nodo estado, char simbolo, Nodo[] estadosAFND, char[] alfabeto, String[][] matrizTransiciones) {
        String nombreEstado = estado.getNombre();
        String[] estadosIndividuales = nombreEstado.split(",");
        StringBuilder transicion = new StringBuilder();
        for (String estadoIndividual : estadosIndividuales) {
            for (int i = 0; i < estadosAFND.length; i++) {
                if (estadosAFND[i].getNombre().equals(estadoIndividual)) {
                    for (int j = 0; j < alfabeto.length; j++) {
                        if (alfabeto[j] == simbolo) {
                            if (!matrizTransiciones[i][j].equals("null")) {
                                if (transicion.length() > 0) {
                                    transicion.append(",");
                                }
                                transicion.append(matrizTransiciones[i][j]);
                            }
                        }
                    }
                }
            }
        }
        return transicion.toString();
    }
}
