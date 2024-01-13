package com.example.blpayment.service;

import com.example.blpayment.dto.PaymentDto;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentDto create(UUID orderId);

    void delete(UUID id);

    PaymentDto getByOrderId(UUID orderId);

    List<PaymentDto> getAll();
}
