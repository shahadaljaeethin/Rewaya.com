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
    public ResponseEntity<?> publishChapter(@RequestBody @Valid Chapter chapter, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        String result = chapterService.publishChapter(chapter);
        if(result.equals("Chapter published! :)")) return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));

    }

    @GetMapping("/get")
    public ResponseEntity<?> getAll(){return ResponseEntity.status(200).body(chapterService.getAll());}

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateChapter(@PathVariable Integer id,@RequestBody @Valid Chapter chapter,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        //-
        String result = chapterService.updateChapter(id, chapter);
        if(result.equals("updated")) return ResponseEntity.status(200).body(new ApiResponse(result));
        return ResponseEntity.status(400).body(new ApiResponse(result));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteChapter(@PathVariable Integer id){

        if(chapterService.deleteChapter(id)) return ResponseEntity.status(200).body(new ApiResponse("deleted"));
        return ResponseEntity.status(400).body(new ApiResponse("chapter not found"));
    }

    //EEP =================================================

    @PutMapping("/read/{userId}/{id}")
    public ResponseEntity<?> readChapter(@PathVariable Integer id,@PathVariable Integer userId){
        Object ch = chapterService.readChapter(id,userId);
        if(ch instanceof Chapter ) return ResponseEntity.status(200).body(ch);
        return ResponseEntity.status(400).body(ch);
    }

    //EEP
    @GetMapping("/novel chapters/{novelId}")
    public ResponseEntity<?> getAlLChapOfNovel(@PathVariable Integer novelId){
        List<Chapter> chapters = chapterService.getAlLChapOfNovel(novelId);
        if(chapters!=null) return ResponseEntity.status(200).body(chapters);
        return ResponseEntity.status(400).body(new ApiResponse("Novel not found"));
    }

    @PostMapping("/improve")
    public ResponseEntity<?> improveChapter(@RequestBody @Valid Chapter chapter,Errors errors){
if (errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

return ResponseEntity.status(200).body(chapterService.improveChapter(chapter));

    }


}
