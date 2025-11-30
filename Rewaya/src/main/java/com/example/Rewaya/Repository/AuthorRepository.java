package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Author findAuthorById(Integer id);
    Author findAuthorByUsernameAndPassword(String username,String password);
}

