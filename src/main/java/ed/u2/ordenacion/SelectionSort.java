package ed.u2.ordenacion;

import java.util.Comparator;
import java.util.List;

public class SelectionSort<T> implements Ordenador<T> {

    @Override
    public void ordenar(List<T> lista, Comparator<T> comp, Contador contador) {

        int n = lista.size();

        for (int i = 0; i < n - 1; i++) {

            int posMin = i;

            for (int j = i + 1; j < n; j++) {

                contador.incrementarComparaciones();

                if (comp.compare(lista.get(j), lista.get(posMin)) < 0) {
                    posMin = j;
                }
            }

            if (posMin != i) {
                T tmp = lista.get(i);
                lista.set(i, lista.get(posMin));
                lista.set(posMin, tmp);

                contador.incrementarIntercambios();
            }
        }
    }
}
