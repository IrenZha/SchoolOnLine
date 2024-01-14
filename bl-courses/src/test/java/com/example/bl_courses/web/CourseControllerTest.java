package com.example.bl_courses.web;

import com.example.bl_courses.domain.Complexity;
import com.example.bl_courses.dto.CourseDto;
import com.example.bl_courses.servise.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

@WebMvcTest(CourseController.class)
class CourseControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CourseService courseService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAllCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void save() throws Exception {
        CourseDto courseDto = new CourseDto(null, null, null, "title1", "description1", 100.0, null, null);
        String valueAsString = objectMapper.writeValueAsString(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/courses")
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).save(courseDto);
    }

    @Test
    public void update() throws Exception {
        UUID id = UUID.randomUUID();
        CourseDto courseDto = new CourseDto(id, null, null, "title1", "description1", 100.0, null, null);
        String valueAsString = objectMapper.writeValueAsString(courseDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/courses/update/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).update(id,courseDto);
    }
    @Test
    public void getById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).getById(id);
    }

    @Test
    public void deleteCourse() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.delete("/courses/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).deleteCourse(id);
    }

    @Test
    public void findByAuthorId() throws Exception {
        UUID authorId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/author/{authorId}", authorId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).findByAuthorId(authorId);
    }

    @Test
    public void getFreeCourses() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/free"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).findAllByPriceIs();
    }

    @Test
    public void search() throws Exception {
        String search = "search";
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/search")
                        .param("search", search))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).search(search);
    }

    @Test
    public void findAllByComplexity() throws Exception {
        Complexity complexity = Complexity.EASY;
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/complexity")
                        .param("complexity", complexity.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).findAllByComplexity(complexity);
    }

    @Test
    public void getAllCategories() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/categories"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).getAllCategories();
    }
    @Test
    public void getCategoryById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/category/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).getCategoryById(id);
    }

    @Test
    public void findAllByCategory() throws Exception {
        UUID categoryId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/courses/category")
                        .param("categoryId", categoryId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(courseService, Mockito.times(1)).findAllByCategory(categoryId);
    }
}