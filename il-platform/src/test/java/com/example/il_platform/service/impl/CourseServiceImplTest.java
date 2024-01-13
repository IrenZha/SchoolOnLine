package com.example.il_platform.service.impl;

import com.example.il_platform.client.AvatarClient;
import com.example.il_platform.client.CourseClient;
import com.example.il_platform.client.FeedbackClient;
import com.example.il_platform.dto.avatar.AvatarDto;
import com.example.il_platform.dto.course.CategoryDto;
import com.example.il_platform.dto.course.Complexity;
import com.example.il_platform.dto.course.CourseDto;
import com.example.il_platform.dto.feedback.FeedbackDto;
import com.example.il_platform.dto.users.UserDto;
import com.example.il_platform.exc.CoursePriceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {
    @Mock
    private CourseClient client;
    @Mock
    private AvatarClient avatarClient;
    @Mock
    private FeedbackClient feedbackClient;
    @InjectMocks
    private CourseServiceImpl service;

    @Test
    public void save() {
        CourseDto courseDto = new CourseDto();
        courseDto.setPrice(200.0);

        service.save(courseDto);

        Mockito.verify(client, Mockito.times(1)).save(courseDto);
    }

    @Test
    public void saveExc() {
        CourseDto courseDto = new CourseDto();
        courseDto.setPrice(-200.0);

        Assertions.assertThrows(CoursePriceException.class, () -> service.save(courseDto));
    }

    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        CourseDto course = new CourseDto();
        course.setId(id);
        Mockito.when(client.getById(id)).thenReturn(course);

        CourseDto result = service.getById(id);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void update() {
        UUID id = UUID.randomUUID();
        CourseDto course = new CourseDto();
        course.setPrice(100.0);
        Mockito.when(client.update(id, course)).thenReturn(course);

        CourseDto result = service.update(id, course);

        Assertions.assertEquals(100, result.getPrice());

    }

    @Test
    public void getAllCourses() {
        CourseDto course1 = new CourseDto();
        CourseDto course2 = new CourseDto();
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.getAllCourses()).thenReturn(courses);

        List<CourseDto> result = service.getAllCourses();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void getAllCategories(){
        CategoryDto category = new CategoryDto();
        List<CategoryDto> categories = List.of(category);
        Mockito.when(client.getAllCategories()).thenReturn(categories);

        List<CategoryDto> result = service.getAllCategories();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(category, result.get(0));
    }

    @Test
    public void setAvatar(){
        String fileName = "fileName";
        String contentType = "fileType";
        String content = "Hello, World!";
        MultipartFile file = new MockMultipartFile(fileName, fileName, contentType, content.getBytes());
        String fileId = UUID.randomUUID().toString();
        AvatarDto avatar = new AvatarDto(fileId, fileName, contentType, content.getBytes(),null);
        UUID id = UUID.randomUUID();
        CourseDto course = new CourseDto();
        course.setId(id);
        Mockito.when(avatarClient.uploadFile(file)).thenReturn(avatar);
        Mockito.when(client.getById(id)).thenReturn(course);
        Mockito.when(client.update(id, course)).thenReturn(course);

        CourseDto result = service.setAvatar(id, file);

        Assertions.assertEquals(id, result.getId());
    }

    @Test
    public void getFreeCourses(){
        CourseDto course1 = new CourseDto();
        course1.setPrice(0.0);
        CourseDto course2 = new CourseDto();
        course2.setPrice(0.0);
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.getFreeCourses()).thenReturn(courses);

        List<CourseDto> result = service.getFreeCourses();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void search(){
        String search = "search";
        CourseDto course1 = new CourseDto();
        course1.setTitle(search);
        CourseDto course2 = new CourseDto();
        course2.setDescription(search);
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.search(search)).thenReturn(courses);

        List<CourseDto> result = service.search(search);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void findAllByComplexity(){
        CourseDto course1 = new CourseDto();
        course1.setComplexity(Complexity.EASY);
        CourseDto course2 = new CourseDto();
        course2.setComplexity(Complexity.EASY);
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.findAllByComplexity(Complexity.EASY)).thenReturn(courses);

        List<CourseDto> result = service.findAllByComplexity(Complexity.EASY);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void findAllByCategory(){
        UUID id = UUID.randomUUID();
        CategoryDto category = new CategoryDto(id,"category");
        CourseDto course1 = new CourseDto();
        course1.setCategory(category);
        CourseDto course2 = new CourseDto();
        course2.setCategory(category);
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.findAllByCategory(id)).thenReturn(courses);

        List<CourseDto> result = service.findAllByCategory(id);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void getAllByAuthor(){
        UUID id = UUID.randomUUID();
        UserDto author = new UserDto();
        author.setId(id);
        CourseDto course1 = new CourseDto();
        course1.setAuthorId(id);
        CourseDto course2 = new CourseDto();
        course2.setAuthorId(id);
        List<CourseDto> courses = List.of(course1, course2);
        Mockito.when(client.findByAuthorId(id)).thenReturn(courses);

        List<CourseDto> result = service.getAllByAuthor(id);

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(course1, result.get(0));
        Assertions.assertEquals(course2, result.get(1));
    }

    @Test
    public void getAllFeedbacksByCourse(){
        FeedbackDto feedback = new FeedbackDto();
        List<FeedbackDto> feedbacks = List.of(feedback);
        UUID courseId = UUID.randomUUID();
        Mockito.when(feedbackClient.getAllByCourseId(courseId)).thenReturn(feedbacks);

        List<FeedbackDto> result = service.getAllFeedbacksByCourse(courseId);

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(feedback, result.get(0));
    }

    @Test
    public void countRatingByCourse(){
        UUID courseId = UUID.randomUUID();
        Mockito.when(feedbackClient.countRatingByCourse(courseId)).thenReturn(4.5);

        Double result = service.countRatingByCourse(courseId);

        Assertions.assertEquals(4.5, result);
    }
}