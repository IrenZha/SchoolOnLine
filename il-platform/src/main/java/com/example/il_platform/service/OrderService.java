package com.example.il_platform.service;

import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.payment.PaymentDto;
import com.example.il_platform.dto.users.RoleEnum;
import com.example.il_platform.dto.users.UserDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderDto create(OrderDto orderDto);

    PaymentDto pay(UUID orderId);

    OrderDto getById(UUID orderId);

    List<OrderDto> getByUser(UUID userId);

    List<PaymentDto> getPayments();

}
