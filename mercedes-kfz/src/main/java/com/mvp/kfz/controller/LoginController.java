package com.mvp.kfz.controller;

import com.mvp.kfz.data.LoginInput;
import com.mvp.kfz.data.LoginOutput;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.service.IUserService;
import com.mvp.kfz.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final IUserService iUserService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;
    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginInput loginInput) {
        try {
            Users usersDetails = iUserService.getUserDetails(loginInput);
            boolean matches = passwordEncoder.matches(loginInput.getPassword(), usersDetails.getPassword());
            if (matches) {
                String token = jwtTokenUtil.generateToken(usersDetails.getUsername());
                return ResponseEntity.ok(LoginOutput.builder()
                                .id(usersDetails.getId())
                        .username(usersDetails.getUsername())
                        .token(token)
                        .build());
            }
        } catch (UsernameNotFoundException e) {
            log.info("User was not found");
        }
        return ResponseEntity.status(UNAUTHORIZED).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody LoginInput loginInput) {

        Users newUsers = Users.builder()
                .username(loginInput.getUsername())
                .password(passwordEncoder.encode(loginInput.getPassword()))
                .build();
        Long userId = iUserService.addUser(newUsers);
        return ResponseEntity.ok(userId);
    }

    @GetMapping(path = "/myUser/{id}")
    public ResponseEntity<Users> myUser(@PathVariable("id") Long userId) {
        Users foundUsers = iUserService.getUserById(userId);
        return ResponseEntity.ok(foundUsers);
    }
}
