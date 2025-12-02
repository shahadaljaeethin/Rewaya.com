package com.example.Rewaya.Controller;

import com.example.Rewaya.Api.ApiResponse;
import com.example.Rewaya.Model.JoinRequest;
import com.example.Rewaya.Service.JoinRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/rewaya/join request")
@RequiredArgsConstructor
public class JoinRequestController {
    private final JoinRequestService joinRequestService;

    @PostMapping("/send")
    public ResponseEntity<?> sendRequest(@RequestBody @Valid JoinRequest jr){
        String result = joinRequestService.sendRequest(jr);
         return ResponseEntity.status(200).body(result);
    }

    @GetMapping("/get")
    public ResponseEntity<?> allJr(){
        return ResponseEntity.status(200).body(joinRequestService.allJr());
                                    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJoinRequest(@PathVariable Integer id,@RequestBody @Valid JoinRequest edit){
        joinRequestService.updateJoinRequest(id, edit) ;
        return ResponseEntity.status(200).body("updated");
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelJr(@PathVariable Integer id) {
        joinRequestService.cancelJr(id);
        return ResponseEntity.status(200).body("join request canceled");
    }
//================================================================================================================================\\

    @PutMapping("/approve request/{authorId}/{jrId}")
    public ResponseEntity<?> approveJoinRequest(@PathVariable Integer jrId, @PathVariable Integer authorId)
    {
       joinRequestService.approveRequest(jrId, authorId);
       return ResponseEntity.status(200).body(new ApiResponse("requets approved"));
    }


    @PutMapping("/reject request/{authorId}/{jrId}")
    public ResponseEntity<?> rejectJoinRequest(@PathVariable Integer jrId, @PathVariable Integer authorId)
    {
        joinRequestService.rejectRequest(jrId, authorId);
      return ResponseEntity.status(200).body(new ApiResponse("request rejected"));
    }

    @GetMapping("/my approved requests/{userId}")
    public ResponseEntity<?> myApprovedRequest(@PathVariable Integer userId)
    {
         return ResponseEntity.status(200).body(joinRequestService.myApprovedRequest(userId));
    }

    @GetMapping("/my requests/{userId}")
    public ResponseEntity<?> allJoinReqOfMine(@PathVariable Integer userId){
         return ResponseEntity.status(200).body(joinRequestService.allJoinReqOfMine(userId));
    }
}
