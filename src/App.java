import java.util.*;

public class App {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        /* 
        char[] alfabeto = {'a','b','c'};
        Nodo[] estados = new Nodo[6];
        for (int i = 0; i < 6; i++) {
            estados[i] = new Nodo("q" + i);
            System.out.println("Estado q" + i + " creado");
        }
        Nodo estadoInicial = estados[0];
        List<Nodo> estadosFinales = new ArrayList<>(); 
        estadosFinales.add(estados[3]);
        estadosFinales.add(estados[5]);

        AFN automata = new AFN(alfabeto, estados, estadoInicial, estadosFinales);
        Set<Nodo> estadosDestino = new HashSet<>();
        estadosDestino.add(estados[1]);
        estadosDestino.add(estados[4]);
        automata.agregarTransicionAFND(estados[0], alfabeto[0], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[0], alfabeto[1], estadosDestino);
        automata.agregarTransicionAFND(estados[0], alfabeto[2], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[1], alfabeto[0], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(estados[2]);
        automata.agregarTransicionAFND(estados[1], alfabeto[1], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[1], alfabeto[2], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[2], alfabeto[0], estadosDestino);
        automata.agregarTransicionAFND(estados[2], alfabeto[1], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(estados[3]);
        automata.agregarTransicionAFND(estados[2], alfabeto[2], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[3], alfabeto[0], estadosDestino);
        automata.agregarTransicionAFND(estados[3], alfabeto[1], estadosDestino);
        automata.agregarTransicionAFND(estados[3], alfabeto[2], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[4], alfabeto[0], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(estados[5]);
        automata.agregarTransicionAFND(estados[4], alfabeto[1], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[4], alfabeto[2], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(null);
        automata.agregarTransicionAFND(estados[5], alfabeto[0], estadosDestino);
        automata.agregarTransicionAFND(estados[5], alfabeto[1], estadosDestino);
        estadosDestino = new HashSet<>();
        estadosDestino.add(estados[4]);
        automata.agregarTransicionAFND(estados[5], alfabeto[2], estadosDestino);


        System.out.println(automata.verificarCadenaAFND("abc"));

        automata.mostrarTablaTransicionesAFND();
        AFD afd = convertirAFNDaAFD(automata);
        afd.mostrarTablaTransicionesAFD();
        System.out.println(afd.verificarCadenaAFD("abc"));*/
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

        boolean repetirCadena = true;
        boolean repetirEleccion = true;

        do {
            // Definir la tabla de transiciones
            System.out.println("¿Qué clase de autómata desea crear? AFD, AFND o AFNDL?");
            String clasificacionAutomata = scanner.next();
            if (clasificacionAutomata.equals("AFD")) {
                System.out.println("AUTOMATA FINITO DETERMINISTA:");
                AFD afd = new AFD(alfabeto, estados, estadoInicial, estadosFinales);
                definirTablaTransiciones(afd, scanner);
                verificarCadenaD(afd, repetirCadena);
                afd.mostrarTablaTransicionesAFD();
                repetirEleccion = false;
            } else if (clasificacionAutomata.equals("AFND")) {
                System.out.println("AUTOMATA FINITO NO DETERMINISTA:");
                AFN afn = new AFN(alfabeto, estados, estadoInicial, estadosFinales);
                definirTablaDeTrancisionesAFND(afn, scanner);
                verificarCadenaN(afn, repetirCadena);
                afn.mostrarTablaTransicionesAFND();
                AFD afd = convertirAFNDaAFD(afn);
                System.out.println("Nuevos estados finales: " + afd.getEstadosFinales());
                afd.mostrarTablaTransicionesAFD();
                verificarCadenaD(afd, repetirCadena);
                repetirEleccion = false;
            } else if (clasificacionAutomata.equals("AFNDL")) {
                System.out.println("AUTOMATA FINITO NO DETERMINISTA LAMBDA:");
                AFN afn = new AFN(alfabeto, estados, estadoInicial, estadosFinales);
                definirTablaDeTrancisionesLambda(afn, scanner);
                do {
                    // Verificar cadenas
                    System.out.println("Ingrese la cadena a verificar:");
                    String cadena = scanner.next();
                    boolean cadenaValida = afn.verificarCadenaAFNDLambda(cadena);
                    System.out.println("La cadena es aceptada por el AFNDL: " + cadenaValida);
                    if (!cadenaValida) {
                        afn.setVida(false);
                        System.out.println("El automata perdio su vida!!");
                    }
                    System.out.println("Desea ingresar otra cadena? s/n");
                    String repetir = scanner.next();

                    if (repetir.equals("s")) {
                        continue;
                    } else {
                        repetirCadena = false;
                    }
                } while (repetirCadena);
            } else {
                System.out.println("Ingrese un autómata válido");
                repetirEleccion = true;
            }
        } while (repetirEleccion);
        // Mostrar el resultado al final del programa
        /*if (automata.isVida()) {
            System.out.println("¡Felicidades! Ha completado todas las cadenas correctamente. WIN!");
        } else {
            System.out.println("GAME OVER. Ha perdido una vida.");
        }*/

        // Cerrar el Scanner
        scanner.close();
    }

    public static void mostrarGameOver(Automata automata){
        if (automata.isVida()) {
            System.out.println("¡Felicidades! Ha completado todas las cadenas correctamente. WIN!");
        } else {
            System.out.println("GAME OVER. Ha perdido una vida.");
        }
    }

    public static void verificarCadenaN(AFN automata, boolean repetirCadena){
        do {
            // Verificar cadenas
            System.out.println("Ingrese la cadena a verificar:");
            String cadena = scanner.next();
            System.out.println(cadena);
            if(cadena==null){
                cadena="";
            }
            boolean cadenaValida = automata.verificarCadenaAFND(cadena);
            System.out.println("La cadena es aceptada por el AFND: " + cadenaValida);
            if (!cadenaValida) {
                automata.setVida(false);
                System.out.println("El automata perdio su vida!!");
            }
            System.out.println("Desea ingresar otra cadena? s/n");
            String repetir = scanner.next();

            if (repetir.equals("s")) {
                continue;
            } else {
                repetirCadena = false;
            }
        } while (repetirCadena);
    }

    public static void verificarCadenaD(AFD automata, boolean repetirCadena){
        do {
            // Verificar cadenas
            System.out.println("Ingrese la cadena a verificar:");
            String cadena = scanner.next();
            boolean cadenaValida = automata.verificarCadenaAFD(cadena);
            System.out.println("La cadena es aceptada por el AFD: " + cadenaValida);
            if (!cadenaValida) {
                automata.setVida(false);
                System.out.println("El automata perdio su vida!!");
            }
            System.out.println("Desea ingresar otra cadena? s/n");
            String repetir = scanner.next();
            if (repetir.equals("s")) {
                continue;
            } else if (repetir.equals("n")) {
                repetirCadena = false;
            } else {
                repetirCadena = true;
            }
        } while (repetirCadena);
    }

    public static void definirTablaDeTrancisionesLambda(AFN automata, Scanner sc) {
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

    public static void solicitarTransicion(AFN automata, Scanner sc, Nodo origen, char simbolo) {
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

    public static void solicitarTransicionEpsilon(AFN automata, Scanner sc, Nodo origen) {
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

    public static void definirTablaTransiciones(AFD automata, Scanner sc) {
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

    public static void definirTablaDeTrancisionesAFND(AFN automata, Scanner sc) {
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

    public static AFD convertirAFNDaAFD(AFN afnd) {
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
        List<Nodo> nuevosEstadosAFD = generarEstadosAFD(estadosAFND, alfabeto, matrizTransiciones);
    
        // Construir el objeto Automata correspondiente al AFD
        AFD afd = construirAutomataAFD(alfabeto, nuevosEstadosAFD, afnd.getEstadoInicial(), estadosFinalesAFND, matrizTransiciones, estadosAFND);
    
        return afd;
    }
    
    private static List<Nodo> generarEstadosAFD(Nodo[] estadosAFND, char[] alfabeto, String[][] matrizTransiciones) {
        List<Nodo> nuevosEstadosAFD = new ArrayList<>();
    
        // Agregar todos los estados del AFND al conjunto de nuevos estados del AFD
        for (Nodo estado : estadosAFND) {
            nuevosEstadosAFD.add(estado);
        }
    
        // Generar los nuevos estados del AFD a partir de la matriz de transiciones
        for (String[] fila : matrizTransiciones) {
            for (String transicion : fila) {
                if (!transicion.equals("-") && !transicion.isEmpty()) { // Verificar si la cadena no está vacía
                    String[] nombresEstados = transicion.split(",");
                    // Verificar si ya existe un estado con estos nombres
                    boolean encontrado = false;
                    for (Nodo estado : nuevosEstadosAFD) {
                        if (estado.getNombre().equals(String.join(",", nombresEstados))) {
                            encontrado = true;
                            break;
                        }
                    }
                    // Si no se encuentra un estado con estos nombres, agregar uno nuevo
                    if (!encontrado) {
                        nuevosEstadosAFD.add(new Nodo(String.join(",", nombresEstados)));
                    }
                }
            }
        }
    
        return nuevosEstadosAFD;
    }
    
    private static AFD construirAutomataAFD(char[] alfabeto, List<Nodo> nuevosEstadosAFD, Nodo estadoInicialAFD, List<Nodo> nuevosEstadosFinalesAFD, String[][] matrizTransiciones, Nodo[] estadosAFND) {
        AFD afd = new AFD(alfabeto, nuevosEstadosAFD.toArray(new Nodo[0]), estadoInicialAFD, nuevosEstadosFinalesAFD);
        // Actualizar la matriz de transiciones del AFD para incluir todas las combinaciones posibles
        String[][] matrizTransicionesAFD = new String[nuevosEstadosAFD.size()][alfabeto.length];
        for (int i = 0; i < nuevosEstadosAFD.size(); i++) {
            for (int j = 0; j < alfabeto.length; j++) {
                Nodo estadoActual = nuevosEstadosAFD.get(i);
                String transicion = obtenerTransicion(estadoActual, alfabeto[j], estadosAFND, alfabeto, matrizTransiciones);
                matrizTransicionesAFD[i][j] = transicion;

                // Verificar si la transición no existe en la lista de nuevos estados AFD y agregarla como un nuevo estado
                // Verificar si la transición no existe en la lista de nuevos estados AFD y agregarla como un nuevo estado
            if (!existeEstadoEnLista(nuevosEstadosAFD, new Nodo(transicion))) {
                // Crear un nuevo estado y agregarlo a la lista de nuevos estados AFD
                Nodo nuevoEstado = new Nodo(transicion);
                nuevosEstadosAFD.add(nuevoEstado);

                // Incrementar el tamaño de la matriz para acomodar el nuevo estado
                matrizTransicionesAFD = ajustarTamanioMatriz(matrizTransicionesAFD, nuevosEstadosAFD.size(), alfabeto.length);
            }
            }
        }



        // Imprimir la matriz de transiciones del AFD
        /*System.out.println("Matriz de Transiciones del AFD:");
        for (int i = 0; i < matrizTransicionesAFD.length; i++) {
            for (int j = 0; j < matrizTransicionesAFD[i].length; j++) {
                System.out.print(matrizTransicionesAFD[i][j] + "\t");
            }
            System.out.println();
        }*/



        afd.setEstados(nuevosEstadosAFD.toArray(new Nodo[0]));
        List<Nodo> estadosAlcanzables = new ArrayList<>();
        transicionesAlcanzables(afd, matrizTransicionesAFD, estadosAlcanzables);
        actualizarEstadosFinales(estadosAlcanzables, nuevosEstadosFinalesAFD);
        afd.setEstados(estadosAlcanzables.toArray(new Nodo[0]));

        
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
    private static boolean existeEstadoEnLista(List<Nodo> lista, Nodo estado) {
        for (Nodo nodo : lista) {
            if (nodo.getNombre().equals(estado.getNombre())) {
                return true;
            }
        }
        return false;
    }

    private static String[][] ajustarTamanioMatriz(String[][] matriz, int filas, int columnas) {
        String[][] nuevaMatriz = new String[filas][columnas];
        for (int i = 0; i < matriz.length; i++) {
            System.arraycopy(matriz[i], 0, nuevaMatriz[i], 0, matriz[i].length);
        }
        return nuevaMatriz;
    }
    

    private static void transicionesAlcanzables(Automata afd, String[][] matrizTransicionesAFD, List<Nodo> estadosAlcanzables) {
        List<Nodo> estadosAFD = Arrays.asList(afd.getEstados());
    
        // Agregar el estado inicial
        estadosAlcanzables.add(afd.getEstadoInicial());
    
        // Verificar qué estados del AFD tienen transiciones entrantes
        for (Nodo estado : estadosAFD) {
            if (estado.equals(afd.getEstadoInicial())) {
                continue; // Saltar el estado inicial, ya que siempre es alcanzable
            }
            boolean alcanzable = false;
            for (int i = 0; i < estadosAFD.size(); i++) {
                for (int j = 0; j < afd.getAlfabeto().length; j++) {
                    String transicion = matrizTransicionesAFD[i][j];
                    if (transicion != null && transicion.equals(estado.getNombre())) {
                        alcanzable = true;
                        break;
                    }
                }
                if (alcanzable) {
                    break;
                }
            }
            if (alcanzable) {
                estadosAlcanzables.add(estado);
            }
        }
    }

    

    private static void actualizarEstadosFinales(List<Nodo> nuevosEstadosAFD, List<Nodo> estadosFinalesAFND) {
        // Crear un conjunto para almacenar los nombres de los estados finales únicos
        Set<String> nombresEstadosFinales = new HashSet<>();
    
        // Agregar los nombres de los estados finales originales al conjunto
        for (Nodo estadoFinalAFND : estadosFinalesAFND) {
            nombresEstadosFinales.add(estadoFinalAFND.getNombre());
        }
    
        // Agregar nuevos estados finales y eliminar los que no están presentes
        for (Nodo estadoAFD : nuevosEstadosAFD) {
            // Verificar si el nombre del estado del AFD contiene el nombre del estado final original
            if (estadoAFD.getNombre().contains(estadosFinalesAFND.get(0).getNombre())) {
                // Si el estado no se ha agregado previamente, marcar este estado del AFD como un nuevo estado final
                if (!nombresEstadosFinales.contains(estadoAFD.getNombre())) {
                    estadosFinalesAFND.add(estadoAFD);
                    nombresEstadosFinales.add(estadoAFD.getNombre()); // Agregar el nombre del estado al conjunto para evitar duplicaciones
                }
            }
        }
    
        // Eliminar los estados finales que no están presentes en la lista de nuevos estados AFD
        for (int i = 0; i < estadosFinalesAFND.size(); i++) {
            Nodo estadoFinal = estadosFinalesAFND.get(i);
            if (!nuevosEstadosAFD.contains(estadoFinal)) {
                estadosFinalesAFND.remove(estadoFinal);
                i--; // Ajustar el índice después de eliminar un elemento
            }
        }
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
        Set<String> transicionesAgregadas = new HashSet<>(); // Para evitar transiciones duplicadas
        StringBuilder transicion = new StringBuilder();
        
        for (String estadoIndividual : estadosIndividuales) {
            estadoIndividual = estadoIndividual.trim(); // Eliminar espacios en blanco adicionales
            if (!estadoIndividual.isEmpty()) { // Verificar si el estado individual no está vacío
                for (int i = 0; i < estadosAFND.length; i++) {
                    if (estadosAFND[i].getNombre().equals(estadoIndividual)) {
                        for (int j = 0; j < alfabeto.length; j++) {
                            if (alfabeto[j] == simbolo) {
                                String transicionActual = matrizTransiciones[i][j];
                                if (!transicionActual.equals("null")) {
                                    // Verificar si la transición actual ya ha sido agregada
                                    if (!transicionesAgregadas.contains(transicionActual)) {
                                        // Verificar si la transición actual contiene un nombre de estado válido
                                        if (!transicionActual.isEmpty() && !transicionActual.equals(",")) {
                                            if (transicion.length() > 0) {
                                                transicion.append(",");
                                            }
                                            transicion.append(transicionActual);
                                            transicionesAgregadas.add(transicionActual); // Agregar la transición a las ya agregadas
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return transicion.toString();
    }
    
    
    
    
    
}