package com.learnthepath.recruitmentsystemabc.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    public JsonObject getExchangeRateData(String currency) throws IOException {
        StringBuilder responseData = new StringBuilder();
        JsonObject jsonObject;
        URL url;

        url = new URL("https://api.exchangerate-api.com/v4/latest/" + currency);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }
            jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);

        }
        return jsonObject.get("rates").getAsJsonObject();
    }

    @Override
    public Double convert(String fromCurrency, String toCurrency, double amount) throws IOException {
        JsonObject rates;
        double exchangedAmount = 0.0;
        rates = getExchangeRateData(fromCurrency);

        if (rates != null) {
            exchangedAmount = amount * rates.get(toCurrency).getAsDouble();
        }
        return exchangedAmount;
    }

}
