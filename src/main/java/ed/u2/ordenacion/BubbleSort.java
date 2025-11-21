package ed.u2.ordenacion;

import java.util.Comparator;
import java.util.List;

public class BubbleSort<T> implements Ordenador<T> {

    @Override
    public void ordenar(List<T> lista, Comparator<T> comp, Contador contador) {

        int n = lista.size();

        for (int i = 0; i < n - 1; i++) {

            boolean huboIntercambio = false;

            for (int j = 0; j < n - 1 - i; j++) {

                contador.incrementarComparaciones();

                if (comp.compare(lista.get(j), lista.get(j + 1)) > 0) {

                    T temp = lista.get(j);
                    lista.set(j, lista.get(j + 1));
                    lista.set(j + 1, temp);

                    contador.incrementarIntercambios();
                    huboIntercambio = true;
                }
            }

            if (!huboIntercambio) break;
        }
    }
}
