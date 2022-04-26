package org.andriipolishchuk.currentexchange.mapper;

import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;

public class ActualExchangeUsdDataMapper {
    public static ExchangeUsdDataDto map(Double currentPrice, Double averageDailyPrice) {
        ExchangeUsdDataDto exchangeUsdDataDto = new ExchangeUsdDataDto();
        exchangeUsdDataDto.setLastPrice(currentPrice);
        exchangeUsdDataDto.setAverageDayPrice(averageDailyPrice);
        return exchangeUsdDataDto;
    }
}
