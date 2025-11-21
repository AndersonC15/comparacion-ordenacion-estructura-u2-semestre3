package ed.u2.ordenacion;

import java.util.Comparator;
import java.util.List;

public interface Ordenador<T> {
    void ordenar(List<T> lista, Comparator<T> comparador, Contador contador);
}
