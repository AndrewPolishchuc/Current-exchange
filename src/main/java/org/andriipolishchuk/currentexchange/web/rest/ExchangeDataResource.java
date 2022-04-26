package org.andriipolishchuk.currentexchange.web.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.andriipolishchuk.currentexchange.service.ExchangeDataService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


@RestController
@RequestMapping("/api/exchange")
@Slf4j
@AllArgsConstructor
public class ExchangeDataResource {

    protected final ExchangeDataService exchangeDataService;

//    @ApiOperation(
//            value = "Getting specific USD/UAH exchange data.",
//            notes = "This API is used before SSE connection establishing or for getting exchange data."
//    )
    @GetMapping(value = "/usd-ua", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> subscribeToUsdExchangeUpdates() {
        log.info("Establish SSE connection for USD/UAH exchange data");
        return exchangeDataService.subscribeToUsdExchangeUpdates();
    }

//    @ApiOperation(
//            value = "Getting specific BTC/USD exchange data.",
//            notes = "This API is used before SSE connection establishing or for getting exchange data."
//    )
    @GetMapping(value = "/btc-usd", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> subscribeBtcUsdExchangeUpdates() {
        log.info("Establish SSE connection for BTC/USD exchange data");
        return exchangeDataService.subscribeToBtcExchangeUpdates();
    }
}
