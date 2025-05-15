package com.mipollosabroso2.springboot_mipollosabroso2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.User;
import com.mipollosabroso2.springboot_mipollosabroso2.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
       return (List<User>) userRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
       return userRepository.findById(id);
    }
    @Transactional
    @Override
    public User save(User user) {
       return userRepository.save(user);
    }
    @Transactional
    @Override
    public Optional<User> delete(Long id) {
       Optional<User> optionalUser = userRepository.findById(id);
       if(optionalUser.isPresent()) {
        userRepository.deleteById(id);
       }
       return optionalUser;
    }
    @Transactional
    @Override
    public Optional<User> update(User user, Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user2 = optionalUser.get();

            user2.setUsername(user.getUsername());
            user2.setPassword(user.getPassword());

            userRepository.save(user2);

            return Optional.of(user2);
        }

        return Optional.empty();
    }
    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
       return userRepository.findByUsernameAndPassword(username, password);
    }

    public Optional<User> findByUsername(String username) {
      return userRepository.findByUsername(username);
    }

}
