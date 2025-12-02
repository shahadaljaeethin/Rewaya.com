package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Chapter;
import com.example.Rewaya.Service.ChapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/v1/rewaya/chapter")
@RequiredArgsConstructor
public class ChapterController {
    private final ChapterService chapterService;
//-

    @PostMapping("/publish")
    public ResponseEntity<?> publishChapter(@RequestBody @Valid Chapter chapter){
         chapterService.publishChapter(chapter);
         return ResponseEntity.status(200).body(new ApiResponse("Chapter published! :)"));

    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){return ResponseEntity.status(200).body(chapterService.getAll());}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable Integer id,@RequestBody @Valid Chapter chapter){
        //-
        chapterService.updateChapter(id, chapter);
       return ResponseEntity.status(200).body(new ApiResponse("updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Integer id){

        chapterService.deleteChapter(id);
    return ResponseEntity.status(200).body(new ApiResponse("deleted"));
    }

    //EEP =================================================

    @PutMapping("/read/{userId}/{id}")
    public ResponseEntity<?> readChapter(@PathVariable Integer id,@PathVariable Integer userId){
     return ResponseEntity.status(200).body(chapterService.readChapter(id,userId));
    }

    //EEP
    @GetMapping("/novel chapters/{novelId}")
    public ResponseEntity<?> getAlLChapOfNovel(@PathVariable Integer novelId){
        List<Chapter> chapters = chapterService.getAlLChapOfNovel(novelId);
         return ResponseEntity.status(200).body(chapters);
    }

    @PostMapping("/improve")
    public ResponseEntity<?> improveChapter(@RequestBody @Valid Chapter chapter){
    return ResponseEntity.status(200).body(chapterService.improveChapter(chapter));

    }


}
