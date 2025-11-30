package com.example.Rewaya.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Meeting{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "enter meeting title")
    @Size(min=8,max=30,message = "length of title is 8-30 range")
    @Column(columnDefinition = "varchar(30) not null")
    private String title;

    @NotNull(message = "enter the start time")
    @Future(message = "time should be from future")
    @Column(nullable = false)
    private LocalDateTime startDate;
    @NotNull(message = "enter the start time")
    @Future(message = "time should be from future")
    @Column(nullable = false)
    private LocalDateTime endDate;

    @NotNull(message = "enter maximum number of listeners of this meeting")
    @Min(value = 1,message = "meeting should have at least one listener")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer limitListeners;

    @NotNull(message = "enter minimum age category for this meeting")
    @Min(value = 13,message = "minimum age should be 13Y")
    @Max(value = 20,message = "meeting minimum range limit is 20Y")
    @Positive
    @Column(columnDefinition = "int not null")
    private Integer ageRange;



    @NotNull(message = "choose if you want to accept listens by manually or Auto by seats number")
    @Column(nullable = false)
    private boolean acceptManually;

    private ArrayList<Integer> listeners = new ArrayList<>();

    @NotEmpty(message = "enter the meeting URL")
    @Column(columnDefinition = "varchar(255) not null")
    private String linkURL;

    @NotNull(message = "log in as author")
    private Integer authorId;


}
