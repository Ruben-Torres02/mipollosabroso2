package com.mipollosabroso2.springboot_mipollosabroso2.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mipollosabroso2.springboot_mipollosabroso2.entities.User;
import com.mipollosabroso2.springboot_mipollosabroso2.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@RequestBody @Valid User user) {
        Optional<User> userExistente = userService.findByUsername(user.getUsername());
        if(userExistente.isPresent()){
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }

        User user2 = userService.save(user);
        return ResponseEntity.ok(user2);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login (@RequestBody User user) {
        Optional<User> usuario = userService.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }

        return ResponseEntity.status(401).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> optionalUser = userService.update(user, id);
            if(optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) {
        Optional<User> optionalUser = userService.delete(id);
        if(optionalUser.isPresent()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }




}
