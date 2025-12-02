package com.example.Rewaya.Service;

import com.example.Rewaya.Api.ApiException;
import com.example.Rewaya.Model.Author;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Repository.AuthorRepository;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;
    private final SendEmail sendEmail;
    //---------------

    public void registerAuthor(Author author){

        if(author.getPfpURL()==null) author.setPfpURL("mystery_author.jpeg");
        author.setActive(false);
        author.setRegisterDate(LocalDate.now());
        author.setPassword(hashPass(author.getPassword())); //hash pass
        authorRepository.save(author);

         //tired of email spams
       // sendEmail.notifyAdmin(author);

    }

    public List<Author> getAll(){return authorRepository.findAll();}


    public void updateAuthor(Integer id,Author upd){
    Author author = authorRepository.findAuthorById(id);
    if(author==null) throw new ApiException("Author not found");

    author.setName(upd.getName());
    author.setUsername(upd.getUsername());
    author.setEmail(upd.getEmail());
    author.setPassword(hashPass(upd.getPassword())); //hash
    author.setFreelancerCode(upd.getFreelancerCode());
    author.setPhoneNumber(upd.getPhoneNumber());

    authorRepository.save(author);
    }

    public void deleteAuthor(Integer id){
        Author author = authorRepository.findAuthorById(id);
        if(author==null) throw new ApiException("Author not found");
        authorRepository.delete(author);
    }




//=========================== END OF CRUD AAH===============================================

    //( service method to hash passwords )

    public String hashPass(String password){
        try {
            //this method return the password as hashed for security database :)
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    //E.E.P    Activate / freeze an author

    public void activateAuth(Integer admin,Integer authorId){

        Author auth = authorRepository.findAuthorById(authorId);
        if(auth==null) throw new ApiException("author not found");

        User user = userRepository.findUserById(admin);
        if(user==null) throw new ApiException("admin not found");
        if(!user.getRole().equals("admin")) throw new ApiException("role not allowed to do this operation!");

        if(auth.getActive().equals(true)) throw new ApiException("author account is already active");

        auth.setActive(true);
        authorRepository.save(auth);
    }

    public void freezeAuth(Integer admin,Integer authorId){

        Author auth = authorRepository.findAuthorById(authorId);
        if(auth==null) throw new ApiException("author not found");

        User user = userRepository.findUserById(admin);
        if(user==null) throw new ApiException("admin not found");
        if(!user.getRole().equals("admin")) throw new ApiException("role not allowed to do this operation!");

        if(auth.getActive().equals(false)) throw new ApiException("author account is already frozen");

        auth.setActive(false);
        authorRepository.save(auth);

    }




    public Author logIn(String[] cred){

        try {
            String username =  cred[0];
            String password = cred[1];
            password = hashPass(password); //since password is saved as a hash

            return authorRepository.findAuthorByUsernameAndPassword(username, password);
        }
        catch (Exception e){ throw new ApiException("credential missing");}
    }

}
