package com.example.blorders.mapper;

import com.example.blorders.domain.Order;
import com.example.blorders.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    Order toEntity(OrderDto dto);

    OrderDto toDto(Order order);

    List<OrderDto> toDtos(List<Order> orders);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    void update(@MappingTarget Order toUpdate, OrderDto dto);
}
