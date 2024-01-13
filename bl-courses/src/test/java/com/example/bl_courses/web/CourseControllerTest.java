package com.example.bl_courses.web;

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

}