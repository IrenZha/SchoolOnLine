package com.example.blpayment.mapper;

import com.example.blpayment.domain.Payment;
import com.example.blpayment.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDto toDto(Payment payment);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    Payment toEntity(UUID orderId);

    List<PaymentDto> toDtos(List<Payment> payments);
}

