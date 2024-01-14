package com.example.blorders.web;

import com.example.blorders.domain.Status;
import com.example.blorders.dto.OrderDto;
import com.example.blorders.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService service;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
   public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/orders"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
   public void create() throws Exception {
        OrderDto orderDto = new OrderDto(UUID.randomUUID(), null, null, Status.UNLOCKED, new Date(), new Date());
        String valueAsString = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).save(orderDto);
    }

    @Test
   public void update() throws Exception {
        UUID id = UUID.randomUUID();
        OrderDto orderDto = new OrderDto(id, null, null, Status.UNLOCKED, new Date(), new Date());
        String valueAsString = objectMapper.writeValueAsString(orderDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/orders/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).update(id, orderDto);

    }

    @Test
  public void getByStatus() throws Exception {
        Status status = Status.UNLOCKED;
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/byStatus")
                        .param("status", status.name()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getByStatus(status);
    }

    @Test
   public void getById() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/{id}", id))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).getById(id);
    }

    @Test
   public void getByUser() throws Exception {
        UUID userId = UUID.randomUUID();
        mockMvc.perform(MockMvcRequestBuilders.get("/orders/byUser")
                        .param("userId", userId.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1)).findByUserId(userId);
    }

}