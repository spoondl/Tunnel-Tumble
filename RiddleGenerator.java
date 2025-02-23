import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RiddleGenerator {
    private static final String API_URL = "https://riddles-api.vercel.app/random";

    public static void main(String[] args) {
        System.out.println("Fetching riddle...");
        String riddle = getRiddle();
        System.out.println("Riddle: " + riddle);

        System.out.println("\nFetching answer...");
        String answer = getAnswer();
        System.out.println("Answer: " + answer);
    }

    public static String getRiddle() {
        return fetchValue("riddle");
    }

    public static String getAnswer() {
        return fetchValue("answer");
    }

    private static String fetchValue(String key) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            return extractJsonValue(content.toString(), key);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching " + key + ".";
        }
    }

    private static String extractJsonValue(String json, String key) {
        String keyPattern = "\"" + key + "\":";
        int keyIndex = json.indexOf(keyPattern);
        if (keyIndex == -1) return "Key not found";

        int valueStart = keyIndex + keyPattern.length();
        int valueEnd = json.indexOf(",", valueStart);

        if (valueEnd == -1) {
            valueEnd = json.indexOf("}", valueStart); // If it's the last key in JSON
        }

        String value = json.substring(valueStart, valueEnd).trim();
        return value.replaceAll("[\"{}]", ""); // Remove quotes and braces
    }
}
