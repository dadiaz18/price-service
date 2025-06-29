package com.diego.priceservice.domain.ports.out;

import com.diego.priceservice.domain.model.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepositoryPort {
    List<Price> findByProductBrandAndDate(Long productId, Long brandId, LocalDateTime date);
}