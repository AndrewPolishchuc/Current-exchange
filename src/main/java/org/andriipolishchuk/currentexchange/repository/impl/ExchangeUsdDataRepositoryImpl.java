package org.andriipolishchuk.currentexchange.repository.impl;

import com.github.benmanes.caffeine.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.andriipolishchuk.currentexchange.repository.ExchangeUsdDataRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
@Slf4j
public class ExchangeUsdDataRepositoryImpl implements ExchangeUsdDataRepository {
    protected final Cache<String, ExchangeUsdDataDto> exchangeDataCache;

    @Override
    public void saveUpdateUsdData(ExchangeUsdDataDto exchangeUsdDataDto) {
        exchangeDataCache.put("USD/UAH", exchangeUsdDataDto);
    }

    @Override
    public void saveUpdateBtcData(ExchangeUsdDataDto exchangeUsdDataDto) {
        exchangeDataCache.put("BTC/USD", exchangeUsdDataDto);
    }

    @Override
    public Optional<ExchangeUsdDataDto> findUsdExchangeData(String exchangeCode) {
        return Optional.ofNullable(exchangeDataCache.getIfPresent(exchangeCode));
    }
}
