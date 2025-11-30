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
    public ResponseEntity<?> createNovel(@RequestBody @Valid Novel novel, Errors errors) {
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        String result = novelService.createNovel(novel);
        if (result.equals("Published! :)"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @GetMapping("/get")
    public List<Novel> getAll() { return novelService.getAll(); }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateNovel(@PathVariable Integer id,@RequestBody @Valid Novel upd, Errors errors) {
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        String result = novelService.updateNovel(id, upd);
        if (result.equals("updated"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNovel(@PathVariable Integer id) {
           if (novelService.deleteNovel(id))
            return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("Novel not found"));
    }

//====================================
//=====================================

    @PutMapping("/like/{userId}/{novelId}")
    public ResponseEntity<?> sendLike(@PathVariable Integer userId, @PathVariable Integer novelId) {

        String result = novelService.sendLike(userId, novelId);
        if (result.equals("Liked :)"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }


    @PutMapping("/dislike/{userId}/{novelId}")
    public ResponseEntity<?> disLike(@PathVariable Integer userId, @PathVariable Integer novelId) {

        String result = novelService.removeLike(userId, novelId);
        if (result.equals("Like removed"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
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
    String out = novelService.setComplete(novelId);
        if(out.equals("novel set to completed successfully"))
            return ResponseEntity.status(200).body(out);
        return ResponseEntity.status(400).body(out);
    }

    @PutMapping("/undo complete/{novelId}")
    public ResponseEntity<?> undoComplete(@PathVariable Integer novelId){
        String out = novelService.undoComplete(novelId);
        if(out.equals("novel set to not completed successfully"))
            return ResponseEntity.status(200).body(out);
        return ResponseEntity.status(400).body(out);
    }

    @GetMapping("/smart search/{query}")
    public ResponseEntity<?> smartSearch(@PathVariable String query){
        List<Novel> res = novelService.smartSearch(query);
        if(res==null) return ResponseEntity.status(400).body(new ApiResponse("no novel found"));

        return ResponseEntity.status(200).body(res);

    }
}

