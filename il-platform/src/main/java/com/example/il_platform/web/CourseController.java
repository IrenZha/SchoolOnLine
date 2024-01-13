package com.example.il_platform.web;

import com.example.il_platform.dto.course.Complexity;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.lesson.LessonDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.service.CourseService;
import com.example.il_platform.service.LessonService;
import com.example.il_platform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/courses")
public class CourseController {
    private final CourseService service;
    private final LessonService lessonService;
    private final UserService userService;

    @GetMapping
    public ModelAndView getAllCourses() {
        List<CourseDto> allCourses = service.getAllCourses();
        ModelAndView modelAndView = new ModelAndView("allCourses");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("allCourses", allCourses);
        modelAndView.addObject("categories", service.getAllCategories());
        return modelAndView;
    }

    @GetMapping("/free")
    public ModelAndView getFreeCourses() {
        List<CourseDto> freeCourses = service.getFreeCourses();
        ModelAndView modelAndView = new ModelAndView("allCourses");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("freeCourses", freeCourses);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView search(@RequestParam(name = "search") String search) {
        List<CourseDto> courseList = service.search(search);
        ModelAndView modelAndView = new ModelAndView("allCourses");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("allCourses", service.getAllCourses());
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("courseList", courseList);
        return modelAndView;
    }

    @GetMapping("/complexity")
    public ModelAndView findAllByComplexity(@RequestParam("complexity") Complexity complexity) {
        List<CourseDto> allByComplexity = service.findAllByComplexity(complexity);
        ModelAndView modelAndView = new ModelAndView("allCourses");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("allByComplexity", allByComplexity);
        return modelAndView;
    }

    @GetMapping("/category")
    public ModelAndView findAllByCategory(@RequestParam(name = "categoryId") UUID categoryId) {
        List<CourseDto> allByCategory = service.findAllByCategory(categoryId);
        ModelAndView modelAndView = new ModelAndView("allCourses");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("allByCategory", allByCategory);
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView saveCourse(@ModelAttribute(name = "course") CourseDto course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        UserDto teacher = userService.becomeTeacher(user.getId());
        service.save(course);
        ModelAndView modelAndView = new ModelAndView("courseCreate");
        modelAndView.addObject("user", user);
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("allCourses", service.getAllByAuthor(teacher.getId()));
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView getAllCourses(@ModelAttribute(name = "course") CourseDto course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("courseCreate");
        modelAndView.addObject("user", user);
        modelAndView.addObject("allCourses", service.getAllByAuthor(user.getId()));
        modelAndView.addObject("categories", service.getAllCategories());
        return modelAndView;
    }

    @GetMapping("/detail/{courseId}")
    public ModelAndView getCourse(@PathVariable(name = "courseId") UUID courseId) {
        CourseDto course = service.getById(courseId);
        ModelAndView modelAndView = new ModelAndView("course");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.addObject("course", course);
        modelAndView.addObject("feedbacks", service.getAllFeedbacksByCourse(courseId));
        modelAndView.addObject("rating", service.countRatingByCourse(courseId));
        modelAndView.addObject("lessons", lessonService.getAll(courseId));
        return modelAndView;
    }


    @PostMapping("/avatar")
    public ModelAndView setAvatar(@RequestParam(name = "id") UUID id,
                                  @RequestPart("file") MultipartFile file,
                                  @ModelAttribute(name = "course") CourseDto course) {
        ModelAndView modelAndView = new ModelAndView("courseUpdate");
        service.setAvatar(id, file);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", course);
        modelAndView.addObject("categories", service.getAllCategories());
        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView courseUpdate(@ModelAttribute(name = "course") CourseDto course) {
        ModelAndView modelAndView = new ModelAndView("courseUpdate");
        CourseDto update = service.update(course.getId(), course);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", update);
        modelAndView.addObject("categories", service.getAllCategories());
        return modelAndView;
    }

    @GetMapping("/update")
    public ModelAndView courseUpdate(@RequestParam(name = "courseId") UUID courseId) {
        ModelAndView modelAndView = new ModelAndView("courseUpdate");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", service.getById(courseId));
        modelAndView.addObject("categories", service.getAllCategories());
        return modelAndView;
    }

    @PostMapping("/lessons")
    public ModelAndView saveLesson(@ModelAttribute(name = "lesson") LessonDto lessonDto) {
        ModelAndView modelAndView = new ModelAndView("lessons");
        CourseDto courseDto = service.getById(lessonDto.getCourseId());
        modelAndView.addObject("course", courseDto);
        lessonService.create(lessonDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("categories", service.getAllCategories());
        modelAndView.addObject("lessons", lessonService.getAll(lessonDto.getCourseId()));
        return modelAndView;
    }


    @GetMapping("/lessons")
    public ModelAndView getLessonsByCourse(@ModelAttribute(name = "lesson") LessonDto lesson) {
        ModelAndView modelAndView = new ModelAndView("lessons");
        CourseDto courseDto = service.getById(lesson.getCourseId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", courseDto);
        modelAndView.addObject("lessons", lessonService.getAll(lesson.getCourseId()));
        return modelAndView;
    }

    @GetMapping("/lessons/update")
    public ModelAndView lessonToUpdate(@RequestParam(name = "id") UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        ModelAndView modelAndView = new ModelAndView("lessonsUpdate");
        LessonDto lessonDto = lessonService.getById(id);
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", service.getById(lessonDto.getCourseId()));
        modelAndView.addObject("lesson", lessonDto);
        return modelAndView;
    }

    @PostMapping("/lessons/update")
    public ModelAndView lessonUpdate(@ModelAttribute(name = "lesson") LessonDto lesson) {
        ModelAndView modelAndView = new ModelAndView("lessons");
        LessonDto lessonDto = lessonService.update(lesson.getId(), lesson);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", service.getById(lesson.getCourseId()));
        modelAndView.addObject("lessons", lessonService.getAll(lesson.getCourseId()));
        return modelAndView;
    }

    @GetMapping("/lessons/view/{id}")
    public ModelAndView getLesson(@PathVariable(name = "id") UUID id) {
        ModelAndView modelAndView = new ModelAndView("sample");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        LessonDto lesson = lessonService.getById(id);
        modelAndView.addObject("lesson", lesson);
        return modelAndView;
    }
    @GetMapping("/lessons/byCourse/{courseId}")
    public ModelAndView lessonsByCourse(@PathVariable(name = "courseId")UUID courseId){
        ModelAndView modelAndView = new ModelAndView("lessonsView");
        CourseDto courseDto = service.getById(courseId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.findByUsername(authentication.getName());
        modelAndView.addObject("user", user);
        modelAndView.addObject("course", courseDto);
        modelAndView.addObject("lessons", lessonService.getAll(courseId));
        return modelAndView;
    }

}
