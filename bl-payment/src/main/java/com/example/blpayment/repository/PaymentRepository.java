package com.example.blpayment.repository;

import com.example.blpayment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Payment findByOrderId(UUID orderId);
}
