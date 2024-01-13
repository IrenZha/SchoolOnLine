package com.example.blorders.service.impl;

import com.example.blorders.domain.Order;
import com.example.blorders.domain.Status;
import com.example.blorders.dto.OrderDto;
import com.example.blorders.exc.EntityNotFoundException;
import com.example.blorders.mapper.OrderMapperImpl;
import com.example.blorders.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository repository;

    @Mock
    private OrderMapperImpl mapper;

    @InjectMocks
    private OrderServiceImpl service;

    @Test
    public void save() {
        Order order = new Order(UUID.randomUUID(), null, null, Status.UNLOCKED, null, null);
        Mockito.when(mapper.toEntity(Mockito.any())).thenReturn(order);
        Mockito.when(repository.save(order)).thenReturn(order);
        Mockito.when(mapper.toDto(order)).thenCallRealMethod();

        OrderDto result = service.save(Mockito.any(OrderDto.class));

        assertEquals(Status.UNLOCKED, result.getStatus());
    }

    @Test
    public void delete() {
        UUID id = UUID.randomUUID();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(order));

        service.delete(id);

        Mockito.verify(repository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void deleteExc() {
        UUID id = UUID.randomUUID();


        Assertions.assertThrows(EntityNotFoundException.class, () -> service.delete(id));
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(order));
        Mockito.when(mapper.toDto(order)).thenCallRealMethod();

        OrderDto result = service.getById(id);

        assertEquals(id, result.getId());
        assertEquals(Status.UNLOCKED, result.getStatus());
    }

    @Test
    public void getByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(id));
    }

    @Test
    public void getAll() {
        UUID id = UUID.randomUUID();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        List<Order> orders = List.of(order);
        Mockito.when(repository.findAll()).thenReturn(orders);
        Mockito.when(mapper.toDtos(orders)).thenCallRealMethod();

        List<OrderDto> result = service.getAll();

        assertEquals(1, result.size());
    }

    @Test
    public void updateExc() {
        UUID id = UUID.randomUUID();
        OrderDto order = new OrderDto();

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.update(id, order));
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        Mockito.when(repository.findById(id)).thenReturn(Optional.of(order));
        Mockito.when(mapper.toDto(order)).thenReturn(orderDto);

        service.update(id, orderDto);

        Mockito.verify(mapper, Mockito.times(1)).update(order, orderDto);
    }

    @Test
    public void findByUserId() {
        UUID userId = UUID.randomUUID();
        UUID id = UUID.randomUUID();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        List<Order> orders = List.of(order);
        Mockito.when(repository.findByUserId(userId)).thenReturn(orders);
        Mockito.when(mapper.toDtos(orders)).thenCallRealMethod();

        List<OrderDto> result = service.findByUserId(userId);

        assertEquals(1, result.size());
    }

    @Test
    public void getByStatus(){
        UUID id = UUID.randomUUID();
        Order order = new Order(id, null, null, Status.UNLOCKED, null, null);
        List<Order> orders = List.of(order);
        Mockito.when(repository.findAllByStatus(Status.UNLOCKED)).thenReturn(orders);
        Mockito.when(mapper.toDtos(orders)).thenCallRealMethod();

        List<OrderDto> result = service.getByStatus(Status.UNLOCKED);

        assertEquals(1, result.size());
    }
}