package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User byCar(String model, int series) {
        try {
            return sessionFactory.getCurrentSession().createQuery("from User where " +
                            "hisCar.model = ?0 and hisCar.series = ?1", User.class)
                    .setParameter(0, model)
                    .setParameter(1, series)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("В базе данных нет пользователя для данного автомобиля, либо автомобиля не существует.");
        }
        return null;
    }
}

