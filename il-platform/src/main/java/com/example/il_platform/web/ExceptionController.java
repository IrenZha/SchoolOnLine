package com.example.il_platform.web;

import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.CourseOrderedException;
import com.example.il_platform.exc.CoursePriceException;
import com.example.il_platform.exc.PasswordMatchException;
import com.example.il_platform.exc.UsernameExistsException;
import com.example.il_platform.service.CourseService;
import com.example.il_platform.service.LessonService;
import com.example.il_platform.service.OrderService;
import com.example.il_platform.service.UserService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;


@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionController {
    private final UserService userService;
    private final CourseService courseService;
    private final OrderService orderService;
    private final LessonService lessonService;

    @ExceptionHandler(FeignException.NotFound.class)
    public ModelAndView clientNotFound(FeignException exc) {
        ModelAndView modelAndView = new ModelAndView("errorNotFound");
        String utf8 = exc.contentUTF8();
        modelAndView.addObject("utf8", utf8);
        return modelAndView;
    }

    @ExceptionHandler(FeignException.class)
    public ModelAndView notPaid() {
        ModelAndView modelAndView = new ModelAndView("account");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("orders", orderService.getByUser(user.getId()));
        modelAndView.addObject("allCourses", courseService.getAllCourses());
        modelAndView.addObject("feedbacks", userService.getAllFeedbacksByUserId(user.getId()));
        modelAndView.addObject("message", "Недостаточно средств");
        return modelAndView;
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ModelAndView usernameExists(UsernameExistsException exc) {
        ModelAndView modelAndView = new ModelAndView("registration");
        String username = exc.getUsername();
        modelAndView.addObject("username_message", username + " уже существует!");
        return modelAndView;
    }

    @ExceptionHandler(PasswordMatchException.class)
    public ModelAndView passwordMatch() {
        ModelAndView modelAndView = new ModelAndView("registration");
        modelAndView.addObject("password_message", "Пароли не совпадают");
        return modelAndView;
    }

    @ExceptionHandler(CoursePriceException.class)
    public ModelAndView priceNotValid() {
        ModelAndView modelAndView = new ModelAndView("courseCreate");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("allCourses", courseService.getAllByAuthor(user.getId()));
        modelAndView.addObject("categories", courseService.getAllCategories());
        modelAndView.addObject("priceError", "Цена не может быть меньше нуля!");
        return modelAndView;
    }

    @ExceptionHandler(CourseOrderedException.class)
    public ModelAndView courseOrdered(CourseOrderedException exc) {
        ModelAndView modelAndView = new ModelAndView("course");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        UUID courseId = exc.getCourseId();
        CourseDto course = courseService.getById(courseId);
        modelAndView.addObject("course", course);
        modelAndView.addObject("rating", courseService.countRatingByCourse(courseId));
        modelAndView.addObject("lessons", lessonService.getAll(courseId));
        modelAndView.addObject("feedbacks", courseService.getAllFeedbacksByCourse(courseId));
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.addObject("message", "Курс: " + course.getTitle()+ " уже приобретен");
        return modelAndView;
    }
}
