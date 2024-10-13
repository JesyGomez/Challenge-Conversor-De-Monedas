import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaValor consulta = new ConsultaValor();
        System.out.println("*** Sean Bienvenid@s al Conversor de Moneda =) ***\n");

        int opcion = 0;

        do {
            try {
                System.out.println("""
                        ***************************************************
                        Menú:
                        1) Dólar =>> Pesos Argentinos
                        2) Peso Argentino =>> Dólar
                        3) Dólar =>> Real Brasileño
                        4) Real Brasileño =>> Dólar
                        5) Dólar =>> Peso Colombiano
                        6) Peso Colombiano =>> Dólar
                        7) Salir
                        Elija una opción válida:
                        ***************************************************
                        """);

                opcion = lectura.nextInt();

                switch (opcion) {
                    case 1 -> realizarConversion(consulta, "USD", "ARS", lectura);
                    case 2 -> realizarConversion(consulta, "ARS", "USD", lectura);
                    case 3 -> realizarConversion(consulta, "USD", "BRL", lectura);
                    case 4 -> realizarConversion(consulta, "BRL", "USD", lectura);
                    case 5 -> realizarConversion(consulta, "USD", "COP", lectura);
                    case 6 -> realizarConversion(consulta, "COP", "USD", lectura);
                    case 7 -> System.out.println("Saliendo del conversor. ¡Hasta luego!");
                    default -> System.out.println("Opción inválida, por favor seleccione una opción válida.");
                }
            } catch (InputMismatchException e) {
                // Manejo de error si el usuario introduce un valor que no sea número
                System.out.println("Entrada no válida. Por favor, ingrese un número.");
                lectura.next();
            }
        } while (opcion != 7);

        lectura.close();
    }

    private static void realizarConversion(ConsultaValor consulta, String monedaOrigen, String monedaDestino, Scanner lectura) {
        try {
            System.out.print("Ingrese la cantidad de " + monedaOrigen + " que desea convertir: ");
            double cantidad = lectura.nextDouble();
            System.out.println("Convirtiendo " + monedaOrigen + " a " + monedaDestino + "...");

            MonedaValor.ExchangeRateResponse resultado = consulta.buscarMonedaValor();

            if (resultado.conversion_rates().containsKey(monedaDestino)) {
                double tasaConversion = resultado.conversion_rates().get(monedaDestino);
                double valorConvertido = cantidad * tasaConversion;
                System.out.println("La tasa de conversión de " + monedaOrigen + " a " + monedaDestino + " es: " + tasaConversion);
                System.out.printf("El valor convertido es: %.2f %s%n", valorConvertido, monedaDestino);
            } else {
                System.out.println("No se encontró una tasa de conversión para " + monedaDestino);
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada inválida. Por favor, ingrese un número válido.");
            lectura.next();
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }
    }
}
