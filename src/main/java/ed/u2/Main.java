package ed.u2;

import ed.u2.generador.GeneradorDatasets;
import ed.u2.benchmark.Benchmark;

public class Main {

    public static void main(String[] args) throws Exception {

        System.out.println("GENERANDO DATASETS...");
        GeneradorDatasets generador = new GeneradorDatasets();
        generador.generarTodos();

        System.out.println("DATASETS GENERADOS.\n");

        System.out.println("EJECUTANDO BENCHMARK...");
        Benchmark bench = new Benchmark();
        bench.ejecutarPruebas();
    }
}
