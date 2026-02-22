package com.sushkov.infrastructure;

import com.sushkov.domain.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.util.List;

class UserDAOImplTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    UserDAO userDAO;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        HibernateSessionFactory.setUrl(postgres.getJdbcUrl());
        HibernateSessionFactory.setUsername(postgres.getUsername());
        HibernateSessionFactory.setPassword(postgres.getPassword());

        userDAO = new UserDAOImpl();

        userDAO.deleteAll();
        userDAO.save(new User("Иванов Иван Иванович", "ivanov@example.com", 25,
                LocalDate.of(2026, 2, 13)));
        userDAO.save(new User("Петров Петр Петрович", "petrov@example.com", 21,
                LocalDate.of(2026, 2, 14)));
        userDAO.save(new User("Беляева Валентина Валерьевна", "beliaeva@example.com", 23,
                LocalDate.of(2026, 2, 14)));
    }

    @Test
    void UserDAOFindById_WhenIdOfFirstUser_ThenGetFirstUser() {
        List<User> users = userDAO.findAll();
        User expectedUser = users.get(0);
        int userId = expectedUser.getId();

        User user = userDAO.findById(userId);

        Assertions.assertEquals(expectedUser, user);
    }

    @Test
    void UserDAOFindAll_WhenCountUsersEquals3_ThenSizeOfListEquals3() {
        List<User> users = userDAO.findAll();

        Assertions.assertEquals(3, users.size());
    }

    @Test
    void UserDAOSave_WhenCreateNewUser_ThenSizeOfListIncrement() {
        User expectedUser = new User("Пользователь", "polzovatel@example.com", 20,
                LocalDate.of(2026, 02, 22));

        userDAO.save(expectedUser);

        Assertions.assertEquals(4, userDAO.findAll().size());
    }

    @Test
    void UserDAOUpdate_WhenUpdateNameOfFirstUser_ThenNameOfUserWillBeChanged() {
        List<User> users = userDAO.findAll();
        User expectedUser = users.get(0);
        int userId = expectedUser.getId();
        String testName = "Тестовое имя";
        expectedUser.setName(testName);

        userDAO.update(expectedUser);
        User updatedUser = userDAO.findById(userId);

        Assertions.assertEquals(expectedUser, updatedUser);
    }

    @Test
    void UserDAODelete_WhenDeleteLastUser_ThenSizeOfListDecrement() {
        List<User> users = userDAO.findAll();
        User user = users.get(users.size() - 1);

        userDAO.delete(user);

        Assertions.assertEquals(2, userDAO.findAll().size());
    }
}