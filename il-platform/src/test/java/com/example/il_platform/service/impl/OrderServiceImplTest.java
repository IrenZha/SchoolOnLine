package com.example.il_platform.service.impl;

import com.example.il_platform.client.CourseClient;
import com.example.il_platform.client.OrderClient;
import com.example.il_platform.client.PaymentClient;
import com.example.il_platform.client.UserClient;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.order.Status;
import com.example.il_platform.dto.payment.PaymentDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.CourseOrderedException;;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static com.example.il_platform.dto.users.RoleEnum.ROLE_STUDENT;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private CourseClient courseClient;
    @Mock
    private UserClient userClient;
    @Mock
    private OrderClient orderClient;
    @Mock
    private PaymentClient paymentClient;
    @Mock
    private UserServiceImpl userService;
    @InjectMocks
    private OrderServiceImpl service;

    @Test
    public void create() {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setAuthorId(UUID.randomUUID());
        courseDto.setPrice(0.0);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setCourseId(UUID.randomUUID());
        List<OrderDto> ordersByUser = List.of(orderDto1);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.getById(userId)).thenReturn(userDto);
        Mockito.when(orderClient.getByUser(userId)).thenReturn(ordersByUser);
        Mockito.when(userService.isPresentRole(userDto, ROLE_STUDENT)).thenReturn(true);
        Mockito.when(orderClient.create(orderDto)).thenReturn(orderDto);

        OrderDto result = service.create(orderDto);

        Assertions.assertEquals(userId, result.getUserId());
    }

    @Test
    public void createRoleIsPresent() {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setAuthorId(UUID.randomUUID());
        courseDto.setPrice(0.0);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setCourseId(UUID.randomUUID());
        List<OrderDto> ordersByUser = List.of(orderDto1);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.getById(userId)).thenReturn(userDto);
        Mockito.when(orderClient.getByUser(userId)).thenReturn(ordersByUser);
        Mockito.when(userService.isPresentRole(userDto, ROLE_STUDENT)).thenReturn(false);
        Mockito.when(orderClient.create(orderDto)).thenReturn(orderDto);

        OrderDto result = service.create(orderDto);

        Assertions.assertEquals(userId, result.getUserId());
    }

    @Test
    public void createPriceNotNull() {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setAuthorId(UUID.randomUUID());
        courseDto.setPrice(100.0);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setCourseId(UUID.randomUUID());
        List<OrderDto> ordersByUser = List.of(orderDto1);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.getById(userId)).thenReturn(userDto);
        Mockito.when(orderClient.getByUser(userId)).thenReturn(ordersByUser);
        Mockito.when(userService.isPresentRole(userDto, ROLE_STUDENT)).thenReturn(false);
        Mockito.when(orderClient.create(orderDto)).thenReturn(orderDto);

        OrderDto result = service.create(orderDto);

        Assertions.assertEquals(userId, result.getUserId());
    }

    @Test
    public void createExc() {
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setAuthorId(userId);
        courseDto.setPrice(0.0);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        OrderDto orderDto1 = new OrderDto();
        orderDto1.setCourseId(UUID.randomUUID());
        List<OrderDto> ordersByUser = List.of(orderDto1);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.getById(userId)).thenReturn(userDto);
        Mockito.when(orderClient.getByUser(userId)).thenReturn(ordersByUser);


        Assertions.assertThrows(CourseOrderedException.class, () -> service.create(orderDto));
    }


    @Test
    public void payStatusOrdered() {
        UUID orderId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setStatus(Status.UNLOCKED);
        Mockito.when(orderClient.getById(orderId)).thenReturn(orderDto);

        PaymentDto result = service.pay(orderId);

        Assertions.assertNull(result);
    }

    @Test
    public void payIsFalse() {
        UUID orderId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        orderDto.setStatus(Status.ORDERED);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setPrice(100.0);
        courseDto.setAuthorId(UUID.randomUUID());
        Mockito.when(orderClient.getById(orderId)).thenReturn(orderDto);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.pay(userId, courseDto.getAuthorId(), 100.0)).thenReturn(false);

        PaymentDto result = service.pay(orderId);

        Assertions.assertNull(result);
    }

    @Test
    public void payIsTrue() {
        UUID orderId = UUID.randomUUID();
        UUID courseId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setCourseId(courseId);
        orderDto.setUserId(userId);
        orderDto.setStatus(Status.ORDERED);
        CourseDto courseDto = new CourseDto();
        courseDto.setId(courseId);
        courseDto.setPrice(100.0);
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setOrderId(orderId);
        courseDto.setAuthorId(UUID.randomUUID());
        Mockito.when(orderClient.getById(orderId)).thenReturn(orderDto);
        Mockito.when(courseClient.getById(courseId)).thenReturn(courseDto);
        Mockito.when(userClient.pay(userId, courseDto.getAuthorId(), 100.0)).thenReturn(true);
        Mockito.when(paymentClient.create(orderId)).thenReturn(paymentDto);
        Mockito.when(orderClient.update(orderId, orderDto)).thenReturn(orderDto);

        PaymentDto result = service.pay(orderId);

        Assertions.assertEquals(orderId, result.getOrderId());
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        Mockito.when(orderClient.getById(id)).thenReturn(orderDto);

        OrderDto result = service.getById(id);

        Assertions.assertEquals(id, result.getId());

    }

    @Test
    public void getByUser() {
        UUID userId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        List<OrderDto> orders = List.of(orderDto);
        Mockito.when(orderClient.getByUser(userId)).thenReturn(orders);

        List<OrderDto> result = service.getByUser(userId);

        Assertions.assertEquals(1, result.size());
    }

    @Test
    public void getPayments() {
        PaymentDto paymentDto = new PaymentDto();
        List<PaymentDto> payments = List.of(paymentDto);
        Mockito.when(paymentClient.getAll()).thenReturn(payments);

        List<PaymentDto> result = service.getPayments();

        Assertions.assertEquals(1, result.size());
    }
}