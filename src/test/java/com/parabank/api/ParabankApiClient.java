package com.parabank.api;

import com.parabank.config.ConfigReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ParabankApiClient {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    public HttpResponse<String> get(String path) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(normalize(path)))
                .timeout(Duration.ofSeconds(20))
                .GET()
                .build();
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException exception) {
            throw new IllegalStateException("Unable to call API endpoint: " + path, exception);
        }
    }

    private String normalize(String path) {
        String baseUrl = ConfigReader.getApiBaseUrl();
        if (path.startsWith("http")) {
            return path;
        }
        if (path.startsWith("/")) {
            return baseUrl + path;
        }
        return baseUrl + "/" + path;
    }
}
