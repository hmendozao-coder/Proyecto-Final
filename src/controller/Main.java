package controller;

import java.util.Scanner;
import model.Usuario;
import model.Prestamo;
import dao.FirebaseService;

/**
 * Clase Main
 * Controlador principal del sistema de préstamo de libros.
 * Permite realizar operaciones CRUD sobre usuarios y préstamos
 * utilizando la consola como interfaz.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FirebaseService service = new FirebaseService();
        int opcion;

        do {
            System.out.println("\n--- MENÚ PRINCIPAL ---");
            System.out.println("USUARIOS:");
            System.out.println("1. Crear usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Consultar usuario");
            System.out.println("4. Modificar usuario");
            System.out.println("5. Eliminar usuario");
            System.out.println("PRÉSTAMOS:");
            System.out.println("6. Crear préstamo");
            System.out.println("7. Listar préstamos");
            System.out.println("8. Consultar préstamo");
            System.out.println("9. Modificar préstamo");
            System.out.println("10. Eliminar préstamo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    Usuario usuario = new Usuario(nombre, correo, telefono);
                    service.registrarUsuario(usuario.toJson());
                    break;

                case 2:
                    System.out.println("Usuarios registrados:");
                    System.out.println(service.listarUsuarios());
                    break;

                case 3:
                    System.out.print("ID del usuario: ");
                    String idUsuario = scanner.nextLine();
                    String urlUsuario = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Usuarios/" + idUsuario;
                    System.out.println(service.consultar(urlUsuario));
                    break;

                case 4:
                    System.out.print("ID del usuario a modificar: ");
                    String idModUsuario = scanner.nextLine();
                    String urlModUsuario = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Usuarios/" + idModUsuario;

                    System.out.println("Ingrese los nuevos datos del usuario:");
                    System.out.print("Nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String nuevoCorreo = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String nuevoTelefono = scanner.nextLine();

                    Usuario usuarioMod = new Usuario(nuevoNombre, nuevoCorreo, nuevoTelefono);
                    service.modificar(urlModUsuario, usuarioMod.toJson());
                    break;

                case 5:
                    System.out.print("ID del usuario a eliminar: ");
                    String idDelUsuario = scanner.nextLine();
                    String urlDelUsuario = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Usuarios/" + idDelUsuario;
                    service.eliminar(urlDelUsuario);
                    break;

                case 6:
                    System.out.print("Título del libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Código del libro: ");
                    String codigo = scanner.nextLine();
                    System.out.print("Fecha de préstamo (YYYY-MM-DD): ");
                    String fechaPrestamo = scanner.nextLine();
                    System.out.print("Fecha de devolución (YYYY-MM-DD): ");
                    String fechaDevolucion = scanner.nextLine();
                    System.out.print("ID del usuario que recibe el libro: ");
                    String idUsuarioPrestamo = scanner.nextLine();

                    Prestamo prestamo = new Prestamo(titulo, codigo, fechaPrestamo, fechaDevolucion, idUsuarioPrestamo);
                    service.registrarPrestamo(prestamo.toJson());
                    break;

                case 7:
                    System.out.println("Préstamos registrados:");
                    System.out.println(service.listarPrestamos());
                    break;

                case 8:
                    System.out.print("ID del préstamo: ");
                    String idPrestamo = scanner.nextLine();
                    String urlPrestamo = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Prestamos/" + idPrestamo;
                    String jsonPrestamo = service.consultar(urlPrestamo);
                    System.out.println("Datos del préstamo:");
                    System.out.println(jsonPrestamo);

                    String idUsuarioVinculado = extraerCampo(jsonPrestamo, "idUsuario");
                    if (!idUsuarioVinculado.isEmpty()) {
                        String urlUsuarioVinculado = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Usuarios/" + idUsuarioVinculado;
                        String jsonUsuario = service.consultar(urlUsuarioVinculado);
                        System.out.println("Usuario que tiene el libro:");
                        System.out.println(jsonUsuario);
                    } else {
                        System.out.println("Este préstamo no tiene usuario vinculado.");
                    }
                    break;

                case 9:
                    System.out.print("ID del préstamo a modificar: ");
                    String idModPrestamo = scanner.nextLine();
                    String urlModPrestamo = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Prestamos/" + idModPrestamo;

                    System.out.println("Ingrese los nuevos datos del préstamo:");
                    System.out.print("Título del libro: ");
                    String nuevoTitulo = scanner.nextLine();
                    System.out.print("Código del libro: ");
                    String nuevoCodigo = scanner.nextLine();
                    System.out.print("Fecha de préstamo (YYYY-MM-DD): ");
                    String nuevaFechaPrestamo = scanner.nextLine();
                    System.out.print("Fecha de devolución (YYYY-MM-DD): ");
                    String nuevaFechaDevolucion = scanner.nextLine();
                    System.out.print("ID del usuario que recibe el libro: ");
                    String nuevoIdUsuario = scanner.nextLine();

                    Prestamo prestamoMod = new Prestamo(nuevoTitulo, nuevoCodigo, nuevaFechaPrestamo, nuevaFechaDevolucion, nuevoIdUsuario);
                    service.modificar(urlModPrestamo, prestamoMod.toJson());
                    break;

                case 10:
                    System.out.print("ID del préstamo a eliminar: ");
                    String idDelPrestamo = scanner.nextLine();
                    String urlDelPrestamo = "https://firestore.googleapis.com/v1/projects/prestamolibros-8f9eb/databases/(default)/documents/Prestamos/" + idDelPrestamo;
                    service.eliminar(urlDelPrestamo);
                    break;

                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        } while (opcion != 0);

        scanner.close();
    }

    /**
     * Extrae el valor de un campo stringValue desde un JSON plano.
     * @param json Cadena JSON completa.
     * @param campo Nombre del campo a buscar.
     * @return Valor del campo como texto.
     */
    public static String extraerCampo(String json, String campo) {
        try {
            String marcador = "\"" + campo + "\":";
            int inicio = json.indexOf(marcador);
            if (inicio == -1) return "";
            int valorInicio = json.indexOf("\"stringValue\":", inicio);
            if (valorInicio == -1) return "";
            valorInicio = json.indexOf("\"", valorInicio + 14) + 1;
            int valorFin = json.indexOf("\"", valorInicio);
            return json.substring(valorInicio, valorFin);
        } catch (Exception e) {
            return "";
        }
    }
}
