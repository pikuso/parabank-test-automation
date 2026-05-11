package com.parabank.tests;

import com.parabank.api.ParabankApiClient;
import java.io.StringReader;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@Tag("api")
public class ApiAdvancedTest {

    private final ParabankApiClient apiClient = new ParabankApiClient();

    @Test
    void loginEndpointReturnsValidCustomerProfileContract() {
        HttpResponse<String> response = apiClient.get("login/john/demo");

        Assertions.assertTrue(response.statusCode() < 500, "Login endpoint should not fail with 5xx");
        Document xml = toXml(response.body());

        Assertions.assertAll(
                () -> Assertions.assertFalse(readXpath(xml, "count(//customer)").isBlank()),
                () -> Assertions.assertFalse(readXpath(xml, "string(//customer/id)").isBlank()),
                () -> Assertions.assertFalse(readXpath(xml, "string(//customer/firstName)").isBlank()),
                () -> Assertions.assertFalse(readXpath(xml, "string(//customer/lastName)").isBlank()));
    }

    @Test
    void customerAccountsEndpointReturnsParsableAccountsWithBalances() {
        HttpResponse<String> response = apiClient.get("customers/12212/accounts");

        Assertions.assertTrue(response.statusCode() < 500, "Accounts endpoint should not fail with 5xx");
        Document xml = toXml(response.body());

        NodeList accountIds = readNodes(xml, "//account/id");
        NodeList balances = readNodes(xml, "//account/balance");

        Assertions.assertAll(
                () -> Assertions.assertTrue(accountIds.getLength() > 0, "Expected at least one account"),
                () -> Assertions.assertEquals(accountIds.getLength(), balances.getLength(),
                        "Every account should have a balance"));

        for (int i = 0; i < balances.getLength(); i++) {
            String value = balances.item(i).getTextContent().trim();
            Assertions.assertDoesNotThrow(() -> Double.parseDouble(value),
                    "Balance should be numeric for account index " + i);
        }
    }

    @Test
    void accountDetailsAreConsistentBetweenListAndSingleAccountEndpoint() {
        HttpResponse<String> allAccounts = apiClient.get("customers/12212/accounts");
        Document allAccountsXml = toXml(allAccounts.body());
        String accountId = readXpath(allAccountsXml, "string(//account[1]/id)");
        String listedBalance = readXpath(allAccountsXml, "string(//account[1]/balance)");

        Assertions.assertFalse(accountId.isBlank(), "First account id should exist in customer accounts response");

        HttpResponse<String> singleAccount = apiClient.get("accounts/" + accountId);
        Document singleAccountXml = toXml(singleAccount.body());

        String detailedId = readXpath(singleAccountXml, "string(//account/id)");
        String detailedBalance = readXpath(singleAccountXml, "string(//account/balance)");

        Assertions.assertAll(
                () -> Assertions.assertEquals(accountId, detailedId, "Account id should match across endpoints"),
                () -> Assertions.assertEquals(listedBalance, detailedBalance,
                        "Balance should stay consistent between list and detail endpoint"));
    }

    @Test
    void invalidLoginResponseDoesNotLeakSensitiveData() {
        HttpResponse<String> response = apiClient.get("login/john/wrong-password");

        Assertions.assertTrue(response.statusCode() < 500, "Invalid login should not trigger 5xx");
        String body = response.body().toLowerCase();

        Assertions.assertAll(
                () -> Assertions.assertFalse(body.contains("wrong-password"),
                        "Response should not echo raw password"),
                () -> Assertions.assertFalse(body.contains("exception"),
                        "Response should not leak stack traces"),
                () -> Assertions.assertFalse(body.contains("java.lang"),
                        "Response should not expose internal exception class names"));
    }

    @Test
    void criticalApiEndpointsRespondWithinPerformanceBudget() {
        List<String> endpoints = List.of(
                "login/john/demo",
                "customers/12212/accounts",
                "accounts/13344"
        );
        List<Long> durations = new ArrayList<>();

        for (String endpoint : endpoints) {
            Instant start = Instant.now();
            HttpResponse<String> response = apiClient.get(endpoint);
            long millis = Duration.between(start, Instant.now()).toMillis();
            durations.add(millis);

            Assertions.assertTrue(response.statusCode() < 500, "Endpoint failed: " + endpoint);
            Assertions.assertTrue(millis < 3000, "Endpoint is too slow: " + endpoint + " took " + millis + " ms");
        }

        Assertions.assertEquals(endpoints.size(), durations.size());
    }

    private Document toXml(String payload) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            return factory.newDocumentBuilder().parse(new InputSource(new StringReader(payload)));
        } catch (Exception exception) {
            throw new IllegalStateException("Payload is not valid XML", exception);
        }
    }

    private String readXpath(Document document, String expression) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return xPath.evaluate(expression, document).trim();
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to evaluate XPath: " + expression, exception);
        }
    }

    private NodeList readNodes(Document document, String expression) {
        try {
            XPath xPath = XPathFactory.newInstance().newXPath();
            return (NodeList) xPath.evaluate(expression, document, XPathConstants.NODESET);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to evaluate nodes XPath: " + expression, exception);
        }
    }
}
