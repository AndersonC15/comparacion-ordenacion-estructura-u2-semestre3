package ed.u2;

import ed.u2.generador.GeneradorDatasets;
import ed.u2.benchmark.Benchmark;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando generación de datasets...");
        GeneradorDatasets gen = new GeneradorDatasets();
        gen.generarTodos(); // genera los 4 CSV en resources/datasets/
        System.out.println("Datasets generados en: " + Config.RUTA_DATASETS);

        System.out.println("\nIniciando benchmarks...");
        Benchmark benchmark = new Benchmark();
        benchmark.ejecutarTodos(); // carga y evalúa los 3 algoritmos sobre los datasets
        System.out.println("Benchmark finalizado.");
    }
}
