package com.example.blorders.web;

import com.example.blorders.domain.Status;
import com.example.blorders.dto.OrderDto;
import com.example.blorders.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public OrderDto create(@RequestBody OrderDto orderDto) {
        return service.save(orderDto);
    }

    @PostMapping("/{id}")
    public OrderDto update(@PathVariable(name = "id") UUID id, @RequestBody OrderDto order) {
        return service.update(id, order);
    }

    @GetMapping("/byStatus")
    public List<OrderDto> getByStatus(@RequestParam(name = "status") Status status) {
        return service.getByStatus(status);
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @GetMapping("/byUser")
    public List<OrderDto> getByUser(@RequestParam("userId")UUID userId){
        return service.findByUserId(userId);
    }
}
