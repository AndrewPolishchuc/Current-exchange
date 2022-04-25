package org.andriipolishchuk.currentexchange.web.rest;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/exchange")
@Slf4j
@AllArgsConstructor
public class ExchangeDataResource {
    @ApiOperation(
            value = "Getting specific USD/UAH exchange data.",
            notes = "This API is used before SSE connection establishing or for getting exchange data."
    )
    @GetMapping(value = "/usd-ua", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ExchangeUsdDataDto> subscribeToCryptoSymbolUpdates() {
        log.info("Establish SSE connection for USD/UAH exchange data");
        return null;
    }
}
