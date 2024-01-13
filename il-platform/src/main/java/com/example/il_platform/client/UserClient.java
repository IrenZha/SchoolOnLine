package com.example.il_platform.client;

import com.example.il_platform.dto.users.RoleDto;
import com.example.il_platform.dto.users.RoleEnum;
import com.example.il_platform.dto.users.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-client", url = "${client.users}")
public interface UserClient {
    @GetMapping("/getAll")
    List<UserDto> getAll();

    @GetMapping("/{id}")
    UserDto getById(@PathVariable(name = "id") UUID id);

    @PostMapping
    UserDto save(@RequestBody UserDto user);

    @PutMapping("/update/{id}")
    UserDto update(@PathVariable(name = "id") UUID id, @RequestBody UserDto user);

    @GetMapping("/findByUsername/{username}")
    UserDto findByUsername(@PathVariable(name = "username") String username);

    @PutMapping("/{id}")
    UserDto addRole(@PathVariable(name = "id") UUID id, @RequestParam String roleName);

    @PutMapping
    boolean pay(@RequestParam(name = "studentId") UUID studentId,
                       @RequestParam(name = "teacherId") UUID teacherId,
                       @RequestParam(name = "price") Double price);

    @GetMapping("/allByRole")
   List<UserDto> getAllByRole(@RequestParam String role);
}
