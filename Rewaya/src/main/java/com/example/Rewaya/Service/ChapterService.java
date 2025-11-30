package com.example.Rewaya.Service;


import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Author;
import com.example.Rewaya.Model.Chapter;
import com.example.Rewaya.Model.Novel;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Repository.AuthorRepository;
import com.example.Rewaya.Repository.ChapterRepository;
import com.example.Rewaya.Repository.NovelRepository;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChapterService {
    private final ChapterRepository chapterRepository;
    private final NovelRepository novelRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private  final  AiService aiService;
//====================================================

    public String publishChapter(Chapter ch){

        Novel nvl = novelRepository.findNovelById(ch.getNovelId());
        if(nvl==null) return "Novel not found";
        if(!authorRepository.findAuthorById(nvl.getAuthorId()).getActive()) return "Author is unActive currently";
        if(nvl.getIsCompleted()) return "this novel is completed, edit novel status first to add new chapters";
//========================================

        ch.setPublishDate(LocalDate.now());
        Integer lastChap = chapterRepository.getLastChapterNumber(ch.getNovelId());
        if(lastChap == null) lastChap = 0;

       ch.setChapterNumber(lastChap+1);
       ch.setViews(0); //it has no views yet
       chapterRepository.save(ch);
       return "Chapter published! :)";

    }

    public List<Chapter> getAll(){return chapterRepository.findAll();}


    public String updateChapter(Integer id,Chapter upd){

        Chapter chapter = chapterRepository.findChapterById(id);
        if(chapter==null) return "Chapter not found";

        //end of check
        chapter.setTitle(upd.getTitle());
        chapter.setContent(upd.getContent());
        chapterRepository.save(chapter);
        return "updated";

    }

    public boolean deleteChapter(Integer id){
        Chapter chapter = chapterRepository.findChapterById(id);
        if(chapter==null) return false;

        chapterRepository.delete(chapter);
        return true;
    }
//============================================================================================EEP

    public Object readChapter(Integer id, Integer userId){

        Chapter ch = chapterRepository.findChapterById(id);
        if(ch==null) return new   ApiResponse("novel not found");;

        User user = userRepository.findUserById(userId);
        if(user==null) return new ApiResponse("user not found");

        Novel novel = novelRepository.findNovelById(ch.getNovelId());
        if(user.getAge() < novel.getAgeCategory()) return new ApiResponse("your age is not suitable with this Novel");

        ch.setViews(ch.getViews()+1);
        chapterRepository.save(ch);
        return ch;

    }

    public List<Chapter> getAlLChapOfNovel(Integer novelId){
        //needs fix
        Novel novel = novelRepository.findNovelById(novelId);
        if(novel==null) return null; //novel not found

        return chapterRepository.findChapterByNovelId(novelId);

    }

    public String improveChapter(Chapter chapter){

        String  prompt = "title: ["+chapter.getTitle()+"]  content:["+chapter.getContent()+"]";

        prompt =  "I want you to improve this written chapter with these rules " +
                ": 1) keep the style, don't make it looks odd to me" +
                " 2) fix my typo " +
                " 3) I want you to add lines that improve my story chapter, try to avoid deleting scenes " +
                "4) give me the result directly, don't answer me anything but the final result" +
                ", this is the chapter: "
                +prompt;

        return aiService.askAI(prompt);
    }


}
