package model;

/**
 * Clase Usuario
 * Representa un usuario con nombre, correo y teléfono.
 * Se utiliza para registrar y convertir datos a formato JSON compatible con Firebase.
 */
public class Usuario {
    private String nombre;
    private String correo;
    private String telefono;

    /**
     * Constructor de Usuario.
     * @param nombre Nombre del usuario.
     * @param correo Correo electrónico.
     * @param telefono Número de teléfono.
     */
    public Usuario(String nombre, String correo, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * Convierte los datos del usuario a formato JSON para Firebase.
     * @return Cadena JSON con los campos del usuario.
     */
    public String toJson() {
        return "{ \"fields\": { " +
                "\"nombre\": {\"stringValue\": \"" + nombre + "\"}, " +
                "\"correo\": {\"stringValue\": \"" + correo + "\"}, " +
                "\"telefono\": {\"stringValue\": \"" + telefono + "\"} " +
                "} }";
    }
}
