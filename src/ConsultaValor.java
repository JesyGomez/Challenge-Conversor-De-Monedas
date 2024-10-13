import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class ConsultaValor {

    public MonedaValor.ExchangeRateResponse buscarMonedaValor() {

        // URL de la API
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/0fcc85a74392dcb974be0948/latest/USD");
        HttpClient client = HttpClient.newHttpClient();

        // Construyo la solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion)
                .build();

        try {
            // Envío de la solicitud y obtengo la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parseo la respuesta a JSON
            JsonElement root = JsonParser.parseString(response.body());
            JsonObject jsonobj = root.getAsJsonObject();

            // Accedo a los campos de la respuesta
            String result = jsonobj.get("result").getAsString();
            String documentation = jsonobj.get("documentation").getAsString();
            String terms_of_use = jsonobj.get("terms_of_use").getAsString();
            long time_last_update_unix = jsonobj.get("time_last_update_unix").getAsLong();
            String time_last_update_utc = jsonobj.get("time_last_update_utc").getAsString();
            long time_next_update_unix = jsonobj.get("time_next_update_unix").getAsLong();
            String time_next_update_utc = jsonobj.get("time_next_update_utc").getAsString();
            String base_code = jsonobj.get("base_code").getAsString();

            // Accedo al mapa de tasas de conversión
            JsonObject conversionRatesJson = jsonobj.getAsJsonObject("conversion_rates");
            Map<String, Double> conversion_rates = new HashMap<>();

            // Relleno el mapa con las tasas de conversión
            for (Map.Entry<String, JsonElement> entry : conversionRatesJson.entrySet()) {
                conversion_rates.put(entry.getKey(), entry.getValue().getAsDouble());
            }

            // Devuelvo un objeto de tipo ExchangeRateResponse
            return new MonedaValor.ExchangeRateResponse(
                    result,
                    documentation,
                    terms_of_use,
                    time_last_update_unix,
                    time_last_update_utc,
                    time_next_update_unix,
                    time_next_update_utc,
                    base_code,
                    conversion_rates
            );

        } catch (Exception e) {
            throw new RuntimeException("Error al consultar la API: " + e.getMessage());
        }
    }
}
