package com.example.Rewaya.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Tip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Size(min=8,max = 480, message = "Tips maximum length is 480 character")
    private String content;

    @PastOrPresent
    private LocalDate publishDate;

    private ArrayList<Integer> likes; //size = 0 = 0 likes


    @NotNull(message = "log in as author")
    private Integer authorId;

}
