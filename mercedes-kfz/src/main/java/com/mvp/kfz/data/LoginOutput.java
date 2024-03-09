package com.mvp.kfz.data;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Jacksonized
@Builder
public class LoginOutput {

    private Long id;
    private String username;

    private String token;
}
