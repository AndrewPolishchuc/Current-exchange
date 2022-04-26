package org.andriipolishchuk.currentexchange.config;

import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class FinageProperties {
    private final String baseUrl = "https://api.finage.co.uk/";

    private final String API_KEY = "API_KEY88PD7A0HWEYW5WH3M3OFAHF3ZZMCO0RP";

    public String getCurrentUsdData() {
        return baseUrl + "last/forex/USDUAH?apikey=" + API_KEY;
    }

    public String getAverageDailyUsdData() {
        return baseUrl + "agg/forex/USDUAH/1/day/" + LocalDate.now().minusDays(1) + "/" + LocalDate.now()
                + "?apikey=" + API_KEY;
    }

    public String getCurrentBTCData() {
        return baseUrl + "last/crypto/BTCUSD?apikey=" + API_KEY;
    }

    public String getAverageDailyBTCData() {
        return baseUrl + "agg/crypto/BTCUSD/1/day/" + LocalDate.now() + "/" + LocalDate.now()
                + "?apikey=" + API_KEY;
    }
}
