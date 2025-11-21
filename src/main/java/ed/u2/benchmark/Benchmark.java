package ed.u2.benchmark;

import ed.u2.Config;
import ed.u2.generador.GeneradorDatasets;
import ed.u2.modelo.Cita;
import ed.u2.modelo.Paciente;
import ed.u2.modelo.ItemInventario;
import ed.u2.ordenacion.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Benchmark:
 * - carga cada CSV.
 * - convierte a int[] (clave) según tipo de dataset.
 * - ejecuta Burbuja, Selección y Inserción.
 * - descarta corridas y calcula mediana de tiempos, comparaciones e intercambios.
 */
public class Benchmark {

    // Se encarga de ejecutar y medir el rendimiento de los algoritmos.
    private final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    // Carga cada dataset desde CSV y convierte los objetos a arrays de enteros.
    public void ejecutarTodos() throws Exception {
        // Citas 100
        System.out.println("\n=== Dataset: citas_100.csv ===");
        List<Cita> citas = leerCitas(Config.RUTA_DATASETS + Config.CITAS_100);
        int[] clavesCitas = clavesParaCitas(citas);
        ejecutarAlgoritmosYMostrar("citas_100", clavesCitas);

        // Citas casi ordenadas
        System.out.println("\n=== Dataset: citas_100_casi_ordenadas.csv ===");
        List<Cita> citasCo = leerCitas(Config.RUTA_DATASETS + Config.CITAS_100_CASI);
        int[] clavesCitasCo = clavesParaCitas(citasCo);
        ejecutarAlgoritmosYMostrar("citas_100_casi", clavesCitasCo);

        // Pacientes 500
        System.out.println("\n=== Dataset: pacientes_500.csv ===");
        List<Paciente> pacientes = leerPacientes(Config.RUTA_DATASETS + Config.PACIENTES_500);
        int[] clavesPacientes = clavesParaPacientes(pacientes);
        ejecutarAlgoritmosYMostrar("pacientes_500", clavesPacientes);

        // Inventario inverso
        System.out.println("\n=== Dataset: inventario_500_inverso.csv ===");
        List<ItemInventario> inventario = leerInventario(Config.RUTA_DATASETS + Config.INVENTARIO_500_INVERSO);
        int[] clavesInventario = clavesParaInventario(inventario);
        ejecutarAlgoritmosYMostrar("inventario_500_inverso", clavesInventario);
    }

    // ------------------ lectura CSV simples ------------------
    // Lee archivos CSV y crea objetos del modelo correspondiente.
    private List<Cita> leerCitas(String ruta) throws IOException {
        List<Cita> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(ruta), StandardCharsets.UTF_8))) {
            br.readLine(); // encabezado
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(";", -1);
                String id = p[0];
                String apellido = p[1];
                LocalDateTime fecha = LocalDateTime.parse(p[2], FORMATO);
                lista.add(new Cita(id, apellido, fecha));
            }
        }
        return lista;
    }

    private List<Paciente> leerPacientes(String ruta) throws IOException {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(ruta), StandardCharsets.UTF_8))) {
            br.readLine();
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(";", -1);
                lista.add(new Paciente(p[0], p[1], Integer.parseInt(p[2])));
            }
        }
        return lista;
    }

    private List<ItemInventario> leerInventario(String ruta) throws IOException {
        List<ItemInventario> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(ruta), StandardCharsets.UTF_8))) {
            br.readLine();
            String l;
            while ((l = br.readLine()) != null) {
                String[] p = l.split(";", -1);
                lista.add(new ItemInventario(p[0], p[1], Integer.parseInt(p[2])));
            }
        }
        return lista;
    }

    // conversiones a claves int[]
    // Para apellidos usar el ranking lexicografico.
    // Para fechas: usar minutos desde una fecha base (preserva orden)
    private int[] clavesParaCitas(List<Cita> citas) {
        LocalDateTime base = LocalDateTime.of(2025,1,1,0,0);
        int n = citas.size();
        int[] claves = new int[n];
        for (int i = 0; i < n; i++) {
            long minutos = ChronoUnit.MINUTES.between(base, citas.get(i).getFechaHora());
            claves[i] = (int) minutos; // seguro porque son pocos minutos
        }
        return claves;
    }

    // Para pacientes: construir ranking de apellidos (orden lexicográfico) y devolver rank como clave
    private int[] clavesParaPacientes(List<Paciente> pacientes) {
        List<String> apellidos = pacientes.stream().map(Paciente::getApellido).collect(Collectors.toList());
        // obtener únicos ordenados
        List<String> unicos = apellidos.stream().distinct().sorted().collect(Collectors.toList());
        Map<String,Integer> rank = new HashMap<>();
        for (int i = 0; i < unicos.size(); i++) rank.put(unicos.get(i), i);
        int[] claves = new int[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) claves[i] = rank.get(pacientes.get(i).getApellido());
        return claves;
    }

    // Inventario ya trae stock, usamos stock como clave
    private int[] clavesParaInventario(List<ItemInventario> items) {
        int[] claves = new int[items.size()];
        for (int i = 0; i < items.size(); i++) claves[i] = items.get(i).getStock();
        return claves;
    }

    // Ejecución de algoritmos y estadísticas.
    // Permite pasar algoritmos como parametros y se utiliza un contador objeto para llevar las metricas.
    private void ejecutarAlgoritmosYMostrar(String nombreDataset, int[] clavesOriginal) {
        ejecutarYMostrar(nombreDataset, "Burbuja", clavesOriginal, (arr, contador) -> BubbleSort.sort(arr, contador, false));
        ejecutarYMostrar(nombreDataset, "Seleccion", clavesOriginal, (arr, contador) -> SelectionSort.sort(arr, contador, false));
        ejecutarYMostrar(nombreDataset, "Insercion", clavesOriginal, (arr, contador) -> InsertionSort.sort(arr, contador, false));
    }

    // interfaz funcional local
    private interface OrdenadorInt {
        void ordenar(int[] arr, Contador contador);
    }

    // Copia el array original para no alterar datos, mide el tiempo y captura metricas del contador.
    private void ejecutarYMostrar(String nombreDataset, String nombreAlg, int[] clavesOriginal, OrdenadorInt ordenador) {
        int R = Config.REPETICIONES;
        int desc = Config.DESCARTAR;
        long[] tiempos = new long[R];
        long[] comparaciones = new long[R];
        long[] intercambios = new long[R];

        for (int r = 0; r < R; r++) {
            // copia del arreglo original
            int[] copia = Arrays.copyOf(clavesOriginal, clavesOriginal.length);
            Contador contador = new Contador();
            long t0 = System.nanoTime();
            ordenador.ordenar(copia, contador);
            long t1 = System.nanoTime();
            tiempos[r] = t1 - t0;
            comparaciones[r] = contador.getComparaciones();
            intercambios[r] = contador.getIntercambios();
            System.out.println(String.format("Run %d: %s - tiempo(ns)=%d comparaciones=%d intercambios=%d",
                    r+1, nombreAlg, tiempos[r], comparaciones[r], intercambios[r]));
        }

        // ordenar arrays para calcular mediana, usa mediana y ordena los arrays.
        long[] tiemposFiltrados = filtrarYOrdenar(tiempos, desc);
        long[] compFiltradas = filtrarYOrdenar(comparaciones, desc);
        long[] intercFiltradas = filtrarYOrdenar(intercambios, desc);

        long medianaT = mediana(tiemposFiltrados);
        long medianaC = mediana(compFiltradas);
        long medianaI = mediana(intercFiltradas);

        System.out.println(String.format("== Resultado %s sobre %s: mediana tiempo(ns)=%d, mediana comparaciones=%d, mediana intercambios=%d",
                nombreAlg, nombreDataset, medianaT, medianaC, medianaI));
    }

    // elimina primeras 'desc' y ordena lo restante para la mediana.
    private long[] filtrarYOrdenar(long[] arr, int desc) {
        if (arr.length <= desc) return new long[0];
        long[] res = Arrays.copyOfRange(arr, desc, arr.length);
        Arrays.sort(res);
        return res;
    }

    private long mediana(long[] arr) {
        if (arr.length == 0) return 0;
        int m = arr.length;
        if (m % 2 == 1) return arr[m/2];
        else return (arr[m/2 - 1] + arr[m/2]) / 2;
    }
}
