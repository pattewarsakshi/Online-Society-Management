package com.society.util;

import com.society.entity.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoggedInUserUtil {

    public User getUser(HttpSession session) {

        if (session == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        User user = (User) session.getAttribute("LOGGED_IN_USER");

        if (user == null) {
            throw new RuntimeException("UNAUTHORIZED");
        }

        return user;
    }
}
