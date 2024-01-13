package com.example.blpayment.web;

import com.example.blpayment.dto.PaymentDto;
import com.example.blpayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    public PaymentDto create(@RequestParam UUID orderId) {
        return service.create(orderId);
    }

    @GetMapping
    public PaymentDto getByOrderId(@RequestParam(name = "orderId")UUID orderId){
      return   service.getByOrderId(orderId);
    }

    @GetMapping("/all")
    public List<PaymentDto> getAll(){
        return service.getAll();
    }
}
