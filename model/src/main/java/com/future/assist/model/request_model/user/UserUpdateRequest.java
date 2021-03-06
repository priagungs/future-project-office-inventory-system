package com.future.assist.model.request_model.user;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private Long idUser;
    private Boolean isAdmin;
    private String name;
    private String username;
    private String password;
    private String pictureURL;
    private String division;
    private String role;
    private UserModelRequest superior;
}
