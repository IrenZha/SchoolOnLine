package com.example.bllessons.web;

import com.example.bllessons.dto.LessonDto;
import com.example.bllessons.service.LessonService;
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

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(LessonController.class)
class LessonControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private LessonService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
   public void create() throws Exception {
        LessonDto lessonDto = new LessonDto(null, "theme", "text",null);
        String valueAsString = objectMapper.writeValueAsString(lessonDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/lessons")
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).create(lessonDto);
    }

    @Test
   public void getById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/lessons/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getById(id);
    }

    @Test
   public void getAll() throws Exception {
        UUID courseId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/lessons")
                        .param("courseId", courseId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getAll(courseId);
    }

    @Test
   public void update() throws Exception {
        UUID id = UUID.randomUUID();
        LessonDto lessonDto = new LessonDto(null, "theme", "text",null);
        String valueAsString = objectMapper.writeValueAsString(lessonDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/lessons/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).update(id, lessonDto);

    }
}