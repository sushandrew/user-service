package com.sushkov.infrastructure;

import com.sushkov.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private static String url;
    private static String username;
    private static String password;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                if (url != null)
                    configuration.setProperty("hibernate.connection.url", url);
                if (username != null)
                    configuration.setProperty("hibernate.connection.username", username);
                if (password != null)
                    configuration.setProperty("hibernate.connection.password", password);

                configuration.addAnnotatedClass(User.class);
                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Подключение к БД некорректно!");
                System.exit(0);
            }
        }

        return sessionFactory;
    }

    public static void setUrl(String url) {
        HibernateSessionFactory.url = url;
    }

    public static void setUsername(String username) {
        HibernateSessionFactory.username = username;
    }

    public static void setPassword(String password) {
        HibernateSessionFactory.password = password;
    }
}
