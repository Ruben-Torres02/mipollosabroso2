package com.mipollosabroso2.springboot_mipollosabroso2.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String Username);
}
