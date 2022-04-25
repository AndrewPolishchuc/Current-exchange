package org.andriipolishchuk.currentexchange.config;

import lombok.extern.slf4j.Slf4j;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;


@Configuration
@Slf4j
public class CaffeineCacheConfiguration extends CachingConfigurerSupport {
    @Bean(name = "UsdExchangeDataCache")
    public Cache<String, ExchangeUsdDataDto> cryptoFullDataCache() {
        return buildCaffeineCacheInstance(100);
    }

    private Cache<String, ExchangeUsdDataDto> buildCaffeineCacheInstance(int size) {
        return Caffeine
                .newBuilder()
                .maximumSize(size)
                .removalListener(
                        (String key, ExchangeUsdDataDto exchangeUsdDataDto, RemovalCause cause)
                                -> log.trace("Key {} was removed from FullDataCache: {}", key, cause)
                )
                .build();
    }
}
