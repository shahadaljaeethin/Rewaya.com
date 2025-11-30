package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Tip;
import com.example.Rewaya.Service.TipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rewaya/tip")
public class TipController {

    private final TipService tipService;

    @PostMapping("/post")
    public ResponseEntity<?> postTip(@RequestBody @Valid Tip tip, Errors errors) {
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        String result = tipService.postTip(tip);
        if (result.equals("Posted :) thank you for helping novelist community!"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @GetMapping("/get")
    public List<Tip> getAll() {return tipService.getAll();}

    @PutMapping("/update tip/{id}")
    public ResponseEntity<?> updateTip(@PathVariable Integer id,@RequestBody @Valid Tip upd,Errors errors) {
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        String result = tipService.updateTip(id, upd);
        if (result.equals("updated"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @DeleteMapping("/delete tip/{id}")
    public ResponseEntity<?> deleteTip(@PathVariable Integer id) {

        if (tipService.deleteTip(id))
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("Tip not found"));
    }

    @PutMapping("/like/{userId}/{tipId}")
    public ResponseEntity<?> sendLike(@PathVariable Integer userId,@PathVariable Integer tipId) {
        String result = tipService.sendLike(userId, tipId);
        if (result.equals("Liked :)"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @PutMapping("/dislike/{userId}/{tipId}")
    public ResponseEntity<?> disLike(@PathVariable Integer userId,@PathVariable Integer tipId) {
        String result = tipService.removeLike(userId, tipId);
        if (result.equals("Like removed"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @GetMapping("/my favorite tips/{userId}")
    public List<Tip> getMyFavTips(@PathVariable Integer userId) {
        return tipService.getMyFavTips(userId);
    }


}
