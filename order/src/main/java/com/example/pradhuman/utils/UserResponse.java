package com.example.pradhuman.utils;

import com.example.pradhuman.entities.User;
import lombok.NoArgsConstructor;

import java.util.List;

public class UserResponse extends BaseEntityResponse<User>{

    public UserResponse(User user){
        super(user);
    }

    public UserResponse(List<User> users){
        super(users);
    }
}
