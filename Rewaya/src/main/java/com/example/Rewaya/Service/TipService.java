package com.example.Rewaya.Service;

import com.example.Rewaya.Model.*;
import com.example.Rewaya.Repository.AuthorRepository;
import com.example.Rewaya.Repository.TipRepository;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipService {
    private final TipRepository tipRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
//==============================================================


    public String postTip(Tip tip){

        Author author = authorRepository.findAuthorById(tip.getAuthorId());
        if(author==null) return "author not found";
        if(!author.getActive()) return "your account is unActive currently :(";

        tip.setLikes(new ArrayList<>());
        tip.setPublishDate(LocalDate.now());
        tipRepository.save(tip);
        return "Posted :) thank you for helping novelist community!";

    }

    public List<Tip> getAll(){return tipRepository.findAll();}


    public String updateTip(Integer id,Tip upd){

        Tip tip = tipRepository.findTipById(id);
        if(tip==null) return "tip not found";


       tip.setContent(upd.getContent());
        return "updated";

    }

    public boolean deleteTip(Integer id){
        Tip tip = tipRepository.findTipById(id);
        if(tip==null) return false;
        tipRepository.delete(tip);
        return true;
    }

/*=========================================================================================================

    Extra end points:

===========================================================================================================
 */


    public String sendLike(Integer userId, Integer tipId){

        Tip tip = tipRepository.findTipById(tipId);
        if(tip==null) return "tip not found";
        User user = userRepository.findUserById(userId);
        if(user==null) return "user not found";
//=====================

         ArrayList<Integer> likes = tip.getLikes();
         if(likes.contains(userId)) return "you already send like on this tip before";

        likes.add(userId);
        tip.setLikes(likes);
        tipRepository.save(tip);
        return "Liked :)";

    }


    public String removeLike(Integer userId, Integer tipId){
        Tip tip = tipRepository.findTipById(tipId);
        if(tip==null) return "tip not found";
        User user = userRepository.findUserById(userId);
        if(user==null) return "user not found";
        //====================================================
//         -------------------------------------        \\
        ArrayList<Integer> likes = tip.getLikes();
        if(!likes.contains(userId)) return "you are not having a like on this tip already";

        likes.remove(userId);
        tip.setLikes(likes);
        tipRepository.save(tip);
        return "Like removed";

    }



    public List<Tip> getMyFavTips(Integer userId){return tipRepository.findAll().stream().filter(tip -> tip.getLikes().contains(userId)).toList();}



}
