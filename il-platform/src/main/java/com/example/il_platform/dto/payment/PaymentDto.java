package com.example.il_platform.dto.payment;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class PaymentDto {
    private UUID id;
    private UUID orderId;
    private Date dateCreation;
}
