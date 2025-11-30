package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NovelRepository extends JpaRepository<Novel,Integer> {

    Novel findNovelById(Integer id);

    @Query("select n from Novel n join n.categories c where lower(c) = lower(?1)")
    List<Novel> filterCategory(String category);

    List<Novel> findAllByIsCompleted(Boolean completed);


}
