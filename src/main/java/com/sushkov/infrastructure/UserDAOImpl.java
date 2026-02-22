package com.sushkov.infrastructure;

import com.sushkov.domain.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UserDAOImpl implements UserDAO{
    private static SessionFactory sessionFactory;

    public UserDAOImpl() {
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    public User findById(int id) {
        Session session = sessionFactory.openSession();

        return session.get(User.class, id);
    }

    public List<User> findAll() {
        Session session = sessionFactory.openSession();

        return session.createQuery("FROM User", User.class).list();
    }

    public void save(User user) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

    }

    public void update(User user) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }

    }

    public void delete(User user) {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.remove(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }

    public void deleteAll() {
        Session session = sessionFactory.openSession();

        Transaction transaction = session.getTransaction();
        try {
            transaction.begin();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null)
                transaction.rollback();
            System.out.println(e.getMessage());
        } finally {
            session.close();
        }
    }
}
