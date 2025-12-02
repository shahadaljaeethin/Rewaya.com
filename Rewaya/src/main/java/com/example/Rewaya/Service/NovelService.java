package com.example.Rewaya.Service;


import com.example.Rewaya.Api.ApiException;
import com.example.Rewaya.Model.*;
import com.example.Rewaya.Repository.*;
import lombok.RequiredArgsConstructor;
//import org.checkerframework.checker.units.qual.N;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelService {
private final NovelRepository novelRepository;
private final AuthorRepository authorRepository;
private final ChapterRepository chapterRepository;
private final UserRepository userRepository;
    private  final  AiService aiService;



    public void createNovel(Novel novel){

        Author auth = authorRepository.findAuthorById(novel.getAuthorId());
        if(auth==null) throw new ApiException("Author not found");
        if(!auth.getActive()) throw new ApiException("your account is not approved yet :(");

        //the website has 3 category age +13 +16 +18
        if(novel.getAgeCategory()<16) novel.setAgeCategory(13);
        else if(novel.getAgeCategory()<18) novel.setAgeCategory(16);
        else novel.setAgeCategory(18);


        novel.setLikes(new ArrayList<>()); //no user made like yet
        novel.setIsCompleted(false);
        novel.setPublishDate(LocalDate.now());
        novelRepository.save(novel);

    }

    public List<Novel> getAll(){return novelRepository.findAll();}


    public void updateNovel(Integer id,Novel upd){

        Author author = authorRepository.findAuthorById(upd.getAuthorId());
        if(author==null) throw new ApiException("Author not found");

        if(!author.getActive()) throw new ApiException("Author is unActive");

        Novel novel = novelRepository.findNovelById(id);
        if(novel==null) throw new ApiException("Novel not found");
        //end of check


       novel.setTitle(upd.getTitle());
       novel.setOverview(upd.getOverview());
       novel.setCategories(upd.getCategories());
       novel.setIsCompleted(upd.getIsCompleted());
       novelRepository.save(novel);
    }

    public void deleteNovel(Integer id){
        Novel novel = novelRepository.findNovelById(id);
        if(novel==null) throw new ApiException("novel not found");

        //delete all chapters of this novel if any
        List<Chapter> chapters = chapterRepository.findChapterByNovelId(id);
        if(!chapters.isEmpty()){
            for(Chapter ch: chapters) chapterRepository.delete(ch);
        }

        //now delete the novel
        novelRepository.delete(novel);
    }

//-----------------------E N D   OF  C R U Ds------------------------------------



    public void sendLike(Integer userId, Integer novelId){

        Novel novel = novelRepository.findNovelById(novelId);
        if(novel==null) throw new ApiException("novel not found");
        User user = userRepository.findUserById(userId);
        if(user==null) throw new ApiException("user not found");
//         -------------------------------------        \\
        ArrayList<Integer> likes = novel.getLikes();
        if(likes.contains(userId)) throw new ApiException("you already have like on this Novel");

        likes.add(userId);
        novel.setLikes(likes);
        novelRepository.save(novel);
    }


    public void removeLike(Integer userId, Integer novelId){

        Novel novel = novelRepository.findNovelById(novelId);
        if(novel==null) throw new ApiException("novel not found");
        User user = userRepository.findUserById(userId);
        if(user==null) throw new ApiException("user not found");
//         -------------------------------------        \\
        ArrayList<Integer> likes = novel.getLikes();
        if(!likes.contains(userId)) throw new ApiException("you are not having a like on this Novel already");

        likes.remove(userId);
        novel.setLikes(likes);
        novelRepository.save(novel);

    }



    public List<Novel> getMyFavNovels(Integer userId){return novelRepository.findAll().stream().filter(novel -> novel.getLikes().contains(userId)).toList();}
    public List<Novel> getCompletedNov(){return novelRepository.findAllByIsCompleted(true);}
    public List<Novel> getNonCompletedNov(){return novelRepository.findAllByIsCompleted(false);}
    public List<Novel> filterCategories(String category){return novelRepository.filterCategory(category);}
    public List<Novel> getTop10Novels() {
        return novelRepository.findAll().stream().sorted((a, b) -> b.getLikes().size() - a.getLikes().size()).limit(10).toList();
                                         }



         public void setComplete(Integer novelId){

            Novel novel = novelRepository.findNovelById(novelId);
            if(novel==null) throw new ApiException("novel not found");

            if(novel.getIsCompleted()) throw new ApiException("this novel is already completed");


            if(chapterRepository.findChapterByNovelId(novelId).isEmpty()) throw new ApiException("add at least one chapter to complete the novel");

            novel.setIsCompleted(true);
            novelRepository.save(novel);


         }
    public void undoComplete(Integer novelId){

        Novel novel = novelRepository.findNovelById(novelId);
        if(novel==null) throw new ApiException("novel not found");

        if(!novel.getIsCompleted()) throw new ApiException("this novel is already NOT completed");


        novel.setIsCompleted(false);
        novelRepository.save(novel);

    }
    public ArrayList<Novel> smartSearch(String query){

        if(novelRepository.findAll().isEmpty()) return  null;

        // 1> gather all Novel info, make it readable to AI
        String save;
        ArrayList<String> novels = new ArrayList<>();
        for(Novel novel:novelRepository.findAll())
        {
        save="[id:( "+novel.getId()+" ) title:( "+novel.getTitle()+" ) Novel overview:( "+novel.getOverview()+" )],";
        novels.add(save);
        }
        String prompt = "Help me to find the novel based on title&overview, I want you to select the potential novels, so it could be one or more of potential novel that I am looking for" +
                "" +
                "please answer me with novel Ids only separated by - , for example : 2-4-7-15" +
                "" +
                "if there is no novel that are potential i am looking for from given list, I want you to reply me with -1" +
                "be direct and don't answer me anything but the final result for example 2-4-7-15 or -1 ,,, here is the list:"+novels+", and here is the description of a novel that i am looking for : "+query;

        //get the answer and turn it into novel list (search one by one)
         String result =  aiService.askAI(prompt);
         if(result.equals("-1")) return new ArrayList<>(); //no novel seems like it
        ArrayList<Novel> search = new ArrayList<>();

        String trim;
        int id;
        Novel novel;

            for(String res : result.split("-")) {
                if (!res.trim().isEmpty()) {
                 id =  Integer.parseInt(res.trim()) ;
                    novel = novelRepository.findNovelById(id);
                    search.add(novel);
                }
               // id = Integer.parseInt(trim);


            }

        return search;
    }






}


//    public ApiResponseFeed feedAlgorithm(){
//
//        List<Novel> top3 ;
//        List<Novel> random3;
//
////======================TOP 3===========================================
//        if(getTop10Novels().isEmpty()) {
//            top3 = new ArrayList<>(); //empty instead of null
//            random3 = new ArrayList<>();
//        }
//        else {
//            if (getTop10Novels().size() < 4)
//                top3 = getTop10Novels();
//            else
//                top3 = getTop10Novels().stream().limit(3).toList();
////=======================================================================
//
////===================================RANDOM NOVELS=======================
//            if(novelRepository.findAll().size()<4)
//                random3 = novelRepository.findAll();
//        else {
//            List<Novel> all =novelRepository.findAll();
//                Random random = new Random( );
//                int one = random.nextInt(novelRepository.findAll().size()),two,three;
//                while (true){
//                two = random.nextInt(novelRepository.findAll().size());
//                if(two!=one) break;
//                }
//                while (true){
//                    three = random.nextInt(novelRepository.findAll().size());
//                    if(three!=one&&three!=two) break;
//                }
//                random3 = new ArrayList<>();
//                random3.add(all.get(one));
//                random3.add(all.get(two));
//                random3.add(all.get(three));
//
//            }//end of adding 3 random novels
//
//
//              } //else of  not empty
////===================================================================
//
//        List<Tip> tips3= new ArrayList<>();;
//        List<Tip> tips = tipRepository.findAll();
//        if(!tips.isEmpty()){
//         if(tips.size()<4) tips3 = tips;
//            else {
//
//        Random randomTips = new Random();
//
//             int oneTip = randomTips.nextInt(tips.size()),twoTip,threeTip;
//             while (true){
//                 twoTip = randomTips.nextInt(tips.size());
//                 if(twoTip!=oneTip) break;
//             }
//             while (true){
//                 threeTip = randomTips.nextInt(tips.size());
//                 if(threeTip!=oneTip&&threeTip!=twoTip) break;
//             }
//
//             tips3.add(tips.get(oneTip));
//             tips3.add(tips.get(twoTip));
//             tips3.add(tips.get(threeTip));
//         }}
//       return new ApiResponseFeed(top3,random3,tips3);
//    }






