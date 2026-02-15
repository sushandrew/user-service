package com.sushkov.infrastructure;

import com.sushkov.domain.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
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
}
