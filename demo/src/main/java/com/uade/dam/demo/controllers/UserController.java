package com.uade.dam.demo.controllers;

import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.service.UserService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    public UserController(UserService service) 
        { this.service = service; }
    @GetMapping public List<User> getAll() { 
        return service.findAll(); 
    }
    
    @GetMapping("/{id}") public User getOne(@PathVariable Long id) { 
        return service.findById(id); 
    }

    @PostMapping public User create(@RequestBody User u) { 
        return service.save(u); 
    }

    @PutMapping("/{id}") public User update(@PathVariable Long id, @RequestBody User u) { 
        u.setId(id); return service.save(u); 
    }

    @DeleteMapping("/{id}") public void delete(@PathVariable Long id) { 
        service.delete(id); 
    }
}