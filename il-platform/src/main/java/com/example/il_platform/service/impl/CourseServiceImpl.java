package com.example.il_platform.service.impl;

import com.example.il_platform.client.AvatarClient;
import com.example.il_platform.client.CourseClient;
import com.example.il_platform.client.FeedbackClient;
import com.example.il_platform.dto.course.CategoryDto;
import com.example.il_platform.dto.course.Complexity;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.exc.CoursePriceException;
import com.example.il_platform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseClient client;
    private final AvatarClient avatarClient;
    private final FeedbackClient feedbackClient;

    @Override
    public void save(CourseDto course) {
        if (course.getPrice() >= 0) {
            client.save(course);
        }else throw new CoursePriceException();

    }

    @Override
    public CourseDto getById(UUID id) {
        return client.getById(id);
    }

    @Override
    public CourseDto update(UUID id, CourseDto dto) {
        return client.update(id, dto);
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return client.getAllCourses();
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return client.getAllCategories();
    }

    @Override
    public CourseDto setAvatar(UUID id, MultipartFile file) {
        String fileId = avatarClient.uploadFile(file).getFileId();
        CourseDto course = client.getById(id);
        course.setAvatarId(fileId);
        return client.update(id, course);
    }

    @Override
    public List<CourseDto> getFreeCourses() {
        return client.getFreeCourses();
    }

    @Override
    public List<CourseDto> search(String search) {
        return client.search(search);
    }

    @Override
    public List<CourseDto> findAllByComplexity(Complexity complexity) {
        return client.findAllByComplexity(complexity);
    }

    @Override
    public List<CourseDto> findAllByCategory(UUID categoryId) {
        return client.findAllByCategory(categoryId);
    }

    @Override
    public Double countRatingByCourse(UUID courseId) {
        return feedbackClient.countRatingByCourse(courseId);
    }

    @Override
    public List<CourseDto> getAllByAuthor(UUID authorId) {
        return client.findByAuthorId(authorId);
    }

    @Override
    public List<FeedbackDto> getAllFeedbacksByCourse(UUID courseId) {
        return feedbackClient.getAllByCourseId(courseId);
    }
}
