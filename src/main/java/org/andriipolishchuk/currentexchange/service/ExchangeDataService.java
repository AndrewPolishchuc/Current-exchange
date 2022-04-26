package org.andriipolishchuk.currentexchange.service;

import reactor.core.publisher.Flux;

public interface ExchangeDataService {
    Flux<String> subscribeToUsdExchangeUpdates();
    Flux<String> subscribeToBtcExchangeUpdates();
}
