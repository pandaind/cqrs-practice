package com.demo.user.core.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    @Size(min = 2, message = "username must have a minimum of 2 characters")
    @Indexed(unique=true)
    private String username;
    @Size(min = 7, message = "username must have a minimum of 7 characters")
    private String password;
    @Valid
    @NotNull(message = "specify at least one role")
    private List<Role> roles;
}
