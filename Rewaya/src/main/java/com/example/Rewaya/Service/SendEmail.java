package com.example.Rewaya.Service;

import com.example.Rewaya.Model.Author;
import com.example.Rewaya.Model.JoinRequest;
import com.example.Rewaya.Model.Meeting;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Repository.AuthorRepository;
import com.example.Rewaya.Repository.MeetingRepository;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendEmail {
    private final UserRepository userRepository;
    private final AuthorRepository authorRepository;
    private final MeetingRepository meetingRepository;



    @Autowired
    private JavaMailSender javaEmailSender;

    public boolean notifyAdmin(Author author){
        User admin = userRepository.findUserByRole("admin");
        if(admin==null) return false;
        String adminMail = admin.getEmail();

        String body = "a new Author has been registered, please contact them.  contact info: "+author.getName()+" | "+author.getPhoneNumber()+" | "+author.getEmail()+" | Freelancer code: "+author.getFreelancerCode();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(adminMail);
        simpleMailMessage.setFrom("rewaya.website26@gmail.com");
        simpleMailMessage.setSubject("New Task");
        simpleMailMessage.setText(body);
        javaEmailSender.send(simpleMailMessage);
        return true;

    }

    public void sendMeetingLink(JoinRequest jr){

        User user = userRepository.findUserById(jr.getUserId());
        Meeting meeting = meetingRepository.findMeetingById(jr.getMeetingId());

    String body = "Dear "+user.getUsername()+", this is the meeting link you requested to join of title: "+meeting.getTitle()+"."
     +" on date "+meeting.getStartDate()+" - "+meeting.getEndDate()+", LINK: [ "+meeting.getLinkURL()+" ] Please join on time";

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom("rewaya.website26@gmail.com");
        simpleMailMessage.setSubject("Meeting Link :)");
        simpleMailMessage.setText(body);
        javaEmailSender.send(simpleMailMessage);

    }

    public void notifyAuthor(JoinRequest jr){

        Meeting meeting = meetingRepository.findMeetingById(jr.getMeetingId());
        Author author = authorRepository.findAuthorById(meeting.getAuthorId());
        String URL = "http://localhost:8080/api/v1/rewaya/join request/approve request/"+author.getId()+"/"+jr.getId();
        String body = "Dear author "+author.getName()+", a new join request send to the meeting of yours [ "+meeting.getTitle()+" ] " +
                ", with request message : ["+jr.getMessage()+" ] "
                +
                "please go to this page to approve them -> "+URL;

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(author.getEmail());
        simpleMailMessage.setFrom("rewaya.website26@gmail.com");
        simpleMailMessage.setSubject("New Request for "+meeting.getTitle());
        simpleMailMessage.setText(body);
        javaEmailSender.send(simpleMailMessage);

    }

}
