package com.example.blorders.repository;

import com.example.blorders.domain.Order;
import com.example.blorders.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByStatus(Status status);
    List<Order> findByCourseId(UUID courseId);
    List<Order> findByUserId(UUID userId);
}
