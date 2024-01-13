package com.example.blusers.web;

import com.example.blusers.dto.RoleDto;
import com.example.blusers.dto.RoleEnum;
import com.example.blusers.dto.UserDto;
import com.example.blusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    @GetMapping("/getAll")
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @PostMapping
    public UserDto save(@RequestBody UserDto user) {
        return service.save(user);
    }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable(name = "id") UUID id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") UUID id) {
        service.delete(id);
    }

    @PutMapping("/update/{id}")
    public UserDto update(@PathVariable(name = "id") UUID id, @RequestBody UserDto user) {
        return service.update(id, user);
    }

    @GetMapping("/findByUsername/{username}")
    public UserDto findByUsername(@PathVariable(name = "username") String username) {
        return service.findByUsername(username);
    }

    @PutMapping("/{id}")
    public UserDto addRole(@PathVariable(name = "id") UUID id, @RequestParam String roleName) {
        return service.addRole(id, roleName);
    }

    @PutMapping
    public boolean pay(@RequestParam(name = "studentId") UUID studentId,
                       @RequestParam(name = "teacherId") UUID teacherId,
                       @RequestParam(name = "price") Double price) {
        return service.pay(studentId, teacherId, price);
    }

    @GetMapping("/allByRole")
    public List<UserDto> getAllByRole(@RequestParam String role){
       return service.findAllByRolesContains(role);
    }
}
