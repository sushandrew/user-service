package com.sushkov;

import com.sushkov.domain.User;
import com.sushkov.service.UserService;
import com.sushkov.service.UserServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static UserService userService;
    public static boolean isRunning = true;
    public static Scanner scanner;

    public static void main(String[] args) {
        userService = new UserServiceImpl();
        scanner = new Scanner(System.in);

        while (isRunning) {
            showMenu();
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    printUsers();
                    break;
                case "2":
                    findUser();
                    break;
                case "3":
                    createUser();
                    break;
                case "4":
                    updateUser();
                    break;
                case "5":
                    deleteUser();
                    break;
                case "6":
                    deleteAllUsers();
                    break;
                case "0":
                    isRunning = false;
                    break;
                default:
                    System.out.println("Введено недопустимое значение, повторите попытку!");
            }
        }
    }

    private static void showMenu() {
        System.out.println("""
                -----------------МЕНЮ-----------------
                Выберите действие:
                1) Вывести всех пользователей
                2) Найти пользователя по id
                3) Создание пользователя
                4) Редактирование пользователя по id
                5) Удаление пользователя по id
                6) Удаление всех пользователей
                0) Выход из программы""");
    }

    private static void printUsers() {
        System.out.println("------------ВСЕ ПОЛЬЗОВАТЕЛИ----------");

        List<User> users = userService.findAllUsers();
        users.forEach(user -> {
            System.out.println(user.toString());
        });
    }

    private static void findUser() {
        System.out.println("--------------ПОЛЬЗОВАТЕЛЬ------------");
        System.out.print("Введите id пользователя: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine());
            if (userId < 0) throw new NumberFormatException();

            User user = userService.findUser(userId);
            if (user == null) {
                System.out.println("Пользователь не найден!");
            } else {
                System.out.println(user.toString());
            }
        } catch (NumberFormatException e) {
            System.out.println("Число было введено в неверном формате!");
        }
    }

    private static void createUser() {
        System.out.println("---------СОЗДАНИЕ ПОЛЬЗОВАТЕЛЯ--------");
        System.out.println("Введите данные пользователя (в формате - имя|email|возраст):");
        try {
            User newUser = scanUser();
            if (newUser == null)
                return;

            User user = new User(newUser.getName(), newUser.getEmail(), newUser.getAge(), LocalDate.now());

            userService.saveUser(user);
            System.out.println("Пользователь был успешно создан");
        } catch (NumberFormatException e) {
            System.out.println("Число было введено в неверном формате!");
        }
    }

    public static void updateUser() {
        System.out.println("------РЕДАКТИРОВАНИЕ ПОЛЬЗОВАТЕЛЯ-----");
        System.out.print("Введите id пользователя: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine());
            if (userId < 0) throw new NumberFormatException();

            User oldUser = userService.findUser(userId);
            if (oldUser == null) {
                System.out.println("Пользователь не найден!");
            } else {
                System.out.println("Введите новые данные пользователя (в формате - имя|email|возраст):");
                User newUser = scanUser();
                if (newUser == null)
                    return;

                User user = new User(oldUser.getId(), newUser.getName(),newUser.getEmail(), newUser.getAge(),
                        oldUser.getCreatedAt());

                userService.updateUser(user);
                System.out.println("Пользователь был успешно изменен");
            }
        } catch (NumberFormatException e) {
            System.out.println("Число было введено в неверном формате!");
        }
    }

    public static void deleteUser() {
        System.out.println("---------УДАЛЕНИЕ ПОЛЬЗОВАТЕЛЯ--------");
        System.out.print("Введите id пользователя: ");
        try {
            int userId = Integer.parseInt(scanner.nextLine());
            if (userId < 0) throw new NumberFormatException();

            userService.deleteUser(userId);
            System.out.println("Пользователь был успешно удален");
        } catch (NumberFormatException e) {
            System.out.println("Число было введено в неверном формате!");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteAllUsers() {
        System.out.println("------УДАЛЕНИЕ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ------");
        try {
            userService.deleteAllUsers();
            System.out.println("Все пользователи были успешно удалены!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static User scanUser() {
        String input = scanner.nextLine();
        String[] stringElements = input.split("\\|");
        if (stringElements.length != 3) {
            System.out.println("Неверное количество параметров!");
            return null;
        }

        String name = stringElements[0];
        String email = stringElements[1];
        int age = Integer.parseInt(stringElements[2]);

        return new User(name, email, age, LocalDate.now());
    }
}