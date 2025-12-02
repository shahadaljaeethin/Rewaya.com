package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Novel;
import com.example.Rewaya.Service.NovelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/rewaya/novel")
public class NovelController {

    private final NovelService novelService;

    @PostMapping("/publish")
    public ResponseEntity<?> createNovel(@RequestBody @Valid Novel novel) {
     novelService.createNovel(novel);
     return ResponseEntity.status(200).body(new ApiResponse("Published! :)"));
    }

    @GetMapping("/get")
    public List<Novel> getAll() { return novelService.getAll(); }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNovel(@PathVariable Integer id,@RequestBody @Valid Novel upd) {
        novelService.updateNovel(id, upd);
       return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNovel(@PathVariable Integer id) {
           novelService.deleteNovel(id);
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }

//====================================
//=====================================

    @PutMapping("/like/{userId}/{novelId}")
    public ResponseEntity<?> sendLike(@PathVariable Integer userId, @PathVariable Integer novelId) {

        novelService.sendLike(userId, novelId);
        return ResponseEntity.status(200).body(new ApiResponse("Liked :)"));
    }


    @PutMapping("/dislike/{userId}/{novelId}")
    public ResponseEntity<?> disLike(@PathVariable Integer userId, @PathVariable Integer novelId) {
       novelService.removeLike(userId, novelId);
       return ResponseEntity.status(200).body(new ApiResponse("Like removed"));
    }


    @GetMapping("/my favorite Novels/{userId}")
    public List<Novel> getMyFavNovels(@PathVariable Integer userId) {
        return novelService.getMyFavNovels(userId);
    }

    @GetMapping("/completed")
    public List<Novel> getCompletedNov() {
        return novelService.getCompletedNov();
    }

    @GetMapping("/non completed")
    public List<Novel> getNonCompletedNov() {
        return novelService.getNonCompletedNov();
    }

    @GetMapping("/categories filter/{cate}")
    public List<Novel> filterCategories(@PathVariable String cate) {
        return novelService.filterCategories(cate);
    }

    @GetMapping("/top 10")
    public List<Novel> getTop10Novels() {
        return novelService.getTop10Novels();
    }

//    GetMapping("/feed")
//    public ResponseEntity<?> algorithmFeed(){
//        return ResponseEntity.status(200).body(novelService.feedAlgorithm());
//    }
    @PutMapping("/set complete/{novelId}")
    public ResponseEntity<?> setComplete(@PathVariable Integer novelId){
   novelService.setComplete(novelId);
            return ResponseEntity.status(200).body(new ApiResponse("novel set to completed successfully"));
    }

    @PutMapping("/undo complete/{novelId}")
    public ResponseEntity<?> undoComplete(@PathVariable Integer novelId){
        novelService.undoComplete(novelId);
            return ResponseEntity.status(200).body(new ApiResponse("novel set to not completed successfully"));
    }

    @GetMapping("/smart search/{query}")
    public ResponseEntity<?> smartSearch(@PathVariable String query){
        List<Novel> res = novelService.smartSearch(query);
        return ResponseEntity.status(200).body(res);
    }
}

