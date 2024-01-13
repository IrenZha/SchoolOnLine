package com.example.il_platform.web;

import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.dto.order.OrderDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.service.CourseService;
import com.example.il_platform.service.OrderService;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final OrderService orderService;
    private final CourseService courseService;
    @GetMapping("/reg")
    public ModelAndView getReg() {
        return new ModelAndView("registration");
    }

    @PostMapping("/reg")
    public ModelAndView save(@ModelAttribute(name = "user") UserDto user) {
        userService.save(user);
        return new ModelAndView("auth");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("auth");
    }

    @GetMapping("/account")
    public ModelAndView getAccount() {
        ModelAndView modelAndView = new ModelAndView("account");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        List<OrderDto> orders = orderService.getByUser(user.getId());
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orders);
        modelAndView.addObject("authorsCourses", courseService.getAllByAuthor(user.getId()));
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        return modelAndView;
    }


    @PostMapping("/avatar")
    public ModelAndView setAvatar(@RequestPart("file") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        userService.setAvatar(user, file);
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders",  orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        return modelAndView;
    }
    @PostMapping("/update")
    public ModelAndView updateUser(@ModelAttribute UserDto userDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        UserDto updated = userService.update(user.getId(), userDto);
        ModelAndView modelAndView = new ModelAndView("account");
        modelAndView.addObject("user", updated);
        modelAndView.addObject("orders",  orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        return modelAndView;
    }

    @PostMapping("/wallet")
    public ModelAndView setWallet(@RequestParam("sum")Double sum){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("account");
        UserDto updated = userService.setWallet(user, sum);
        modelAndView.addObject("user", updated);
        modelAndView.addObject("orders",  orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        return modelAndView;
    }

    @PostMapping("/feedbackUpdate")
    public ModelAndView feedbackUpdate(@ModelAttribute(name = "feedbackDto") FeedbackDto feedbackDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("account");
        userService.feedbackUpdate(feedbackDto.getId(), feedbackDto);
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders",  orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        return modelAndView;

    }
}