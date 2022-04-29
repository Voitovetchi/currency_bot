package service;

import dto.Rater;
import exceptions.BadResponseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HistoricalRatesService {
    public static String sendRequest(Rater rate) throws URISyntaxException, IOException, InterruptedException {
        String uri = "http://api.exchangeratesapi.io/v1/" + rate.getDate() + "?access_key=4790cd5b2441fea218127c5eb063b542";

        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(uri))
            .version(HttpClient.Version.HTTP_2)
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new BadResponseException("Request went with an error");
        }
    }
}
