package com.example.Rewaya.Service;

import com.example.Rewaya.Api.ApiException;
import com.example.Rewaya.Model.User;
import com.example.Rewaya.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService{
private final UserRepository userRepository;

//CRUD]=================================================================== (0w0)

    public void addUser(User user){
        user.setPassword(hashPass(user.getPassword()));
        user.setRegisterDate(LocalDate.now());
        user.setAboutMe(" ");
        user.setFavoriteCategories(new ArrayList<>());
        if(user.getPfpURL()==null) user.setPfpURL("mystery_user.jpeg"); //if user didn't upload pfp
        userRepository.save(user);}


    public List<User> getAll(){
        return userRepository.findAll();}

    public void updateUser(Integer id,User newInfo){

        User user = userRepository.findUserById(id);
        if(user==null)throw new ApiException("user not found");

        user.setName(newInfo.getName());
        user.setUsername(newInfo.getUsername());
        user.setEmail(newInfo.getEmail());
        user.setPassword(hashPass(newInfo.getPassword()));
        user.setAge(newInfo.getAge());
        user.setRole(newInfo.getRole());
        user.setPfpURL(newInfo.getPfpURL());
        user.setAboutMe(newInfo.getAboutMe());


        userRepository.save(user);
    }

    public void deleteUser(Integer id){

        User user = userRepository.findUserById(id);
        if(user==null) throw new ApiException("user not found");

        userRepository.delete(user);
    }



//========================================================================= extra E.P. =====(0-0)

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

    public User logIn(String[] info){

        try {
            String username =  info[0];
            String password = info[1];
            password = hashPass(password); //since pass is saved as hash
            //==
            return userRepository.findUserByUsernameAndPassword(username, password);
            }
        catch (Exception e){throw new ApiException("password or username is wrong");}

    }

    public void addBio(String aboutMe,Integer id){
    User user = userRepository.findUserById(id);
    if(user==null) throw new ApiException("user not found");
    if(aboutMe.length()>500) throw new ApiException("bio too long");
    user.setAboutMe(aboutMe);
    }

    public void  addToFavCategory(Integer id,String category){

        User user = userRepository.findUserById(id);
        if(user==null) throw new ApiException("user not found");

        String[] cate = {"Fantasy","SciFi","Romance","Drama","Action","Horror","Mystery","Thriller","Historical","Comedy","YoungAdult","Psychological","Social","Detective","Adventure","Philosophical"};
        for(String c:cate){

        if(c.equals(category)){

            ArrayList<String> favCat = user.getFavoriteCategories();
            favCat.add(category);
            user.setFavoriteCategories(favCat);
            userRepository.save(user);
                }

        }
        throw new ApiException( "invalid category" );

    }

    public void removeFavCate(Integer id,String category) {

        User user = userRepository.findUserById(id);
        if (user == null) throw new ApiException("user not found");

        ArrayList<String> fav = user.getFavoriteCategories();
        for(String cate:fav) {
            if(cate.equals(category)){
                fav.remove(cate);
                user.setFavoriteCategories(fav);
                userRepository.save(user);
            }

        }
        throw new ApiException( "category not found in list" );
    }




}
