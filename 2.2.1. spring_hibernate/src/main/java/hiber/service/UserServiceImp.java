package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    private UserDao userDao;

    public UserServiceImp(UserDao userDao) { // СО SPRING 4.3 АННОТАЦИЯ @AUTOWIRED НЕОБЯЗАТЕЛЬНА НА ЕДИНСТВЕННОМ КОНСТРУКТОРЕ
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Transactional
    @Override
    public User byCar(String model, int series) {
        return userDao.byCar(model, series);
    }

}
