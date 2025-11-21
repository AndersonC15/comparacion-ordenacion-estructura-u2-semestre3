package ed.u2.generador;

import ed.u2.Config;
import ed.u2.modelo.Cita;
import ed.u2.modelo.Paciente;
import ed.u2.modelo.ItemInventario;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.*;

public class GeneradorDatasets {

    private final Random random = new Random(Config.SEMILLA);

    public void generarTodos() throws Exception {
        generarCitas100();
        generarCitas100CasiOrdenadas();
        generarPacientes500();
        generarInventario500Inverso();
    }

    private void generarCitas100() throws Exception {
        List<Cita> lista = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String id = "C" + i;
            String apellido = "Apellido" + random.nextInt(50);
            LocalDateTime fecha = LocalDateTime.of(2025, 3, random.nextInt(28) + 1, random.nextInt(12) + 8, 30);

            lista.add(new Cita(id, apellido, fecha));
        }

        lista.sort(Comparator.comparing(Cita::apellido));

        guardarCitas(lista, Config.C100);
    }

    private void generarCitas100CasiOrdenadas() throws Exception {
        List<Cita> lista = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            String id = "CO" + i;
            String apellido = "Apellido" + i;
            LocalDateTime fecha = LocalDateTime.of(2025, 4, random.nextInt(28) + 1, 8 + random.nextInt(5), 0);

            lista.add(new Cita(id, apellido, fecha));
        }

        Collections.swap(lista, 20, 21);
        Collections.swap(lista, 50, 51);

        guardarCitas(lista, Config.C100CO);
    }

    private void generarPacientes500() throws Exception {
        List<Paciente> lista = new ArrayList<>();

        for (int i = 1; i <= 500; i++) {
            String id = "P" + i;
            String apellido = "Paciente" + random.nextInt(100);
            int prioridad = random.nextInt(1000);

            lista.add(new Paciente(id, apellido, prioridad));
        }

        guardarPacientes(lista, Config.P500);
    }

    private void generarInventario500Inverso() throws Exception {
        List<ItemInventario> lista = new ArrayList<>();

        for (int i = 500; i >= 1; i--) {
            String id = "I" + i;
            String insumo = "Insumo" + i;
            int stock = random.nextInt(200);

            lista.add(new ItemInventario(id, insumo, stock));
        }

        guardarInventario(lista, Config.I500);
    }

    private void guardarCitas(List<Cita> lista, String archivo) throws Exception {
        FileWriter fw = new FileWriter(Config.RUTA + archivo, false);
        fw.write("id;apellido;fechaHora\n");

        for (Cita c : lista)
            fw.write(c.id() + ";" + c.apellido() + ";" + c.fechaHora() + "\n");

        fw.close();
    }

    private void guardarPacientes(List<Paciente> lista, String archivo) throws Exception {
        FileWriter fw = new FileWriter(Config.RUTA + archivo, false);
        fw.write("id;apellido;prioridad\n");

        for (Paciente p : lista)
            fw.write(p.id() + ";" + p.apellido() + ";" + p.prioridad() + "\n");

        fw.close();
    }

    private void guardarInventario(List<ItemInventario> lista, String archivo) throws Exception {
        FileWriter fw = new FileWriter(Config.RUTA + archivo, false);
        fw.write("id;insumo;stock\n");

        for (ItemInventario p : lista)
            fw.write(p.id() + ";" + p.insumo() + ";" + p.stock() + "\n");

        fw.close();
    }
}
