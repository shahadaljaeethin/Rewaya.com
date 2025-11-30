package com.example.Rewaya.Model;
import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Novel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Enter Novel title")
    @Size(min=2,max=25,message = "Novel title must be between 2-25 char")
    @Column(columnDefinition = "varchar(25) not null")
    private String title;

    @NotEmpty(message = "Enter Novel overview")
    @Size(min=100,max=500,message = "Novel overview must be between 100-500 char")
    @Lob
    private String overview;

    @ElementCollection
    @CollectionTable(name = "novel_categories", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "category")
    @Size(min=1, message ="choose at least 1 category")
    private List<@Pattern(
                regexp = "(?i)^(Fantasy|SciFi|Romance|Drama|Action|Horror|Mystery|Thriller|Historical|Comedy|YoungAdult|Psychological|Social|Detective|Adventure|Philosophical)$",
                message = "Invalid category"
        ) String> categories;

    @NotNull(message = "enter the age category")
    @Min(value = 13, message = "minimum age category is +13")
    @Max(value = 18, message = "maximum age category is +18")
    @Column(columnDefinition = "int not null")
    private Integer ageCategory;

    @Column(nullable = false)
    private Boolean isCompleted;


    private ArrayList<Integer> likes; //size = 0 = 0 likes

    @PastOrPresent
    private LocalDate publishDate;

    @NotEmpty(message = "upload cover image of the novel")
    private String coverImg;

    @NotNull(message = "log in as author")
    private Integer authorId;

}

