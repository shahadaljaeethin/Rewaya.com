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
    public ResponseEntity<?> announceMeeting(@RequestBody @Valid Meeting meeting){
        meetingService.announceMeeting(meeting);
        return ResponseEntity.status(200).body(new ApiResponse( "meeting announced :) thank you for supporting community"));

    }

    @GetMapping("/get")
    public ResponseEntity<?> allMeeting(){  return ResponseEntity.status(200).body(meetingService.allMeeting()); }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMeeting(@PathVariable Integer id, @RequestBody @Valid Meeting edit){
         meetingService.updateMeeting(id, edit);
        return ResponseEntity.status(200).body(new ApiResponse( "updated") );
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelMeeting(@PathVariable Integer id){
       meetingService.cancelMeeting(id);
       return ResponseEntity.status(200).body(new ApiResponse("meeting canceled"));
    }
//========

    @GetMapping("/my meetings/{authId}")
    public ResponseEntity<?> myMeetings(@PathVariable Integer authId){
        return ResponseEntity.status(200).body(meetingService.myMeetings(authId));
    }

    @GetMapping("/get link")
    public ResponseEntity<?> getLink(@PathVariable Integer jr){
      return ResponseEntity.status(200).body(meetingService.getLink(jr));
    }

}
