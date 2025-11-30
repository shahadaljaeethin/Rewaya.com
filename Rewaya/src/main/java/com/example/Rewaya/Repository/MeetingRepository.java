package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Integer> {

    Meeting findMeetingById(Integer id);
    List<Meeting> findMeetingByAuthorId(Integer ID);
}
