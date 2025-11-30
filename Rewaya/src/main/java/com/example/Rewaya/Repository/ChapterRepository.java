package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Integer> {
    Chapter findChapterById(Integer ID);

    @Query("select max(c.chapterNumber) from Chapter c where c.novelId = ?1")
    Integer getLastChapterNumber(Integer novelId);

    List<Chapter> findChapterByNovelId(Integer id);

    }
