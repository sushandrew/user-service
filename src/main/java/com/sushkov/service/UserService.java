package com.sushkov.service;

import com.sushkov.domain.User;

import java.util.List;

public interface UserService {
    User findUser(int id);
    List<User> findAllUsers();
    void saveUser(User user);
    void updateUser(User user);
    void deleteUser(int id);
    void deleteAllUsers();
}
