package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Author;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rewaya/author")
public class AuthorController {
    private final AuthorService authorService;
//------


    @PostMapping("/register")
    public ResponseEntity<?> registerAuthor(@RequestBody @Valid Author author){
        authorService.registerAuthor(author);
        return ResponseEntity.status(200).body(new ApiResponse("registered successfully, we will contact you soon to activate your account"));

    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){return ResponseEntity.status(200).body(authorService.getAll());}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Integer id,@RequestBody @Valid Author upd){
        authorService.updateAuthor(id, upd);
      return ResponseEntity.status(200).body(new ApiResponse("author updated"));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Integer id){

        authorService.deleteAuthor(id);
         return ResponseEntity.status(200).body(new ApiResponse("Author deleted"));
    }
    //==============================

    @PutMapping("/activate/{admin}/{authId}")
    public ResponseEntity<?> activateAuthor(@PathVariable Integer admin,@PathVariable Integer authId){
        authorService.activateAuth(admin,authId);
        return ResponseEntity.status(200).body(new ApiResponse("Account Activated! :)"));
    }
    @PutMapping("/freeze/{admin}/{authId}")
    public ResponseEntity<?> freezeAuthor(@PathVariable Integer admin,@PathVariable Integer authId){
         authorService.freezeAuth(admin,authId);
         return ResponseEntity.status(200).body(new ApiResponse("Account froze successfully"));
    }





    @GetMapping("/log in")
    public ResponseEntity<?> logIn(@RequestBody String[] info){
       authorService.logIn(info);
            return ResponseEntity.status(200).body(new ApiResponse("logged in successfully"));
    }

}
