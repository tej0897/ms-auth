package com.tej0897.msauth.service;

import com.tej0897.msauth.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final FirestoreService firestoreService;

    public UserDetailsServiceImpl(FirestoreService firestoreService) {
        this.firestoreService = firestoreService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = firestoreService.getUserByUsername(username);
        } catch (ExecutionException | InterruptedException e) {
            throw new UsernameNotFoundException("Error accessing Firestore", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(user.getUsername());
        builder.password(user.getPasswordHash());
        builder.roles("USER");

        return builder.build();
    }
}