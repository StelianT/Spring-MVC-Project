package com.motivation.services;

public interface UserService {

    Long getUserIdByUsername(String username);
    String getFullNameByUsername(String username);
    String getFullNameByUserId(Long userId);
    String getUsernameByUserId(Long userId);
}
