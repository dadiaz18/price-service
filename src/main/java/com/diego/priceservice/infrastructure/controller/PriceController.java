package com.diego.priceservice.infrastructure.controller;

import com.diego.priceservice.application.usecase.GetApplicablePriceUseCaseImpl;
import com.diego.priceservice.domain.exception.PriceNotFoundException;
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

        var price = priceService.getApplicablePrice(productId, brandId, date)
                .orElseThrow(() -> new PriceNotFoundException(productId, brandId, date));

        return ResponseEntity.ok(price);
    }
}
