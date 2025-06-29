package com.diego.priceservice.application.usecase;

import com.diego.priceservice.domain.model.Price;
import com.diego.priceservice.domain.ports.out.PriceRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class GetApplicablePriceUseCaseImplTest {

    @Mock
    private PriceRepositoryPort priceRepositoryPort;

    private GetApplicablePriceUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        useCase = new GetApplicablePriceUseCaseImpl(priceRepositoryPort);
    }

    @Test
    @DisplayName("Returns empty when no match")
    void returnsEmptyWhenNoMatch() {
        when(priceRepositoryPort.findByProductBrandAndDate(anyLong(), anyLong(), any()))
                .thenReturn(Collections.emptyList());

        Optional<Price> result = useCase.getApplicablePrice(1L, 1L, LocalDateTime.now());

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Returns highest priority price")
    void returnsHighestPriorityPrice() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price lowPriority = createPrice(1, 0);
        Price highPriority = createPrice(2, 1);

        when(priceRepositoryPort.findByProductBrandAndDate(productId, brandId, date))
                .thenReturn(Arrays.asList(lowPriority, highPriority));

        Optional<Price> result = useCase.getApplicablePrice(productId, brandId, date);

        assertTrue(result.isPresent());
        assertEquals(2, result.get().getPriceList().intValue());
        assertEquals(1, result.get().getPriority().intValue());
    }

    @Test
    @DisplayName("Returns single price")
    void returnsSinglePrice() {
        LocalDateTime date = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        Price price = createPrice(1, 0);

        when(priceRepositoryPort.findByProductBrandAndDate(productId, brandId, date))
                .thenReturn(Collections.singletonList(price));

        Optional<Price> result = useCase.getApplicablePrice(productId, brandId, date);

        assertTrue(result.isPresent());
        assertEquals(1, result.get().getPriceList().intValue());
    }

    private Price createPrice(int priceList, int priority) {
        return new Price(
                1L,
                LocalDateTime.now().minusHours(1),
                LocalDateTime.now().plusHours(1),
                priceList,
                35455L,
                priority,
                new BigDecimal("35.50"),
                "EUR"
        );
    }
}
