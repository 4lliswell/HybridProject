package baseUrl;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import utilities.ConfigReader;

public class TrelloBaseUrl {

    public static RequestSpecification spec;

    public static void setSpec() {

        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.trello.com/1")
                .addQueryParam("key", ConfigReader.getProperty("apiKey"))
                .addQueryParam("token", ConfigReader.getProperty("token"))
                .build();
    }
}
