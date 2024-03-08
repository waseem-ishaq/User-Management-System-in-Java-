package com.example.REATapi.Services;

import com.example.REATapi.Entities.User;
import org.springframework.stereotype.Service;

public interface UserService {
    public User saveUser(User user);

    public void removeSessionMessage();
}
