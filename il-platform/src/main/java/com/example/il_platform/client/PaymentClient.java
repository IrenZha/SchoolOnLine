package com.example.il_platform.client;

import com.example.il_platform.dto.payment.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "payment-client", url = "${client.payment}")
public interface PaymentClient {
    @PostMapping
    PaymentDto create(@RequestParam UUID orderId);

    @GetMapping
    PaymentDto getByOrderId(@RequestParam(name = "orderId") UUID orderId);

    @GetMapping("/all")
   List<PaymentDto> getAll();
}
