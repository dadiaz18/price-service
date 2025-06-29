package com.diego.priceservice.infrastructure.controller;

import com.diego.priceservice.application.usecase.GetApplicablePriceUseCaseImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetApplicablePriceUseCaseImpl priceService;

    public PriceController(GetApplicablePriceUseCaseImpl priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<?> getPrice(
            @RequestParam("applicationDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam Long productId,
            @RequestParam Long brandId) {

        var optionalPrice = priceService.getApplicablePrice(productId, brandId, date);

        if (optionalPrice.isPresent()) {
            return ResponseEntity.ok(optionalPrice.get());
        } else {
            String message = String.format("No price found for productId=%d, brandId=%d at %s",
                    productId, brandId, date);
            return ResponseEntity.status(404).body(message);
        }
    }
}
