package ed.u2.ordenacion;

public class Contador {

    private long comparaciones = 0;
    private long intercambios = 0;

    public void incrementarComparaciones() {
        comparaciones++;
    }

    public void incrementarIntercambios() {
        intercambios++;
    }

    public long getComparaciones() { return comparaciones; }
    public long getIntercambios() { return intercambios; }
}
