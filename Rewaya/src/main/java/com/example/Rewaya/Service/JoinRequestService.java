package com.example.Rewaya.Service;

import com.example.Rewaya.Api.ApiException;
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

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class JoinRequestService {
    private final MeetingRepository meetingRepository;
    private final JoinRequestRepository joinRequestRepository;
    private final MeetingService meetingService;
    private final UserRepository userRepository;
    private final SendEmail sendEmail;
    private final AuthorRepository authorRepository;



    //add
    public String sendRequest(JoinRequest jr){

        Meeting meeting = meetingRepository.findMeetingById(jr.getMeetingId());
        if(meeting==null) throw new ApiException("meeting not found or cancelled");
        if(meeting.getLimitListeners()==meeting.getListeners().size()) throw new ApiException("this meeting is full");
        if(meeting.getStartDate().isAfter(LocalDateTime.now())) throw new ApiException("meeting is expired");



        User user = userRepository.findUserById(jr.getUserId());
        if(user==null) throw new ApiException("log in as user");
        if(user.getAge()<meeting.getAgeRange()) throw new ApiException("this meeting's age category is not suitable for your age :(");

        jr.setRequestTime(LocalDateTime.now());
        jr.setStatus("pending");
        joinRequestRepository.save(jr);


        //if author set meeting to Auto accept by available seats :
        if(!meeting.isAcceptManually()){
            //set status to approved :)
            approveRequest(jr.getId(), meeting.getAuthorId());
                return "your request is approved! link details are sent to your email. or check my join request pages";

        }





        //notify author
        sendEmail.notifyAuthor(jr);
        return "Request sent to the Author :)";

    }

    public List<JoinRequest> allJr(){return joinRequestRepository.findAll();}

    public void updateJoinRequest(Integer id,JoinRequest edit){
        JoinRequest jr = joinRequestRepository.findJoinRequestById(id);
        if(jr==null) throw new ApiException("request not found");


         jr.setMessage(edit.getMessage());
         joinRequestRepository.save(jr);
    }

    public void cancelJr(Integer id){
        JoinRequest jr = joinRequestRepository.findJoinRequestById(id);
        if(jr==null) throw new ApiException("request not found");
        joinRequestRepository.delete(jr);
    }
    //================================================= Extra End Points :

    public void approveRequest(Integer jrId, Integer author){

        JoinRequest jr = joinRequestRepository.findJoinRequestById(jrId);
        if(jr==null) throw new ApiException("Join request not found or deleted");
        if(jr.getStatus().equals("approved")) throw new ApiException("this request is already approve");

        Author author1 = authorRepository.findAuthorById(author);
        if(author1==null) throw new ApiException("author not found");
        Meeting meeting = meetingRepository.findMeetingById(jr.getMeetingId());
        if(!author1.getId().equals(meeting.getAuthorId())) throw new ApiException("this meeting belongs to another author");


        if(meeting.getLimitListeners()==meeting.getListeners().size()) throw new ApiException("this meeting is full");
        meetingService.addListener(jr.getUserId(), jr.getMeetingId());
        jr.setStatus("approved");
        joinRequestRepository.save(jr);
        sendEmail.sendMeetingLink(jr); //send link to user
    }

    public void rejectRequest(Integer jrId, Integer author){

        JoinRequest jr = joinRequestRepository.findJoinRequestById(jrId);
        if(jr==null) throw new ApiException( "Join request not found or deleted" );
        if(jr.getStatus().equals("rejected")) throw new ApiException( "this request is already rejected" );
        if(jr.getStatus().equals("approved")) throw new ApiException( "you can't reject an accepted request" );

        Author author1 = authorRepository.findAuthorById(author);
        if(author1==null) throw new ApiException("author not found");
        Meeting meeting = meetingRepository.findMeetingById(jr.getMeetingId());
        if(!author1.getId().equals(meeting.getAuthorId())) throw new ApiException("this meeting belongs to another author");

        jr.setStatus("rejected");
        joinRequestRepository.save(jr);
    }








    public List<JoinRequest> myApprovedRequest(Integer userId){
        User user = userRepository.findUserById(userId);
        if(user==null) return null;
        return joinRequestRepository.getMyApprovedRequests(userId);
    }

    public List<JoinRequest> allJoinReqOfMine(Integer userId){
        User user = userRepository.findUserById(userId);
        if(user==null) return null;
        return joinRequestRepository.findJoinRequestByUserId(userId);
    }

}
