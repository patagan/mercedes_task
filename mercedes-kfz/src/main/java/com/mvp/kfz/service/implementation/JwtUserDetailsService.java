package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users foundUsers = userRepository.findByUsername(username).orElseThrow();
        return new User(foundUsers.getUsername(), foundUsers.getPassword(),
                new ArrayList<>());

    }
}
