package org.andriipolishchuk.currentexchange.service;

import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;

public interface FinageIntegrationService {
    ExchangeUsdDataDto getActualUsdExchangeData();

    ExchangeUsdDataDto getActualBtcExchangeData();
}
