package ed.u2.ordenacion;

import java.util.Comparator;
import java.util.List;

public class InsertionSort<T> implements Ordenador<T> {

    @Override
    public void ordenar(List<T> lista, Comparator<T> comp, Contador contador) {

        for (int i = 1; i < lista.size(); i++) {

            T valor = lista.get(i);
            int j = i - 1;

            while (j >= 0) {

                contador.incrementarComparaciones();

                if (comp.compare(lista.get(j), valor) > 0) {
                    lista.set(j + 1, lista.get(j));
                    contador.incrementarIntercambios();
                    j--;
                } else break;
            }

            lista.set(j + 1, valor);
        }
    }
}
