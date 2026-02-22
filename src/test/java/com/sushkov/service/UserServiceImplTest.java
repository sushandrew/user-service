package com.sushkov.service;

import com.sushkov.domain.User;
import com.sushkov.infrastructure.UserDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserDAO userDAO;

    @InjectMocks
    UserServiceImpl userService;


    @Test
    void UserServiceFindUser_WhenIdEquals1_ThenGetUserWithId1() {
        User expectedUser = new User(1, "Иванов Иван Иванович", "ivanov@example.com",
                25, LocalDate.of(2026, 2, 13));
//
        when(userDAO.findById(1)).thenReturn(expectedUser);

        User user = userService.findUser(1);

        assertEquals(expectedUser, user);
    }

    @Test
    void UserServiceFindAllUsers_GetListOfUsers() {
        List<User> expectedUsers = List.of(
                new User(1,"Иванов Иван Иванович", "ivanov@example.com", 25,
                        LocalDate.of(2026, 2, 13)),
                new User("Петров Петр Петрович", "petrov@example.com", 21,
                        LocalDate.of(2026, 2, 14)),
                new User("Беляева Валентина Валерьевна", "beliaeva@example.com", 23,
                        LocalDate.of(2026, 2, 14))
        );

        when(userDAO.findAll()).thenReturn(expectedUsers);

        List<User> users = userService.findAllUsers();

        assertEquals(expectedUsers, users);
    }

    @Test
    void UserServiceSaveUser_WhenCreateNewUser_ThenUserDAOSaveExecutes1Time() {
        User user = new User(1,"Иванов Иван Иванович", "ivanov@example.com", 25,
                LocalDate.of(2026, 2, 13));

        userService.saveUser(user);

        verify(userDAO, times(1)).save(user);
    }

    @Test
    void UserServiceUpdateUser_WhenUpdateNameOfUserWithId1_ThenUserDAOUpdateExecutes1Time() {
        when(userDAO.findById(1)).thenReturn(new User(1,"Иванов Иван Иванович", "ivanov@example.com", 25,
                LocalDate.of(2026, 2, 13)));
        User user = userService.findUser(1);
        user.setName("Тестовое имя");

        userService.updateUser(user);

        verify(userDAO, times(1)).update(user);
    }

    @Test
    void UserServiceDeleteUser_WhenDeleteUserWithId1_ThenUserDAODeleteExecutes1Time() {
        User user = new User(1,"Иванов Иван Иванович", "ivanov@example.com", 25,
                LocalDate.of(2026, 2, 13));

        when(userDAO.findById(1)).thenReturn(user);

        userService.deleteUser(1);

        verify(userDAO, times(1)).delete(user);
    }

    @Test
    void UserServiceDeleteAllUsers_UserDAODeleteAllExecutes1Time() {
        userService.deleteAllUsers();

        verify(userDAO, times(1)).deleteAll();
    }
}