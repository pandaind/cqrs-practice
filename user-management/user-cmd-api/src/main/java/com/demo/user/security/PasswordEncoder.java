package com.demo.user.security;

public interface PasswordEncoder {
    String hashedPassword(String password);
}
