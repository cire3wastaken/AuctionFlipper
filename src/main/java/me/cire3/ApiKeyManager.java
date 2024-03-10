package me.cire3;

import java.io.BufferedReader;
import java.io.FileReader;

public class ApiKeyManager {
    private static String apiKey;

    public static String getApiKey() {
        if (apiKey != null)
            return apiKey;

        try {
            try (BufferedReader br = new BufferedReader(new FileReader("ApiKey.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                return apiKey = sb.toString().strip();
            }
        } catch (Exception e) {
            System.exit(-1);
        }
        return null;
    }
}
