package com.mipollosabroso2.springboot_mipollosabroso2.services;

import java.util.List;
import java.util.Optional;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.User;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> delete (Long id);

    Optional<User> update(User user, Long id);

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);
}
