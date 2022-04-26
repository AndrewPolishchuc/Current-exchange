package org.andriipolishchuk.currentexchange.dto;

import lombok.Data;

@Data
public class ExchangeUsdDataDto {
    private double lastPrice;

    private double averageDayPrice;
}
