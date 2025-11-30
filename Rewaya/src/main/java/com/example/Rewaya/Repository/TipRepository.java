package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.Novel;
import com.example.Rewaya.Model.Tip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipRepository extends JpaRepository<Tip,Integer> {
    Tip findTipById(Integer id);
//    Query("select n from Tip n where :userId member of n.likes")
//    List<Novel> getLikedTipsByMe(Integer userId);
}
