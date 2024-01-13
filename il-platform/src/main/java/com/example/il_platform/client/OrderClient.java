package com.example.il_platform.client;

import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.order.Status;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-client", url = "${client.orders}")
public interface OrderClient {
    @GetMapping
    List<OrderDto> getAll();

    @PostMapping
    OrderDto create(@RequestBody OrderDto orderDto);

    @PostMapping("/{id}")
    OrderDto update(@PathVariable(name = "id") UUID id, @RequestBody OrderDto order);

    @GetMapping("/byStatus")
    List<OrderDto> getByStatus(@RequestParam(name = "status") Status status);

    @GetMapping("/{id}")
    OrderDto getById(@PathVariable(name = "id") UUID id);

    @GetMapping("/byUser")
    List<OrderDto> getByUser(@RequestParam("userId") UUID userId);
}
