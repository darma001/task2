import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.io.BufferedReader;

public class WeatherClient {

    private static final String API_KEY = "YOUR_API_KEY_HERE";  // Replace with your OpenWeatherMap API Key
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        // City for which to fetch the weather
        String city = "London";  // You can change this to any city you like

        try {
            // Create the URL to send the HTTP GET request
            String urlString = BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);

            // Open a connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse the JSON response
            JSONObject jsonResponse = new JSONObject(response.toString());

            // Extract relevant weather information
            String cityName = jsonResponse.getString("name");
            JSONObject main = jsonResponse.getJSONObject("main");
            double temperature = main.getDouble("temp");
            int humidity = main.getInt("humidity");

            JSONObject weather = jsonResponse.getJSONArray("weather").getJSONObject(0);
            String description = weather.getString("description");

            // Display the weather information
            System.out.println("Weather Information for " + cityName + ":");
            System.out.println("Temperature: " + temperature + "°C");
            System.out.println("Humidity: " + humidity + "%");
            System.out.println("Description: " + description);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
