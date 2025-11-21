package ed.u2.benchmark;

import ed.u2.Config;
import ed.u2.modelo.*;
import ed.u2.ordenacion.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

public class Benchmark {

    public void ejecutarPruebas() throws Exception {

        probarDataset(Config.C100, this::cargarCitas, Comparator.comparing(Cita::apellido));
        probarDataset(Config.C100CO, this::cargarCitas, Comparator.comparing(Cita::apellido));
        probarDataset(Config.P500, this::cargarPacientes, Comparator.comparing(Paciente::prioridad));
        probarDataset(Config.I500, this::cargarInventario, Comparator.comparing(ItemInventario::stock));
    }

    private <T> void probarDataset(
            String nombreArchivo,
            Cargador<T> cargador,
            Comparator<T> comparador
    ) throws Exception {

        System.out.println("\n======================================================");
        System.out.println(" DATASET: " + nombreArchivo);
        System.out.println("======================================================");

        List<Resultado> resultados = new ArrayList<>();

        resultados.add(medir("Burbuja", new BubbleSort<>(), cargador, nombreArchivo, comparador));
        resultados.add(medir("Selección", new SelectionSort<>(), cargador, nombreArchivo, comparador));
        resultados.add(medir("Inserción", new InsertionSort<>(), cargador, nombreArchivo, comparador));

        imprimirTabla(resultados);
    }

    private <T> Resultado medir(
            String nombreAlgoritmo,
            Ordenador<T> algoritmo,
            Cargador<T> cargador,
            String archivo,
            Comparator<T> comp
    ) throws Exception {

        List<Double> tiempos = new ArrayList<>();
        List<Long> comps = new ArrayList<>();
        List<Long> swaps = new ArrayList<>();

        for (int r = 0; r < 10; r++) {
            List<T> datos = cargador.cargar(Config.RUTA + archivo);
            Contador contador = new Contador();

            long inicio = System.nanoTime();
            algoritmo.ordenar(datos, comp, contador);
            long fin = System.nanoTime();

            if (r >= 3) {
                tiempos.add((fin - inicio) / 1_000_000.0);
                comps.add(contador.getComparaciones());
                swaps.add(contador.getIntercambios());
            }
        }

        double tiempoMediana = calcularMediana(tiempos);
        long compMediana = calcularMedianaLong(comps);
        long swapMediana = calcularMedianaLong(swaps);

        return new Resultado(nombreAlgoritmo, tiempoMediana, compMediana, swapMediana);
    }

    private double calcularMediana(List<Double> lista) {
        Collections.sort(lista);
        return lista.get(lista.size() / 2);
    }

    private long calcularMedianaLong(List<Long> lista) {
        Collections.sort(lista);
        return lista.get(lista.size() / 2);
    }

    // ---------------------------
    // Tabla bonita en consola
    // ---------------------------
    private void imprimirTabla(List<Resultado> lista) {

        System.out.println("--------------------------------------------------------------");
        System.out.printf("| %-12s | %-11s | %-14s | %-10s |\n",
                "Algoritmo", "Tiempo (ms)", "Comparaciones", "Swaps");
        System.out.println("--------------------------------------------------------------");

        for (Resultado r : lista) {
            System.out.printf("| %-12s | %11.2f | %14d | %10d |\n",
                    r.algoritmo(), r.tiempoMs(), r.comparaciones(), r.swaps());
        }

        System.out.println("--------------------------------------------------------------");
    }

    // ---------------------------
    // Lectores CSV
    // ---------------------------

    private List<Cita> cargarCitas(String ruta) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        List<Cita> lista = new ArrayList<>();
        br.readLine();

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] p = linea.split(";");
            lista.add(new Cita(p[0], p[1], LocalDateTime.parse(p[2])));
        }
        return lista;
    }

    private List<Paciente> cargarPacientes(String ruta) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        List<Paciente> lista = new ArrayList<>();
        br.readLine();

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] p = linea.split(";");
            lista.add(new Paciente(p[0], p[1], Integer.parseInt(p[2])));
        }
        return lista;
    }

    private List<ItemInventario> cargarInventario(String ruta) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(ruta));
        List<ItemInventario> lista = new ArrayList<>();
        br.readLine();

        String linea;
        while ((linea = br.readLine()) != null) {
            String[] p = linea.split(";");
            lista.add(new ItemInventario(p[0], p[1], Integer.parseInt(p[2])));
        }
        return lista;
    }

    // Función para usar lambdas como cargadores
    interface Cargador<T> {
        List<T> cargar(String ruta) throws Exception;
    }
}
