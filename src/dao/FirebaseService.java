package dao;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Clase FirebaseService
 * Proporciona métodos para realizar operaciones CRUD sobre documentos
 * en las colecciones "Usuarios" y "Prestamos" de Firebase Firestore.
 */
public class FirebaseService {

    // URLs base para las colecciones en Firestore
    private static final String FIREBASE_URL_USUARIOS = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Usuarios";
    private static final String FIREBASE_URL_PRESTAMOS = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Prestamos";

    /**
     * Registra un nuevo usuario en Firebase.
     * @param jsonData Cadena JSON con los datos del usuario.
     */
    public void registrarUsuario(String jsonData) {
        enviarDatosAFirebase(FIREBASE_URL_USUARIOS, jsonData);
    }

    /**
     * Registra un nuevo préstamo en Firebase.
     * @param jsonData Cadena JSON con los datos del préstamo.
     */
    public void registrarPrestamo(String jsonData) {
        enviarDatosAFirebase(FIREBASE_URL_PRESTAMOS, jsonData);
    }

    /**
     * Método privado para enviar datos a Firebase mediante POST.
     * @param urlDestino URL de la colección destino.
     * @param jsonData Datos en formato JSON.
     */
    private void enviarDatosAFirebase(String urlDestino, String jsonData) {
        try {
            URL url = new URL(urlDestino);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Respuesta Firebase (CREAR): " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Consulta un documento específico en Firebase.
     * @param urlDocumento URL completa del documento.
     * @return Contenido del documento en formato JSON.
     */
    public String consultar(String urlDocumento) {
        try {
            URL url = new URL(urlDocumento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            Scanner scanner = new Scanner(conn.getInputStream());
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            scanner.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Modifica un documento existente en Firebase.
     * Usa POST con X-HTTP-Method-Override para simular PATCH.
     * @param urlDocumento URL completa del documento.
     * @param jsonData Datos JSON con los campos a modificar.
     */
    public void modificar(String urlDocumento, String jsonData) {
        try {
            URL url = new URL(urlDocumento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            os.write(jsonData.getBytes("UTF-8"));
            os.close();

            int responseCode = conn.getResponseCode();
            System.out.println("Respuesta Firebase (MODIFICAR): " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un documento específico en Firebase.
     * @param urlDocumento URL completa del documento.
     */
    public void eliminar(String urlDocumento) {
        try {
            URL url = new URL(urlDocumento);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

            int responseCode = conn.getResponseCode();
            System.out.println("Respuesta Firebase (ELIMINAR): " + responseCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista todos los documentos de la colección "Usuarios".
     * @return Cadena JSON con todos los usuarios registrados.
     */
    public String listarUsuarios() {
        return consultar(FIREBASE_URL_USUARIOS);
    }

    /**
     * Lista todos los documentos de la colección "Prestamos".
     * @return Cadena JSON con todos los préstamos registrados.
     */
    public String listarPrestamos() {
        return consultar(FIREBASE_URL_PRESTAMOS);
    }
}
