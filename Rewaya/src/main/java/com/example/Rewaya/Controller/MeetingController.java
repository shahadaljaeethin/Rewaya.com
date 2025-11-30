package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.Meeting;
import com.example.Rewaya.Service.MeetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rewaya/meeting")
@RequiredArgsConstructor
public class MeetingController {
    private final MeetingService meetingService;


    @PostMapping("/announce")
    public ResponseEntity<?> announceMeeting(@RequestBody @Valid Meeting meeting, Errors errors){
         if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        String result = meetingService.announceMeeting(meeting);
        if(result.equals("meeting announced :) thank you for supporting community"))
            return ResponseEntity.status(200).body(new ApiResponse( result));
        return ResponseEntity.status(400).body(new ApiResponse( result));

    }

    @GetMapping("/get")
    public ResponseEntity<?> allMeeting(){  return ResponseEntity.status(200).body(meetingService.allMeeting()); }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMeeting(@PathVariable Integer id, @RequestBody @Valid Meeting edit, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        String result = meetingService.updateMeeting(id, edit);
        if(result.equals("updated"))
            return ResponseEntity.status(200).body(new ApiResponse( result) );
        return ResponseEntity.status(400).body( new ApiResponse(result));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelMeeting(@PathVariable Integer id){
        boolean done = meetingService.cancelMeeting(id);
        if(done) return ResponseEntity.status(200).body(new ApiResponse("meeting canceled"));
        return ResponseEntity.status(400).body("meeting not found");
    }
//========

    @GetMapping("/my meetings/{authId}")
    public ResponseEntity<?> myMeetings(@PathVariable Integer authId){
        if(meetingService.myMeetings(authId)==null) return ResponseEntity.status(400).body(new ApiResponse("author not found"));
        return ResponseEntity.status(200).body(meetingService.myMeetings(authId));
    }

    @GetMapping("/get link")
    public ResponseEntity<?> getLink(@PathVariable Integer jr){
     if(meetingService.getLink(jr) instanceof Meeting) return ResponseEntity.status(200).body(meetingService.getLink(jr));


    return ResponseEntity.status(400).body(meetingService.getLink(jr));
    }

}
