package com.learnthepath.recruitmentsystemabc.service;

import com.google.gson.JsonObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface CurrencyService {
    JsonObject getExchangeRateData(String currency) throws IOException;
    Double convert(String fromCurrency, String toCurrency, double amount) throws IOException;
}
