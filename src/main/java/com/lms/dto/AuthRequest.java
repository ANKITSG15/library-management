package com.lms.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthRequest {
    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be blank")
    private String name;
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotNull(message = "mobile cannot be null")
    @NotBlank(message = "mobile cannot be blank")
    private String mobile;
}
