package com.example.blpayment.service;

import com.example.blpayment.domain.Payment;
import com.example.blpayment.dto.PaymentDto;
import com.example.blpayment.exc.EntityNotFoundException;
import com.example.blpayment.mapper.PaymentMapperImpl;
import com.example.blpayment.repository.PaymentRepository;
import com.example.blpayment.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
    @Mock
    private PaymentRepository repository;

    @Mock
    private PaymentMapperImpl mapper;

    @InjectMocks
    private PaymentServiceImpl service;

    @Test
    void create() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Payment payment = new Payment(id, orderId, new Date());
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(payment);
        Mockito.when(repository.save(payment)).thenReturn(payment);
        Mockito.when(mapper.toDto(payment)).thenCallRealMethod();

        PaymentDto result = service.create(orderId);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals(orderId, result.getOrderId());
    }

    @Test
    void delete() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Payment payment = new Payment(id, orderId, new Date());
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(payment));

        service.delete(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    void deleteExc() {
        UUID orderId = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.delete(orderId));
    }

    @Test
    void getByOrderId() {
        UUID orderId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setId(id);

        Mockito.when(repository.findByOrderId(orderId)).thenReturn(payment);
        Mockito.when(mapper.toDto(payment)).thenCallRealMethod();

        PaymentDto result = service.getByOrderId(orderId);

        Assertions.assertEquals(id, result.getId());
    }


    @Test
    void getAll() {
        Payment payment = new Payment();
        List<Payment> payments = List.of(payment);
        Mockito.when(repository.findAll()).thenReturn(payments);
        Mockito.when(mapper.toDtos(payments)).thenCallRealMethod();

        List<PaymentDto> result = service.getAll();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(payments.get(0), payment);
    }
}