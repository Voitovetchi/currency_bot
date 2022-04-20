package service;

import dto.Rater;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Service {
    String sendRequest(Rater rate) throws URISyntaxException, IOException, InterruptedException;
}
