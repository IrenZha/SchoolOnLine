package com.example.bl_courses.servise.impl;

import com.example.bl_courses.domain.Category;
import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.domain.Course;

import com.example.bl_courses.dto.CategoryDto;
import com.example.bl_courses.dto.CourseDto;
import com.example.bl_courses.exc.EntityNotFoundException;
import com.example.bl_courses.mapper.CourseMapperImpl;
import com.example.bl_courses.repository.CategoryRepository;
import com.example.bl_courses.repository.CourseRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CourseMapperImpl courseMapper;
    @InjectMocks
    private CourseServiceImpl courseService;

    @Test
    public void save() {
        CourseDto courseDto = new CourseDto();
        Course course = new Course();
        Mockito.when(courseMapper.toEntity(courseDto)).thenReturn(course);

        courseService.save(courseDto);

        Mockito.verify(courseRepository, Mockito.times(1)).save(Mockito.any(Course.class));
    }

    @Test
    public void getByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> courseService.getById(id));
    }


    @Test
    public void getById() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, null, null, "title", "description", 0.0, null, null);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));
        Mockito.when(courseMapper.toDto(course)).thenCallRealMethod();

        CourseDto result = courseService.getById(id);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("title", result.getTitle());
    }

    @Test
    public void deleteCourse() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, null, null, "title", "description", 0.0, null, null);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        courseService.deleteCourse(id);

        Mockito.verify(courseRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    public void deleteExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourse(id));
    }

    @Test
    public void update1() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, null, null, "title", "description", 0.0, null, null);
        CourseDto courseDto = new CourseDto(null, null, null, "title1", "description1", 100.0, null, null);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));


        courseService.update(id, courseDto);

        Mockito.verify(courseMapper, Mockito.times(1)).update(course, courseDto);
    }

    @Test
    public void update2() {
        UUID id = UUID.randomUUID();
        Course course = new Course(id, null, null, "title", "description", 0.0, null, null);
        CourseDto courseDto = new CourseDto(null, null, null, "title1", "description1", 100.0, null, null);
        Mockito.when(courseRepository.findById(id)).thenReturn(Optional.of(course));

        ArgumentCaptor<CourseDto> argumentCaptor = ArgumentCaptor.forClass(CourseDto.class);
        courseService.update(id, courseDto);

        Mockito.verify(courseMapper).update(Mockito.any(), argumentCaptor.capture());
        CourseDto value = argumentCaptor.getValue();

        Assertions.assertEquals("title1", value.getTitle());
        Assertions.assertEquals("description1", value.getDescription());
        Assertions.assertEquals(100.0, value.getPrice());
    }

    @Test
    public void getAll() {
        Course course1 = new Course();
        Course course2 = new Course();
        Course course3 = new Course();
        List<Course> courses = Arrays.asList(course1, course2, course3);

        Mockito.when(courseRepository.findAll()).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.getAll();

        Assertions.assertEquals(3, result.size());
    }

    @Test
    public void getCategoryById() {
        UUID id = UUID.randomUUID();
        Category category = new Category(id, "title");
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        Mockito.when(courseMapper.toCategoryDto(category)).thenCallRealMethod();

        CategoryDto result = courseService.getCategoryById(id);

        Assertions.assertEquals(id, result.getId());
        Assertions.assertEquals("title", result.getCategoryTitle());
    }

    @Test
    public void getCategoryByIdExc() {
        UUID id = UUID.randomUUID();

        Assertions.assertThrows(EntityNotFoundException.class, () -> courseService.getCategoryById(id));
    }

    @Test
    public void getAllCategories() {
        Category category1 = new Category();
        Category category2 = new Category();
        List<Category> categories = Arrays.asList(category1, category2);
        Mockito.when(categoryRepository.findAll()).thenReturn(categories);
        Mockito.when(courseMapper.toCategoryDtos(categories)).thenCallRealMethod();

        List<CategoryDto> result = courseService.getAllCategories();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findAllByPriceIs() {
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setPrice(0.0);
        course2.setPrice(0.0);
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository.findAllByPrice(0.0)).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.findAllByPriceIs();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findAllByComplexity() {
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setComplexity(Complexity.EASY);
        course2.setComplexity(Complexity.EASY);
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository.findAllByComplexity(Complexity.EASY)).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.findAllByComplexity(Complexity.EASY);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void searchIsNull() {
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository.findAll()).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.search(null);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void search() {
        String search = "search";
        Course course1 = new Course();
        Course course2 = new Course();
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository
                        .findAllByTitleContainsOrDescriptionContains(search, search))
                .thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.search(search);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findByAuthorId() {
        UUID authorId = UUID.randomUUID();
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setAuthorId(authorId);
        course2.setAuthorId(authorId);
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository.findAllByAuthorId(authorId)).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();

        List<CourseDto> result = courseService.findByAuthorId(authorId);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findAllByCategory() {
        UUID id = UUID.randomUUID();
        Category category = new Category(id, "text");
        Course course1 = new Course();
        Course course2 = new Course();
        course1.setCategory(category);
        course2.setCategory(category);
        List<Course> courses = Arrays.asList(course1, course2);
        Mockito.when(courseRepository.findAllByCategory(category)).thenReturn(courses);
        Mockito.when(courseMapper.toDtos(courses)).thenCallRealMethod();
        Mockito.when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));

        List<CourseDto> result = courseService.findAllByCategory(category.getId());

        Assertions.assertEquals(2, result.size());
    }

    @Test
    public void findAllByCategoryRxc() {

        Assertions.assertThrows(EntityNotFoundException.class, () -> courseService.findAllByCategory(UUID.randomUUID()));
    }
}