package com.example.Rewaya.Controller;

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
    public ResponseEntity<?> sendRequest(@RequestBody @Valid JoinRequest jr, Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        String result = joinRequestService.sendRequest(jr);
        if(result.startsWith("your request is approved") || result.startsWith("Request sent"))
            return ResponseEntity.status(200).body(result);

        return ResponseEntity.status(400).body(result);

    }

    @GetMapping("/get")
    public ResponseEntity<?> allJr(){
        return ResponseEntity.status(200).body(joinRequestService.allJr());
                                    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJoinRequest(@PathVariable Integer id,@RequestBody @Valid JoinRequest edit,Errors errors){
        if(errors.hasErrors()) return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());


        if(joinRequestService.updateJoinRequest(id, edit)) return ResponseEntity.status(200).body("updated");

        return ResponseEntity.status(400).body("request not found");
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelJr(@PathVariable Integer id){
        if(joinRequestService.cancelJr(id)) return ResponseEntity.status(200).body("join request canceled");
        return ResponseEntity.status(400).body("join request not found");}
//================================================================================================================================\\

    @PutMapping("/approve request/{authorId}/{jrId}")
    public ResponseEntity<?> approveJoinRequest(@PathVariable Integer jrId, @PathVariable Integer authorId)
    {
        String result = joinRequestService.approveRequest(jrId, authorId);
        if(result.contains("approved")) return ResponseEntity.status(200).body(result);
        return ResponseEntity.status(400).body(result);
    }


    @PutMapping("/reject request/{authorId}/{jrId}")
    public ResponseEntity<?> rejectJoinRequest(@PathVariable Integer jrId, @PathVariable Integer authorId)
    {
        String result = joinRequestService.rejectRequest(jrId, authorId);
        if(result.contains("rejected")) return ResponseEntity.status(200).body(result);
        return ResponseEntity.status(400).body(result);
    }

    @GetMapping("/my approved requests/{userId}")
    public ResponseEntity<?> myApprovedRequest(@PathVariable Integer userId)
    {
        var list = joinRequestService.myApprovedRequest(userId);
        if(list != null) return ResponseEntity.status(200).body(list);
        return ResponseEntity.status(400).body("user not found");
    }

    @GetMapping("/my requests/{userId}")
    public ResponseEntity<?> allJoinReqOfMine(@PathVariable Integer userId){
        var list = joinRequestService.allJoinReqOfMine(userId);
        if(list != null) return ResponseEntity.status(200).body(list);
        return ResponseEntity.status(400).body("user not found");
    }
}
