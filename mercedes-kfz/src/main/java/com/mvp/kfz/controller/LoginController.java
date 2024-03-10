package com.mvp.kfz.controller;

import com.mvp.kfz.data.LoginInput;
import com.mvp.kfz.data.LoginOutput;
import com.mvp.kfz.data.entity.Users;
import com.mvp.kfz.service.UserService;
import com.mvp.kfz.util.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final UserService iUserService;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenUtil jwtTokenUtil;

    @Operation(summary = "Login", description = "Login in using username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details and JWT token",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginOutput.class))})
    })
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

    @Operation(summary = "Register or signup", description = "Sign up as new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "new assigned ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Long.class))})
    })
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody LoginInput loginInput) {

        Users newUsers = Users.builder()
                .username(loginInput.getUsername())
                .password(passwordEncoder.encode(loginInput.getPassword()))
                .build();
        Long userId = iUserService.addUser(newUsers);
        return ResponseEntity.ok(userId);
    }

    @Operation(summary = "Get user by ID", description = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found user by ID",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Users.class))})
    })
    @GetMapping(path = "/myUser/{id}")
    public ResponseEntity<Users> myUser(@PathVariable("id") Long userId) {
        Users foundUsers = iUserService.getUserById(userId);
        return ResponseEntity.ok(foundUsers);
    }
}
