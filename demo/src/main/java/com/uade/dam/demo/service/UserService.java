package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;
    public UserService(UserRepository repo) { this.repo = repo; }
    public List<User> findAll() { return repo.findAll(); }
    public User findById(Long id) { return repo.findById(id).orElse(null); }
    public User save(User u) { return repo.save(u); }
    public void delete(Long id) { repo.deleteById(id); }
}