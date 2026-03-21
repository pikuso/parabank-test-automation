package com.parabank.tests;

import com.parabank.api.ParabankApiClient;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ApiSmokeTest {

    private final ParabankApiClient apiClient = new ParabankApiClient();

    @ParameterizedTest
    @ValueSource(strings = {
            "login/john/demo",
            "customers/12212",
            "customers/12212/accounts",
            "accounts/13344",
            "transactions/onDate/13344?onDate=2024-01-01"
    })
    void apiEndpointReturnsHttpResponse(String endpoint) {
        HttpResponse<String> response = apiClient.get(endpoint);

        Assertions.assertTrue(response.statusCode() < 500);
    }

    @Test
    void loginApiReturnsPayloadBody() {
        HttpResponse<String> response = apiClient.get("login/john/demo");

        Assertions.assertFalse(response.body().isBlank());
    }

    @Test
    void accountsApiReturnsXmlOrJsonPayload() {
        HttpResponse<String> response = apiClient.get("customers/12212/accounts");
        String body = response.body();

        Assertions.assertTrue(body.contains("<") || body.contains("{"));
    }
}
