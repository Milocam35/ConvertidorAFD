# Automata Finito Determinista (AFD) en Java
Este proyecto implementa un autómata finito determinista (AFD) en Java, permitiendo al usuario definir el alfabeto, los estados del autómata, el estado inicial, los estados finales y la tabla de transiciones. Luego, el programa puede verificar si una cadena dada es aceptada por el autómata.

## Funcionalidades
Permite al usuario definir el alfabeto, estados, estado inicial y estados finales del autómata.
Verifica cadenas contra el autómata para determinar si se aceptan o rechazan.
Convierte autómatas finitos no deterministas (NFA) en autómatas finitos deterministas (DFA).

- Definir el alfabeto del autómata.
- Ingresar los estados del autómata.
- Establecer el estado inicial y los estados finales del autómata.
- Crear la tabla de transiciones del autómata.
- Verificar si una cadena dada es aceptada por el autómata.
- Si el automata es no determinista, lo transforma a determinista e imprime su tabla de transiciones
- Si el automata es no determinista con lambda, lo transforma a no determinista, imprime su tabla de transiciones y luego transforma el nuevo AFN a un AFD comun e imprime su tabla de transiciones.

- La clase App es la clase principal del programa. Proporciona una interfaz de usuario para crear e interactuar con autómatas finitos. La clase permite al usuario definir el alfabeto, los estados, el estado inicial y los estados finales del autómata. También proporciona métodos para verificar cadenas contra el autómata, convertir autómatas finitos no deterministas (NFA) en autómatas finitos deterministas (DFA) y convertir automatas finitos no deterministas con transiciones lambda (NFA-Lambda) a un automata finito determinista (DFA).

## Avances
- Definicion de transiciones nulas entre estados
- Realizacion de un automata finito no determinista
- Realizacion de automata finito no determinista con transiciones lambda
- Conversion de AFD a AFND
- Definir transiciones compuestas durante la transformacion de AFN a AFD
- Conversion de AFN con lambda a AFN

