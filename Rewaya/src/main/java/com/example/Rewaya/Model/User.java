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
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max=20, message = "name max is 20 character")
    @Column(columnDefinition = "varchar(20)")
    private String name; //not all readers care about name


    @NotEmpty(message = "enter username")
    @Size(min=3, max=25, message = "username max is 25 character")
    @Column(columnDefinition = "varchar(25) unique not null")
    private String username;

    @NotEmpty(message = "enter the email")
    @Email(message = "write valid email")
    @Size(max = 50,message = "email too long")
    @Column(columnDefinition = "varchar(50) unique")
    private String email;

    @NotEmpty(message = "enter the password")
    //Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,30}$",message = "password must contains at least one small & capital letter, one digit with minimum of 8 characters")
    @Column(columnDefinition = "varchar(150) not null") //long hashing
    private String password;

    @NotNull(message = "enter the age")
    @Positive(message = "age must be in positive")
    @Min(value = 13, message = "minimum age is 13")
    @Column(columnDefinition = "int not null")
    private Integer age;


    @NotEmpty(message = "enter the role")
    @Pattern(regexp = "^(admin|user)$",message = "role can be admin or user")
    @Column(columnDefinition = "varchar(5)")
    private String role;

    @Size(max = 500,message = "maximum bio is 500 char")
    @Lob
    private String aboutMe; //can be empty

    @PastOrPresent
    private LocalDate registerDate;

    //can be empty -> default img.jpeg
    private String pfpURL;

    private ArrayList<String> favoriteCategories = new ArrayList<>();



}
