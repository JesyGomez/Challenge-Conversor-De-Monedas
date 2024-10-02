import java.util.Map;

public record MonedaValor() {
    public record ExchangeRateResponse(
            String result, // indica si la solicitud fue exitosa
            String documentation, // URL de la documentación
            String terms_of_use, // URL de los términos de uso
            long time_last_update_unix, // Última actualización en formato UNIX
            String time_last_update_utc, // Última actualización en formato UTC
            long time_next_update_unix, // Próxima actualización en formato UNIX
            String time_next_update_utc, // Próxima actualización en formato UTC
            String base_code, // Código de la moneda base
            Map<String, Double> conversion_rates // Map de monedas con tasas de conversión
    ) {}
}
