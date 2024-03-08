package com.example.REATapi.config;

import com.example.REATapi.Entities.User;
import com.example.REATapi.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user  = userRepository.findByEmail(username);
        if(user==null)
        {
            throw new UsernameNotFoundException("Usre Not Found");
        }else{
            return new CustomUser(user);
        }
    }
}
