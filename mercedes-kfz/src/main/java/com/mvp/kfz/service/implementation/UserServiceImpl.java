package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.LoginInput;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.repository.UserRepository;
import com.mvp.kfz.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Long addUser(Users users) {
        Optional<Users> foundUser = userRepository.findByUsername(users.getUsername());
        foundUser.ifPresent(x -> {throw new IllegalArgumentException(x.getUsername() + " already exists");});
        Users savedUsers = userRepository.save(users);
        return savedUsers.getId();
    }


    @Override
    public Users getUserDetails(LoginInput loginInput) {
        return userRepository.findByUsername(loginInput.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Users getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

}
