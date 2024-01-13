package com.example.blorders.service.impl;

import com.example.blorders.domain.Order;
import com.example.blorders.domain.Status;
import com.example.blorders.dto.OrderDto;
import com.example.blorders.exc.EntityNotFoundException;
import com.example.blorders.mapper.OrderMapper;
import com.example.blorders.repository.OrderRepository;
import com.example.blorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Transactional
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;
    private final OrderMapper mapper;

    @Override
    public OrderDto save(OrderDto orderDto) {
        Order order = mapper.toEntity(orderDto);
        repository.save(order);
        return mapper.toDto(order);
    }

    @Override
    public List<OrderDto> findByUserId(UUID userId) {
        return mapper.toDtos(repository.findByUserId(userId));
    }

    @Override
    public void delete(UUID orderId) {
        Optional<Order> byId = repository.findById(orderId);
        if (byId.isPresent()){
            repository.deleteById(orderId);
        }else throw new EntityNotFoundException(orderId.toString());
    }

    @Override
    public OrderDto update(UUID id, OrderDto dto) {
        Optional<Order> byId = repository.findById(id);
        if (byId.isPresent()){
            Order order = byId.get();
            mapper.update(order, dto);
            return mapper.toDto(order);
        }else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public OrderDto getById(UUID id) {
        Optional<Order> byId = repository.findById(id);
        if (byId.isPresent()){
            Order order = byId.get();
            return mapper.toDto(order);
        }else throw new EntityNotFoundException(id.toString());
    }

    @Override
    public List<OrderDto> getAll() {
        return mapper.toDtos(repository.findAll());
    }

    @Override
    public List<OrderDto> getByStatus(Status status) {
        return mapper.toDtos(repository.findAllByStatus(status));
    }

}
