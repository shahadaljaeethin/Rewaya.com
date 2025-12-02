package com.example.Rewaya.Service;

import com.example.Rewaya.Api.ApiException;
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


    public void postTip(Tip tip){

        Author author = authorRepository.findAuthorById(tip.getAuthorId());
        if(author==null) throw new ApiException("author not found");
        if(!author.getActive()) throw new ApiException("your account is unActive currently :(");

        tip.setLikes(new ArrayList<>());
        tip.setPublishDate(LocalDate.now());
        tipRepository.save(tip);

    }

    public List<Tip> getAll(){return tipRepository.findAll();}


    public void updateTip(Integer id,Tip upd){

        Tip tip = tipRepository.findTipById(id);
        if(tip==null) throw new ApiException("tip not found");


       tip.setContent(upd.getContent());

    }

    public void deleteTip(Integer id){
        Tip tip = tipRepository.findTipById(id);
        if(tip==null) throw new ApiException("tip not found");
        tipRepository.delete(tip);
    }

/*=========================================================================================================

    Extra end points:

===========================================================================================================
 */


    public void sendLike(Integer userId, Integer tipId){

        Tip tip = tipRepository.findTipById(tipId);
        if(tip==null) throw new ApiException("tip not found");
        User user = userRepository.findUserById(userId);
        if(user==null) throw new ApiException("user not found");
//=====================

         ArrayList<Integer> likes = tip.getLikes();
         if(likes.contains(userId)) throw new ApiException( "you already send like on this tip before");

        likes.add(userId);
        tip.setLikes(likes);
        tipRepository.save(tip);
    }


    public void removeLike(Integer userId, Integer tipId){
        Tip tip = tipRepository.findTipById(tipId);
        if(tip==null) throw new ApiException("tip not found");
        User user = userRepository.findUserById(userId);
        if(user==null) throw new ApiException("user not found");
        //====================================================
//         -------------------------------------        \\
        ArrayList<Integer> likes = tip.getLikes();
        if(!likes.contains(userId)) throw new ApiException("you are not having a like on this tip already");

        likes.remove(userId);
        tip.setLikes(likes);
        tipRepository.save(tip);
    }



    public List<Tip> getMyFavTips(Integer userId){return tipRepository.findAll().stream().filter(tip -> tip.getLikes().contains(userId)).toList();}



}
