package com.example.Rewaya.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JoinRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Pattern(regexp = "(?i)^(pending|approved|rejected)$")
    @Column(columnDefinition = "varchar(30) not null")
    private String status;

    @NotEmpty(message = "enter why you want to join the meeting, so the author can approve")
    @Size(min = 8,max = 100,message = "message should be between 8-100 char")
    @Column(columnDefinition = "varchar(80) not null")
    private String message;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime requestTime;

    @NotNull(message = "enter meeting")
    private Integer meetingId;

    @NotNull(message = "log in as user")
    private Integer userId;


}
