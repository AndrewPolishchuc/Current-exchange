package org.andriipolishchuk.currentexchange.config;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.classmate.types.ResolvedArrayType;
import org.andriipolishchuk.currentexchange.dto.ExchangeUsdDataDto;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import springfox.documentation.spring.web.readers.operation.HandlerMethodResolver;

import java.util.List;

@Configuration
public class WebFluxConfigurationTest {
    @Bean
    public WebClient getWebClient() {
        return WebClient.builder().build();
    }

    /**
     * This configuration needs to unwrap returned classes from Mono, Flux, and ResponseEntity
     * It allows Swagger to display entity class name correctly
     */
    @Bean
    @Primary
    public HandlerMethodResolver fluxMethodResolver(TypeResolver resolver) {
        return new HandlerMethodResolver(resolver) {
            @Override
            public ResolvedType methodReturnType(HandlerMethod handlerMethod) {
                ResolvedType retType = super.methodReturnType(handlerMethod);

                // Unwrapping
                while (
                        retType.getErasedType() == Mono.class ||
                                retType.getErasedType() == Flux.class ||
                                retType.getErasedType() == ResponseEntity.class
                ) {
                    ResolvedType type = retType.getTypeBindings().getBoundType(0);
                    if (retType.getErasedType() == Flux.class) {
                        // treat it as an array
                        retType = new ResolvedArrayType(type.getErasedType(), type.getTypeBindings(), type);
                    } else {
                        retType = type;
                    }
                }

                return retType;
            }
        };
    }

    @Bean
    public TypeResolver typeResolver() {
        return new TypeResolver();
    }

    @Bean(name = "symbolUpdatesSink")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Sinks.Many<List<ExchangeUsdDataDto>> symbolUpdatesSink() {
        return Sinks.many().multicast().onBackpressureBuffer(150, false);
    }
}
