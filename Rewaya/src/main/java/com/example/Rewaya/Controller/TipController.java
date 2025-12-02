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
    public ResponseEntity<?> postTip(@RequestBody @Valid Tip tip) {
         tipService.postTip(tip);
         return ResponseEntity.status(200).body(new ApiResponse("Posted :) thank you for helping novelist community!"));
    }

    @GetMapping("/get")
    public List<Tip> getAll() {return tipService.getAll();}

    @PutMapping("/update tip/{id}")
    public ResponseEntity<?> updateTip(@PathVariable Integer id,@RequestBody @Valid Tip upd) {
       tipService.updateTip(id, upd);
         return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @DeleteMapping("/delete tip/{id}")
    public ResponseEntity<?> deleteTip(@PathVariable Integer id) {
        tipService.deleteTip(id);
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }

    @PutMapping("/like/{userId}/{tipId}")
    public ResponseEntity<?> sendLike(@PathVariable Integer userId,@PathVariable Integer tipId) {
       tipService.sendLike(userId, tipId);
        return ResponseEntity.status(200).body(new ApiResponse("liked :)"));
    }

    @PutMapping("/dislike/{userId}/{tipId}")
    public ResponseEntity<?> disLike(@PathVariable Integer userId,@PathVariable Integer tipId) {
        tipService.removeLike(userId, tipId);
        return ResponseEntity.status(200).body(new ApiResponse("Like removed"));
    }

    @GetMapping("/my favorite tips/{userId}")
    public List<Tip> getMyFavTips(@PathVariable Integer userId) {
        return tipService.getMyFavTips(userId);
    }


}
