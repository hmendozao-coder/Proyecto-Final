package model;

/**
 * Clase Prestamo
 * Representa un préstamo de libro con título, código, fechas y vínculo al usuario.
 */
public class Prestamo {
    private String tituloLibro;
    private String codigoLibro;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String idUsuario;

    /**
     * Constructor de Prestamo.
     * @param tituloLibro Título del libro prestado.
     * @param codigoLibro Código identificador del libro.
     * @param fechaPrestamo Fecha en que se realizó el préstamo.
     * @param fechaDevolucion Fecha estimada de devolución.
     * @param idUsuario ID del documento del usuario en Firebase.
     */
    public Prestamo(String tituloLibro, String codigoLibro, String fechaPrestamo, String fechaDevolucion, String idUsuario) {
        this.tituloLibro = tituloLibro;
        this.codigoLibro = codigoLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.idUsuario = idUsuario;
    }

    /**
     * Convierte los datos del préstamo a formato JSON para Firebase.
     * @return Cadena JSON con los campos del préstamo.
     */
    public String toJson() {
        return "{ \"fields\": { " +
                "\"tituloLibro\": {\"stringValue\": \"" + tituloLibro + "\"}, " +
                "\"codigoLibro\": {\"stringValue\": \"" + codigoLibro + "\"}, " +
                "\"fechaPrestamo\": {\"stringValue\": \"" + fechaPrestamo + "\"}, " +
                "\"fechaDevolucion\": {\"stringValue\": \"" + fechaDevolucion + "\"}, " +
                "\"idUsuario\": {\"stringValue\": \"" + idUsuario + "\"} " +
                "} }";
    }
}
