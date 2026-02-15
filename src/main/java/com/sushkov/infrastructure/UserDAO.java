package com.sushkov.infrastructure;

import com.sushkov.domain.User;

import java.util.List;

public interface UserDAO {
    User findById(int id);
    List<User> findAll();
    void save(User user);
    void update(User user);
    void delete(User user);
}
