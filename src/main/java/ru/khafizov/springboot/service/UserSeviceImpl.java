package ru.khafizov.springboot.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.khafizov.springboot.dao.UserDao;
import ru.khafizov.springboot.model.User;

import java.util.List;


@Service
public class UserSeviceImpl implements UserService {


    public final UserDao userDao;

    public UserSeviceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    @Transactional
    public void update(int id, User user) {
        userDao.update(user.getId(), user);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
}
