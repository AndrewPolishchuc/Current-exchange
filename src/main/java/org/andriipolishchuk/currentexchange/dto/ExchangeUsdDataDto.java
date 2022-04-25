package org.andriipolishchuk.currentexchange.dto;

import lombok.Data;

@Data
public class ExchangeUsdDataDto {
    private Double lastPrice;

    private Double averageDayPrice;
}
