package com.diego.priceservice.infrastructure.config;

import com.diego.priceservice.application.usecase.GetApplicablePriceUseCaseImpl;
import com.diego.priceservice.domain.ports.out.PriceRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public GetApplicablePriceUseCaseImpl priceService(PriceRepositoryPort priceRepositoryPort) {
        return new GetApplicablePriceUseCaseImpl(priceRepositoryPort);
    }
}
