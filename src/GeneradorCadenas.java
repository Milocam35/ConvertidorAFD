import java.util.*;

public class GeneradorCadenas {

    public static Set<String> generarCadenas(Map<String, List<List<String>>> gramatica, String simboloInicial) {
        Set<String> cadenasGeneradas = new HashSet<>();
        int iteracionesStagnadas = 0;

        while (cadenasGeneradas.size() < 10) {
            String cadena = "";
            Deque<Pair<String, String>> stack = new ArrayDeque<>();
            stack.push(new Pair<>(simboloInicial, ""));

            Set<String> nuevasCadenas = new HashSet<>();

            while (!stack.isEmpty()) {
                Pair<String, String> pair = stack.pop();
                String simbolo = pair.getKey();
                String cadenaActual = pair.getValue();

                if (!gramatica.containsKey(simbolo)) {
                    cadenaActual += simbolo;
                    cadena += cadenaActual;
                } else {
                    List<List<String>> producciones = gramatica.get(simbolo);
                    List<String> produccionElegida = producciones.get(new Random().nextInt(producciones.size()));
                    for (int i = produccionElegida.size() - 1; i >= 0; i--) {
                        stack.push(new Pair<>(produccionElegida.get(i), cadenaActual));
                    }
                }
            }

            if (!cadenasGeneradas.contains(cadena)) {
                cadenasGeneradas.add(cadena);
                nuevasCadenas.add(cadena);
                iteracionesStagnadas = 0;
            } else {
                iteracionesStagnadas++;
            }

            if (iteracionesStagnadas > 100) {
                break;
            }
        }

        return cadenasGeneradas;
    }

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);
        Map<String, List<List<String>>> gramatica = new HashMap<>();

        while (true) {
            System.out.println("Introduce los estados no terminales de tu gramática, En mayusculas y separados por comas (Ejemplo: S,A,B):");
            String[] noTerminalesInput = leer.nextLine().split(",");
            Set<String> noTerminalesSet = new HashSet<>();

            // Convertir los no terminales ingresados a mayúsculas y verificar duplicados
            boolean terminalesDuplicados = false;
            for (String noTerminalInput : noTerminalesInput) {
                String noTerminal = noTerminalInput.trim().toUpperCase(); // Convertir a mayúsculas
                if (!noTerminalesSet.add(noTerminal)) {
                    System.out.println("Error: No se pueden ingresar no terminales duplicados.");
                    terminalesDuplicados = true;
                    break;
                }
            }

            if (!terminalesDuplicados) {
                for (String noTerminal : noTerminalesSet) {
                    System.out.println("\nProducciones para el estado no terminal " + noTerminal + ":");
                    List<List<String>> producciones = new ArrayList<>();
                    while (true) {
                        System.out.print("Ingrese una producción o ingrese 'null' para agregar una producción vacía (deje vacío para terminar): ");
                        String produccionInput = leer.nextLine();
                        if (produccionInput.isEmpty()) {
                            System.out.println("Producciones para el estado " + noTerminal + " completadas.\n");
                            break;
                        }
                        List<String> produccion = Arrays.asList(produccionInput.split(","));
                        producciones.add(produccion);
                        System.out.println("Producción agregada correctamente.");
                    }
                    gramatica.put(noTerminal, producciones);
                }

                String simboloInicial;
                while (true) {
                    System.out.println("\nIngrese el símbolo inicial:");
                    simboloInicial = leer.nextLine().trim().toUpperCase();
                    if (gramatica.containsKey(simboloInicial)) {
                        break;
                    }
                    System.out.println("El símbolo inicial no está en la lista de no terminales. Inténtelo de nuevo.");
                }

                Set<String> cadenasGeneradas = generarCadenas(gramatica, simboloInicial);
                System.out.println("\nCadenas generadas:");
                for (String cadena : cadenasGeneradas) {
                    System.out.println(cadena);
                }
                leer.close();
                return;
            }
        }
    }
}
