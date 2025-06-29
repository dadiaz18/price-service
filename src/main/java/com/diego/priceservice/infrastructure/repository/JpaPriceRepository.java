package com.diego.priceservice.infrastructure.repository;

import com.diego.priceservice.domain.model.Price;
import com.diego.priceservice.domain.ports.out.PriceRepositoryPort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaPriceRepository implements PriceRepositoryPort {

    private final SpringDataPriceJpaRepository jpaRepository;

    public JpaPriceRepository(SpringDataPriceJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Price> findByProductBrandAndDate(Long productId, Long brandId, LocalDateTime date) {
        return jpaRepository.findApplicablePrices(productId, brandId, date).stream()
                .map(entity -> new Price(
                        entity.getBrandId(),
                        entity.getStartDate(),
                        entity.getEndDate(),
                        entity.getPriceList(),
                        entity.getProductId(),
                        entity.getPriority(),
                        entity.getPrice(),
                        entity.getCurrency()
                ))
                .toList();
    }
}
