package ed.u2.ordenacion;

/**
 * Selection sort instrumentado.
 */
public final class SelectionSort {

    public static void sort(int[] arreglo, Contador contador, boolean trazar) {
        if (arreglo == null || arreglo.length <= 1) return;

        if (trazar) System.out.println("=== SELECTION SORT ===");

        for (int posicionActual = 0; posicionActual < arreglo.length - 1; posicionActual++) {
            int indiceMinimo = posicionActual;
            if (trazar) {
                System.out.println("---- Iteración posicionActual=" + posicionActual + " ----");
            }
            for (int posicionBusqueda = posicionActual + 1; posicionBusqueda < arreglo.length; posicionBusqueda++) {
                contador.incrementarComparaciones();
                if (trazar) {
                    System.out.println("Comparando arreglo[" + posicionBusqueda + "]=" + arreglo[posicionBusqueda] +
                            " con arreglo[" + indiceMinimo + "]=" + arreglo[indiceMinimo]);
                }
                if (arreglo[posicionBusqueda] < arreglo[indiceMinimo]) {
                    indiceMinimo = posicionBusqueda;
                    if (trazar) System.out.println("→ Nuevo mínimo en " + posicionBusqueda);
                }
            }
            if (indiceMinimo != posicionActual) {
                int temporal = arreglo[posicionActual];
                arreglo[posicionActual] = arreglo[indiceMinimo];
                arreglo[indiceMinimo] = temporal;
                contador.incrementarIntercambios();
                if (trazar) {
                    System.out.println("Intercambio entre posiciones " + posicionActual + " y " + indiceMinimo);
                    imprimirArreglo(arreglo);
                }
            }
        }
    }

    private static void imprimirArreglo(int[] arreglo) {
        System.out.print("[ ");
        for (int numero : arreglo) System.out.print(numero + " ");
        System.out.println("]");
    }
}
