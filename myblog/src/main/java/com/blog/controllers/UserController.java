package com.blog.controllers;

import com.blog.payloads.UserDto;
import com.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Post- create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userDto){
        UserDto createUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    // Post- update user
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
        UserDto updatedUser=this.userService.updateUser(userDto, uid);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    // Delete- delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
        this.userService.deleteUser(uid);
        return new ResponseEntity<>(Map.of("message","user deleted successfully"),HttpStatus.OK);
    }

    // Get- user get
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getUsers(){
        return new ResponseEntity<>(this.userService.getAllusers(), HttpStatus.OK);
    }

    // Get - user get
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
        return new ResponseEntity<>(this.userService.getUserById(userId), HttpStatus.OK);
    }
}
