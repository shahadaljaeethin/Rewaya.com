package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.JoinRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest,Integer> {
    JoinRequest findJoinRequestById(Integer id);
    @Query("select j from JoinRequest j where j.status = 'Approved' and j.userId = ?1")
    List<JoinRequest> getMyApprovedRequests(Integer userId);
    List<JoinRequest> findJoinRequestByUserId(Integer id);
}
