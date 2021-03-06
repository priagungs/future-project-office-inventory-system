package com.future.assist.service.service_impl;

import com.future.assist.model.entity_model.User;
import com.future.assist.service.service_interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LoggedinUserInfo {
    @Autowired
    private UserService userService;

    public User getUser() {
        return userService.readUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
