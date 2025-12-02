package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewaya/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> registerUser(@RequestBody @Valid User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("added"));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){return ResponseEntity.status(200).body(userService.getAll());}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,@RequestBody @Valid User upd){
        userService.updateUser(id, upd);
            return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){

        userService.deleteUser(id);
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }

    //============================================

    @GetMapping("/log in")
    public ResponseEntity<?> logIn(@RequestBody String[] info){
            return ResponseEntity.status(200).body(userService.logIn(info));
    }

    @PutMapping("/add favorite category/{userId}/{category}")
    public ResponseEntity<?> addFavCate(@PathVariable Integer userId,@PathVariable String category){
     userService.addToFavCategory(userId,category);
     return ResponseEntity.status(200).body(new ApiResponse(category+" is added to your favorite"));
    }
    @PutMapping("/remove favorite category/{userId}/{category}")
    public ResponseEntity<?> removeFavCate(@PathVariable Integer userId,@PathVariable String category){
       userService.removeFavCate(userId,category);
        return ResponseEntity.status(200).body(new ApiResponse(category+" is removed from your favorite"));
    }
    @PutMapping("/update bio")
    public ResponseEntity<?> addBio(@PathVariable Integer userId,@RequestBody String aboutMe){
        userService.addBio(aboutMe,userId);
        return ResponseEntity.status(200).body(new ApiResponse("Bio updated"));
    }




}
