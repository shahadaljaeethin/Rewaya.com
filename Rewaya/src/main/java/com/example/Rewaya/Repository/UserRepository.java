package com.example.Rewaya.Repository;

import com.example.Rewaya.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findUserById(Integer id);
    User findUserByUsernameAndPassword(String username, String password);
    User findUserByRole(String role);
}
