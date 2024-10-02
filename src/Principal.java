import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaValor consulta = new ConsultaValor();
        System.out.println("***Sean Bienvenid@s al Conversor de Moneda =)***\n");

        int opcion;

        do {
            System.out.println("""
                    ***************************************************
                    Menu:
                    1) Dólar =>> Pesos Argentinos
                    2) Peso Argentino =>> Dólar
                    3) Dólar =>> Real Brasileño
                    4) Real Brasileño =>> Dólar
                    5) Dólar =>> Peso Colombiano
                    6) Peso Colombiano =>> Dólar
                    7) Salir
                    Elija una opción válida:
                    """
            );

            opcion = lectura.nextInt();
            switch (opcion) {
                case 1 -> {
                    System.out.println("Convirtiendo Dólar a Pesos Argentinos...");
                    realizarConversion(consulta, "USD", "ARS");
                }
                case 2 -> {
                    System.out.println("Convirtiendo Pesos Argentinos a Dólar...");
                    realizarConversion(consulta, "ARS", "USD");
                }
                case 3 -> {
                    System.out.println("Convirtiendo Dólar a Reales Brasileños...");
                    realizarConversion(consulta, "USD", "BRL");
                }
                case 4 -> {
                    System.out.println("Convirtiendo Reales Brasileños a Dólar...");
                    realizarConversion(consulta, "BRL", "USD");
                }
                case 5 -> {
                    System.out.println("Convirtiendo Dólar a Pesos Colombianos...");
                    realizarConversion(consulta, "USD", "COP");
                }
                case 6 -> {
                    System.out.println("Convirtiendo Pesos Colombianos a Dólar...");
                    realizarConversion(consulta, "COP", "USD");
                }
                case 7 -> System.out.println("Saliendo del conversor. ¡Hasta luego!");
                default -> System.out.println("Opción inválida, por favor seleccione una opción válida.");
            }
        } while (opcion != 7);

        lectura.close();
    }

    // Método para realizar la conversión usando la API
    private static void realizarConversion(ConsultaValor consulta, String monedaOrigen, String monedaDestino) {
        try {
            MonedaValor.ExchangeRateResponse resultado = consulta.buscarMonedaValor();

            if (resultado.conversion_rates().containsKey(monedaDestino)) {
                double tasaConversion = resultado.conversion_rates().get(monedaDestino);
                System.out.println("La tasa de conversión de " + monedaOrigen + " a " + monedaDestino + " es: " + tasaConversion);
            } else {
                System.out.println("No se encontró una tasa de conversión para " + monedaDestino);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar la API: " + e.getMessage());
        }
    }
}
