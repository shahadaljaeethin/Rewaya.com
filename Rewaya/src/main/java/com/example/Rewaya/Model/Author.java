package com.example.Rewaya.Model;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Author{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Size(max = 20,message = "maximum length for name is 20 char")
    @Column(columnDefinition = "varchar(20)")
    private String name;

    @NotEmpty(message = "fill username")
    @Size(min=3,max=24,message = "length must be from 3-24 char")
    @Column(columnDefinition = "varchar(20) unique not null")
    private String username;

    @NotEmpty(message = "fill password")
    //Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-]).{8,30}$",message = "password must contains at least one small&capital letter, one digit and special character, with minimum of 8 characters")
    @Column(columnDefinition = "varchar(100) not null") //hashing
    private String password;

    @NotEmpty(message = "fill email")
    @Email(message = "invalid email")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String email;


    @NotEmpty(message = "enter phone number")
    @Size(min=10,max=10,message = "length must be 10")
    @Pattern(regexp = "^05\\d{8}$", message = "phone-number must start with 05")
    @Column(columnDefinition = "varchar(10) unique not null")
    private String phoneNumber;


    @NotEmpty(message = "fill freelancer code")
    @Column(columnDefinition = "varchar(30) unique not null")
    private String freelancerCode;


    @Column(nullable = false)
    private Boolean active;

    //can be empty -> default img.jpeg
    private String pfpURL;

    @PastOrPresent
    private LocalDate registerDate;


}
