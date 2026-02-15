package com.sushkov.service;

import com.sushkov.domain.User;
import com.sushkov.infrastructure.UserDAO;
import com.sushkov.infrastructure.UserDAOImpl;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {
        userDAO = new UserDAOImpl();
    }

    public User findUser(int id) {
        return userDAO.findById(id);
    }

    public List<User> findAllUsers() {
        return userDAO.findAll();
    }

    public void saveUser(User user) {
        userDAO.save(user);
    }

    public void updateUser(User user) {
        userDAO.update(user);
    }

    public void deleteUser(int id) {
        User user = userDAO.findById(id);
        if (user == null) throw new RuntimeException("Не существует пользователя с введенным id!");
        userDAO.delete(user);
    }
}
