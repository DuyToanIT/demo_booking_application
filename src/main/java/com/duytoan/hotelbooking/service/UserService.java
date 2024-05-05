package com.duytoan.hotelbooking.service;

import com.duytoan.hotelbooking.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
}
