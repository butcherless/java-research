package dev.cmartin.learn.mapper;

import dev.cmartin.learn.aggregate.BusinessService.Order;

public class OrderMapper {
    public static Order toDomain(final OrderDTO dto) {
        return new Order(
                dto.getId(),
                dto.getSupplierId(),
                dto.getOrderNumber()
        );
    }

    public static OrderDTO toDto(final Order order) {
        return OrderDTO.builder()
                .id(order.id())
                .supplierId(order.supplierId())
                .orderNumber(order.orderNumber())
                .build();
    }
}
