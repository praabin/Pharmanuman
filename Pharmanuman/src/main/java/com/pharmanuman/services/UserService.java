package com.pharmanuman.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharmanuman.dao.UserRepository;
import com.pharmanuman.entities.User;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
