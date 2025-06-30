package com.diego.priceservice.domain.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PriceNotFoundException extends RuntimeException {

    private final Long productId;
    private final Long brandId;
    private final LocalDateTime date;

    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime date) {
        super(String.format("No price found for productId=%d, brandId=%d at %s", productId, brandId, date));
        this.productId = productId;
        this.brandId = brandId;
        this.date = date;
    }
}
