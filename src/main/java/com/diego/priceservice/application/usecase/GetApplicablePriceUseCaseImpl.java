package com.diego.priceservice.application.usecase;

import com.diego.priceservice.domain.model.Price;
import com.diego.priceservice.domain.ports.in.GetApplicablePriceUseCase;
import com.diego.priceservice.domain.ports.out.PriceRepositoryPort;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GetApplicablePriceUseCaseImpl implements GetApplicablePriceUseCase {

    private final PriceRepositoryPort priceRepositoryPort;

    public GetApplicablePriceUseCaseImpl(PriceRepositoryPort priceRepositoryPort) {
        this.priceRepositoryPort = priceRepositoryPort;
    }

    @Override
    public Optional<Price> getApplicablePrice(Long productId, Long brandId, LocalDateTime date) {
        List<Price> prices = priceRepositoryPort.findByProductBrandAndDate(productId, brandId, date);
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
