package com.mvp.kfz.service.implementation;

import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.repository.UserRepository;
import com.mvp.kfz.service.ContextUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContextUserServiceImpl implements ContextUserService {

    private final UserRepository userRepository;
    @Override
    public Users retrieveContextUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername()).orElseThrow(() -> new UsernameNotFoundException("No user found"));
    }
}
