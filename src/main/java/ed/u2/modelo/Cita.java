package ed.u2.modelo;

import java.time.LocalDateTime;

public record Cita(String id, String apellido, LocalDateTime fechaHora) {}