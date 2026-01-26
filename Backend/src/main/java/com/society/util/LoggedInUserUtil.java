package com.society.util;

import com.society.entity.User;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class LoggedInUserUtil {

    public User getUser(HttpSession session) {

        User user = (User) session.getAttribute("LOGGED_IN_USER");

        if (user == null) {
            throw new RuntimeException("User not logged in");
        }
        return user;
    }
}
