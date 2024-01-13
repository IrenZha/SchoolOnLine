package com.example.blorders.service;

import com.example.blorders.domain.Status;
import com.example.blorders.dto.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    void delete(UUID orderId);

    OrderDto update(UUID id, OrderDto dto);

    OrderDto getById(UUID id);

    List<OrderDto> getAll();

    List<OrderDto> getByStatus(Status status);

    OrderDto save(OrderDto dto);

    List<OrderDto> findByUserId(UUID userId);
}
