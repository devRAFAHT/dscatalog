package com.andradscorporation.dscatalog.services;

import com.andradscorporation.dscatalog.entities.User;
import com.andradscorporation.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUserName(username);
        if(user != null){
            return user;
        }else{
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
    }
}
