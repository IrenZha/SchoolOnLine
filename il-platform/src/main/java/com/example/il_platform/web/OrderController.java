package com.example.il_platform.web;

import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.payment.PaymentDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.service.CourseService;
import com.example.il_platform.service.OrderService;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CourseService courseService;
    private final UserService userService;

    @PostMapping
    public ModelAndView create(@ModelAttribute OrderDto orderDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        UUID userId = user.getId();
        orderDto.setUserId(userId);
        orderService.create(orderDto);
        List<OrderDto> orders = orderService.getByUser(userId);
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("user", user);
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        return modelAndView;
    }


    @PostMapping("/pay")
    public ModelAndView pay(@RequestParam(name = "orderId") UUID orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("account");
        PaymentDto payment = orderService.pay(orderId);
        modelAndView.addObject("payment", payment);
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        return modelAndView;
    }

}
