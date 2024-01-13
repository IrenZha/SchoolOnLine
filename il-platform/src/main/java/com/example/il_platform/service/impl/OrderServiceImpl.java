package com.example.il_platform.service.impl;

import com.example.il_platform.client.CourseClient;
import com.example.il_platform.client.OrderClient;
import com.example.il_platform.client.PaymentClient;
import com.example.il_platform.client.UserClient;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.payment.PaymentDto;
import com.example.il_platform.dto.order.Status;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.CourseOrderedException;
import com.example.il_platform.service.OrderService;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import static com.example.il_platform.dto.users.RoleEnum.ROLE_STUDENT;


@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderClient orderClient;
    private final CourseClient courseClient;
    private final PaymentClient paymentClient;
    private final UserClient userClient;
    private final UserService userService;

    @Override
    public OrderDto create(OrderDto orderDto) {
        CourseDto course = courseClient.getById(orderDto.getCourseId());
        List<OrderDto> byUser = getByUser(orderDto.getUserId());
        UserDto user = userClient.getById(orderDto.getUserId());
        Optional<OrderDto> first = byUser
                .stream()
                .filter(order -> order.getCourseId()
                        .equals(orderDto.getCourseId()))
                .findFirst();
        if (first.isPresent()||course.getAuthorId().equals(user.getId())) {
            throw new CourseOrderedException(course.getId());
        } else {
            if (course.getPrice() == 0.00) {
                orderDto.setStatus(Status.UNLOCKED);
            } else {
                orderDto.setStatus(Status.ORDERED);
            }

            if (!userService.isPresentRole(user, ROLE_STUDENT)) {
                userClient.addRole(user.getId(), ROLE_STUDENT.name());
            }
            return orderClient.create(orderDto);
        }
    }

    @Override
    public PaymentDto pay(UUID orderId) {
        OrderDto order = orderClient.getById(orderId);
        if (order.getStatus().equals(Status.ORDERED)) {
            CourseDto course = courseClient.getById(order.getCourseId());
            Double price = course.getPrice();
            UUID authorId = course.getAuthorId();
            UUID studentId = order.getUserId();
            if (userClient.pay(studentId, authorId, price)) {
                PaymentDto payment = paymentClient.create(order.getId());
                order.setStatus(Status.UNLOCKED);
                orderClient.update(orderId, order);
                return payment;
            }
        }
        return null;
    }

    @Override
    public OrderDto getById(UUID orderId) {
        return orderClient.getById(orderId);
    }

    @Override
    public List<OrderDto> getByUser(UUID userId) {
        return orderClient.getByUser(userId);
    }

    @Override
    public List<PaymentDto> getPayments() {
        return paymentClient.getAll();
    }

}
