package com.example.Rewaya.Service;

import com.example.Rewaya.Api.ApiException;
import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Author;
import com.example.Rewaya.Model.JoinRequest;
import com.example.Rewaya.Model.Meeting;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Repository.AuthorRepository;
import com.example.Rewaya.Repository.JoinRequestRepository;
import com.example.Rewaya.Repository.MeetingRepository;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MeetingService {
    private final MeetingRepository meetingRepository;
    private final AuthorRepository authorRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final UserRepository userRepository;


    public void announceMeeting(Meeting meeting){
        Author author = authorRepository.findAuthorById(meeting.getAuthorId());
        if(author==null)  throw new ApiException("author not found");
        if(!author.getActive())  throw new ApiException( "author account not activate yet :(" );
        if(meeting.getEndDate().isBefore(meeting.getStartDate()))  throw new ApiException( "End time should be after start date" );

        meeting.setListeners(new ArrayList<>());
        meetingRepository.save(meeting);
    }

    public List<Meeting> allMeeting(){return meetingRepository.findAll();}

    public void updateMeeting(Integer id,Meeting edit){
        Meeting meeting = meetingRepository.findMeetingById(id);
        if(meeting==null) throw new ApiException( "meeting not found" );

        if(edit.getEndDate().isBefore(edit.getStartDate())) throw new ApiException( "End time should be after start date" );

        //===
        meeting.setStartDate(edit.getStartDate());
        meeting.setEndDate(edit.getEndDate());
        meeting.setLimitListeners(edit.getLimitListeners());
        meeting.setLinkURL(edit.getLinkURL());

        meetingRepository.save(meeting);

    }

    public void cancelMeeting(Integer id){
        Meeting meeting = meetingRepository.findMeetingById(id);
        if(meeting==null)throw new ApiException("meeting not found");
        meetingRepository.delete(meeting);
    }

    //===================< E N D of C R U D >========================================< Start E.E.P >====================\\

    public void addListener(Integer userId,Integer meetingId){

        Meeting meeting = meetingRepository.findMeetingById(meetingId);
        ArrayList<Integer> upd = meeting.getListeners();
        upd.add(userId);
        meeting.setListeners(upd);
        meetingRepository.save(meeting);

    }

    public List<Meeting> myMeetings(Integer auth){
        Author author = authorRepository.findAuthorById(auth);
        if(author==null) throw new ApiException("author not found");
    return meetingRepository.findMeetingByAuthorId(auth);
    }


    public Meeting getLink( Integer jr){

        JoinRequest request = joinRequestRepository.findJoinRequestById(jr);
        if(request==null) throw new ApiException("request not found");

        if(!request.getStatus().equals("approved")) throw new ApiException("you don't have permission to enter the meeting!");
        return meetingRepository.findMeetingById(request.getMeetingId());
    }

}
