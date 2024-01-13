package com.example.il_platform.web;

import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.service.CourseService;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final CourseService courseService;

    @GetMapping
    public ModelAndView getHomePage() {
        ModelAndView modelAndView = new ModelAndView("home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("teachers", userService.teachers());
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.addObject("allFeedbacks", userService.getAllFeedbacks());
        return modelAndView;
    }
}
