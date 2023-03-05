package com.example.pradhuman.services;

import com.example.pradhuman.entities.User;
import com.example.pradhuman.repositories.UserRepository;
import com.example.pradhuman.utils.Jutil;
import com.example.pradhuman.utils.PasswordManager;
import com.example.pradhuman.utils.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Override
    public User getById(String id) {
        User user = userRepository.findById(id).orElse(User.builder().found(false).build());
        try {
            user.setPassword(PasswordManager.decrypt(user.getPassword(), PasswordManager.getSecretKey()));
            user.setPassword(Jutil.maskString(user.getPassword(),
                    2,user.getPassword().length()-2,'*'));

        } catch (Exception e) {
            throw new RuntimeException("error in password decryption");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        users.stream().forEach((u) -> {
            try {
                u.setPassword(PasswordManager.decrypt(u.getPassword(), PasswordManager.getSecretKey()));
                u.setPassword(Jutil.maskString(u.getPassword(),2,u.getPassword().length()-2,'*'));
            } catch (Exception e) {
                throw new RuntimeException("Error in decrypting password", e);
            }
        });
        return users;
    }

    @Override
    public User addUser(User user) throws ValidationException {
        Jutil.validateUser(user);
        user.setUserId(UUID.randomUUID().toString());
        try {
            user.setPassword(PasswordManager.encrypt(user.getPassword(), PasswordManager.getSecretKey()));
        }catch (GeneralSecurityException e){
            throw new RuntimeException("Error in password encryption");
        }

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) throws ValidationException {
        if(userRepository.findById(user.getUserId()).isPresent()){
            User oldUser = userRepository.findById(user.getUserId()).get();
            Jutil.getUpdatedUser(oldUser, user);
            Jutil.validateUser(oldUser);
            try {
                oldUser.setPassword(PasswordManager.encrypt(oldUser.getPassword(), PasswordManager.getSecretKey()));
            } catch (GeneralSecurityException e) {
                throw new RuntimeException("Error in password Encryption");
            }
            return userRepository.save(oldUser);
        }
        return User.builder().build();

    }

    @Override
    public boolean deleteUser(String id) {
        if(userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            user.setDisabled(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
