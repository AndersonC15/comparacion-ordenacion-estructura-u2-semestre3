package ed.u2.ordenacion;

/**
 * Insertion sort instrumentado.
 */
public final class InsertionSort {

    public static void sort(int[] arreglo, Contador contador, boolean trazar) {
        if (arreglo == null || arreglo.length <= 1) return;

        if (trazar) System.out.println("=== INSERTION SORT ===");

        for (int indiceActual = 1; indiceActual < arreglo.length; indiceActual++) {
            int valorAInsertar = arreglo[indiceActual];
            int indiceComparacion = indiceActual - 1;

            if (trazar) {
                System.out.println("---- Iteración indiceActual =" + indiceActual + " ----");
                System.out.println("Valor a insertar: " + valorAInsertar);
            }

            while (indiceComparacion >= 0) {
                contador.incrementarComparaciones();
                if (arreglo[indiceComparacion] > valorAInsertar) {
                    arreglo[indiceComparacion + 1] = arreglo[indiceComparacion];
                    contador.incrementarIntercambios(); // contar movimiento como intercambio lógico
                    if (trazar) {
                        System.out.println("Mover arreglo[" + indiceComparacion + "] -> arreglo[" + (indiceComparacion + 1) + "]");
                        imprimirArreglo(arreglo);
                    }
                    indiceComparacion--;
                } else {
                    break;
                }
            }
            arreglo[indiceComparacion + 1] = valorAInsertar;
            // insertar contaría como movimiento también
            contador.incrementarIntercambios();
            if (trazar) {
                System.out.println("Insertar valor en posición " + (indiceComparacion + 1));
                imprimirArreglo(arreglo);
            }
        }
    }

    private static void imprimirArreglo(int[] arreglo) {
        System.out.print("[ ");
        for (int numero : arreglo) System.out.print(numero + " ");
        System.out.println("]");
    }
}

