package com.example.blreviews.web;

import com.example.blreviews.dto.FeedbackDto;
import com.example.blreviews.service.FeedbackService;
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
@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private FeedbackService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void save() throws Exception {
        FeedbackDto feedbackDto = new FeedbackDto(null, null, null, 5, "text");
        String valueAsString = objectMapper.writeValueAsString(feedbackDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/feedbacks")
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/feedbacks/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getById(id);

    }

    @Test
    public void getAllByCourseId() throws Exception {
        UUID courseId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/feedbacks/course/{courseId}", courseId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getByCourseId(courseId);

    }

    @Test
    public void getAllByUserId() throws Exception {
        UUID userId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/feedbacks")
                        .param("userId", userId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getByUserId(userId);

    }

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/feedbacks/all"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getAll();
    }

    @Test
    public void update() throws Exception {
        UUID id = UUID.randomUUID();
        FeedbackDto feedbackDto = new FeedbackDto(null, null, null, 5, "text");
        String valueAsString = objectMapper.writeValueAsString(feedbackDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/feedbacks/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void countRatingByCourse() throws Exception {
        UUID courseId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/feedbacks/rating")
                        .param("courseId", courseId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).countRatingByCourse(courseId);
    }
}