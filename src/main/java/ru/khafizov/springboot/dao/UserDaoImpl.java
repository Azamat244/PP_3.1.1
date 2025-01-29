package ru.khafizov.springboot.dao;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import ru.khafizov.springboot.model.User;

import java.util.List;


@Repository //спец аннотация Spring которая помечает данный класс
// как класс который работает с бд (является компонентом DAO)
//является бином
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден для удаления");
        }
        entityManager.remove(user);
    }

    @Override
    public void update(int id, User user) {
        if (entityManager.find(User.class, id) == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден для обновления");
        }
        entityManager.merge(user);
    }

    @Override
    public List<User> getAll() {
        return entityManager.createQuery("from User ", User.class).getResultList();
    }

    @Override
    public User getById(int id) {
        User user = entityManager.find(User.class, id);
        if (user == null) {
            throw new EntityNotFoundException("Пользователь с id = " + id + " не найден для получения");
        }

        return user;
    }
}
