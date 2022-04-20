package business;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RateForecast {
    private double[] previousRates = new double[5];
    private double[] forecastRates = new double[5];

    public void doForecast() {
        List<Double> lower = new ArrayList<>();
        List<Double> higher = new ArrayList<>();

        for (int i = 1; i < previousRates.length; i++) {
            double change = previousRates[i] - previousRates[i - 1];
            forecastRates[i-1] = change;
            if (change > 0) {
                higher.add(change);
            } else {
                lower.add(change);
            }
        }

        double median = 0;
        if (higher.size() > lower.size()) {
            double sum = 0;
            for (Double change : higher) {
                sum+=change;
            }
            median = sum/higher.size();
        } else {
            double sum = 0;
            for (Double change : lower) {
                sum+=change;
            }
            median = sum/higher.size();
        }

        forecastRates[0] = previousRates[0] + median;
        for (int i = 1; i < forecastRates.length; i++) {
            forecastRates[i] = forecastRates[i-1] + median;
        }
    }
}
