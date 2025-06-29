package com.diego.priceservice.domain.ports.in;

import com.diego.priceservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetApplicablePriceUseCase {
    Optional<Price> getApplicablePrice(Long productId, Long brandId, LocalDateTime date);
}
