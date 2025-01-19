package dev.cmartin.learn.mapper;

import lombok.*;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID supplierId;
    private String orderNumber;

    public OrderDTO copy(
            UUID id,
            UUID supplierId,
            String orderNumber
    ) {
        return OrderDTO.builder()
                .id(id != null ? id : this.id)
                .supplierId(supplierId != null ? supplierId : this.supplierId)
                .orderNumber(orderNumber != null ? orderNumber : this.orderNumber)
                .build();
    }
}


