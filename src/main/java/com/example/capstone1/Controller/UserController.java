package com.example.capstone1.Controller;

import com.example.capstone1.API.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        ArrayList<User> users = userService.getUsers();
        return ResponseEntity.status(200).body(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isAdded = userService.addUser(user);
        if(isAdded){
            return ResponseEntity.status(200).body(new ApiResponse("User added successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User couldn't be added"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if(errors.hasErrors()){
            String message = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        boolean isUpdated = userService.updateUser(id,user);
        if(isUpdated){
            return ResponseEntity.status(200).body(new ApiResponse("User updated successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User couldn't be updated"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        boolean isDeleted = userService.deleteUser(id);
        if(isDeleted){
            return ResponseEntity.status(200).body(new ApiResponse("User deleted successfully"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("User couldn't be deleted"));

    }
}
