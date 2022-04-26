package org.andriipolishchuk.currentexchange.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.andriipolishchuk.currentexchange.repository.ExchangeUsdDataRepository;
import org.andriipolishchuk.currentexchange.service.ExchangeDataService;
import org.andriipolishchuk.currentexchange.service.FinageIntegrationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeDataServiceImpl implements ExchangeDataService {
    private final String USD_DATA = "USD/UAH";

    private final String BTC_DATA = "BTC/USD";

    private final int FLUX_DELAY = 0;

    private final int FLUX_PERIOD = 30;

    private final ExchangeUsdDataRepository exchangeUsdDataRepository;

    @NonNull
    private Sinks.Many<List<ExchangeUsdDataDto>> symbolUpdatesSink;

    private final FinageIntegrationService finageIntegrationService;

    @Override
    public Flux<String> subscribeToUsdExchangeUpdates() {
        log.info("Subscribe for usd/uah exchange");
        return Flux.interval(Duration.ofSeconds(FLUX_DELAY), Duration.ofSeconds(FLUX_PERIOD))
                .map(sequence -> answerFormatting(USD_DATA));
//        return symbolUpdatesSink
//                .asFlux()
//                .flatMap(Flux::fromIterable)
//                .doOnNext(symbol -> log.info("Prepare to publish usd/uah exchange data - " + symbol));
    }

    @Override
    public Flux<String> subscribeToBtcExchangeUpdates() {
        log.info("Subscribe for usd/uah exchange");
        return Flux.interval(Duration.ofSeconds(FLUX_DELAY), Duration.ofSeconds(FLUX_PERIOD))
                .map(sequence -> answerFormatting(BTC_DATA));
    }

    private String answerFormatting(String symbol) {
        Optional<ExchangeUsdDataDto> usdExchangeData = exchangeUsdDataRepository.findUsdExchangeData(symbol);
        if (usdExchangeData.isEmpty()) {
            return "Данные не получены!";
        }
        return "Текущий курс: " + usdExchangeData.get().getLastPrice() + " Средний курс: " + usdExchangeData.get().getAverageDayPrice()
            + " Дата: " + LocalDateTime.now() + "\n";
    }

    @Scheduled(fixedRateString = "3000")
    public void publishUpdates() {
        int subscribers = symbolUpdatesSink.currentSubscriberCount();
        log.info("Sink subscribers count on publish updates: {}", subscribers);
        Optional<ExchangeUsdDataDto> symbolCached =
                exchangeUsdDataRepository.findUsdExchangeData(USD_DATA);
        if(symbolCached.isPresent()) {
            List<ExchangeUsdDataDto> exchangeUsdDataDto = new ArrayList<>();
            ExchangeUsdDataDto exchangeUsdDataDto1 = new ExchangeUsdDataDto();
            exchangeUsdDataDto1.setAverageDayPrice(symbolCached.get().getAverageDayPrice());
            exchangeUsdDataDto1.setLastPrice(symbolCached.get().getLastPrice());
            exchangeUsdDataDto.add(exchangeUsdDataDto1);
            startEmmitData(exchangeUsdDataDto);
        }
    }

    @Scheduled(fixedRateString = "19000")
    public void fillUpdates() {
        exchangeUsdDataRepository.saveUpdateUsdData(finageIntegrationService.getActualUsdExchangeData());
        exchangeUsdDataRepository.saveUpdateBtcData(finageIntegrationService.getActualBtcExchangeData());
    }

    private void startEmmitData(List<ExchangeUsdDataDto> exchangeUsdDataDto) {
        try {
            Sinks.EmitResult res = symbolUpdatesSink.tryEmitNext(exchangeUsdDataDto);
            if (res.isFailure() && !Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER.equals(res)) {
                if (Sinks.EmitResult.FAIL_OVERFLOW.equals(res)) {
                    log.warn("Try Emit exchange USD/UAH data failed due to EmitResult=FAIL_OVERFLOW");
                } else {
                    log.warn("Try Emit failed for exchange USD/UAH, EmitResult={}", res);
                }
            }
        } catch (Exception ex) {
            log.error("Fatal error during tryEmitNext for symbolCached", ex); // TODO: need to restart service?
        }
    }
}
