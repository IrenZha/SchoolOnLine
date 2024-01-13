package com.example.blpayment.service.impl;

import com.example.blpayment.domain.Payment;
import com.example.blpayment.dto.PaymentDto;
import com.example.blpayment.exc.EntityNotFoundException;
import com.example.blpayment.mapper.PaymentMapper;
import com.example.blpayment.repository.PaymentRepository;
import com.example.blpayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    @Override
    public PaymentDto create(UUID orderId) {
        return mapper.toDto(repository.save(mapper.toEntity(orderId)));
    }

    @Override
    public void delete(UUID id) {
        Optional<Payment> byId = repository.findById(id);
        if (byId.isPresent()) {
            repository.deleteById(id);
        } else throw new EntityNotFoundException();
    }

    @Override
    public PaymentDto getByOrderId(UUID orderId) {
        return mapper.toDto(repository.findByOrderId(orderId));
    }

    @Override
    public List<PaymentDto> getAll() {
        return mapper.toDtos(repository.findAll());
    }
}
