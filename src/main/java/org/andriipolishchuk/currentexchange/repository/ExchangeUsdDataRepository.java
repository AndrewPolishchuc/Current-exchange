package org.andriipolishchuk.currentexchange.repository;

import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import java.util.Optional;

public interface ExchangeUsdDataRepository {
    void saveUpdateUsdData(ExchangeUsdDataDto exchangeUsdDataDto);

    void saveUpdateBtcData(ExchangeUsdDataDto exchangeUsdDataDto);

    Optional<ExchangeUsdDataDto> findUsdExchangeData(String exchangeCode);
}
