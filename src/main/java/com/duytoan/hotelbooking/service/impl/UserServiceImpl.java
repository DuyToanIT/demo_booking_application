package com.duytoan.hotelbooking.service.impl;

import com.duytoan.hotelbooking.model.entity.User;
import com.duytoan.hotelbooking.repository.UserRepository;
import com.duytoan.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Get user by id
     *
     * @param id
     * @return
     */
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
