package com.mirea.coursework.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HeaderService {
    public String isUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getName().equals("admin")) {
            return "admin";
        }
        return "user";
    }
}
