package dev.cmartin.learn.mapper;

import dev.cmartin.learn.aggregate.BusinessService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class MapperTest {

    @Test
    void shouldMapDtoToDomain() {
        final var orderId = UUID.randomUUID();
        final var supplierId = UUID.randomUUID();
        final var orderNumber = "orderNumber1";

        final var dto = OrderDTO.builder()
                .id(orderId)
                .supplierId(supplierId)
                .orderNumber(orderNumber)
                .build();

        final var order = OrderMapper.toDomain(dto);

        assertThat(order.id()).isEqualTo(dto.getId());
        assertThat(order.supplierId()).isEqualTo(dto.getSupplierId());
        assertThat(order.orderNumber()).isEqualTo(dto.getOrderNumber());
    }

    @Test
    void shouldMapDomainToDto() {
        final var orderId = UUID.randomUUID();
        final var supplierId = UUID.randomUUID();
        final var orderNumber = "orderNumber1";

        final var order = new BusinessService.Order(
                orderId,
                supplierId,
                orderNumber
        );

        final var dto = OrderMapper.toDto(order);

        assertThat(dto.getId()).isEqualTo(order.id());
        assertThat(dto.getSupplierId()).isEqualTo(order.supplierId());
        assertThat(dto.getOrderNumber()).isEqualTo(order.orderNumber());
    }

}
