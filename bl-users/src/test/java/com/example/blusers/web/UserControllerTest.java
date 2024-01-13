package com.example.blusers.web;

import com.example.blusers.dto.UserDto;
import com.example.blusers.service.UserService;
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

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/getAll"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void save() throws Exception {
        UserDto user = new UserDto(UUID.randomUUID(), "firstName", "lastName", null, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void getById() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto(id, "firstName", "lastName", null, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).getById(id);
    }


    @Test
    public void delete() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto(id, "firstName", "lastName", null, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).delete(id);

    }

    @Test
    public void findByUsername() throws Exception {
        UUID id = UUID.randomUUID();
        String username = "username";
        UserDto user = new UserDto(id, "firstName", "lastName", username, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/findByUsername/{username}", username)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).findByUsername(username);
    }

    @Test
    public void getAllByRole() throws Exception {
        String role = "role";

        mockMvc.perform(MockMvcRequestBuilders.get("/users/allByRole")
                        .param("role", role))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).findAllByRolesContains(role);
    }

    @Test
    public void update() throws Exception {
        UUID id = UUID.randomUUID();
        UserDto user = new UserDto(id, "firstName", "lastName", null, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/update/{id}", id)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void addRole() throws Exception {
        UUID id = UUID.randomUUID();
        String roleName = "roleName";
        UserDto user = new UserDto(id, "firstName", "lastName", null, null, null, null, null, 20.0, null);
        String valueAsString = objectMapper.writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.put("/users/{id}", id)
                        .param("roleName", roleName)
                        .content(valueAsString)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).addRole(id,roleName);
    }

    @Test
    public void pay() throws Exception {
        UUID studentId = UUID.randomUUID();
        UUID teacherId = UUID.randomUUID();
       Double price = 100.0;

        mockMvc.perform(MockMvcRequestBuilders.put("/users")
                        .param("studentId", studentId.toString())
                        .param("teacherId", teacherId.toString())
                        .param("price", "100.0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userService, Mockito.times(1)).pay(studentId, teacherId, price);
    }
}