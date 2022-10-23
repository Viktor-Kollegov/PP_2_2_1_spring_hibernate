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
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User byCar(String model, int series) {
        return sessionFactory.getCurrentSession().createQuery("from User where " +
                        "hisCar.model = :model and hisCar.series = :series", User.class)
                .setParameter("model", model)
                .setParameter("series", series)
                .uniqueResult();
    }
}
/*
    String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? "
            + "ORDER BY e.salary";

    Объект Query вызывает метод setXxx для поддержки стиля программирования цепочки методов.
        Department dept = new Department();
		dept.setId(1);
                query.setFloat(0, 3000)
                .setParameter(1, "%A%",StringType.INSTANCE)
                .setEntity(2, dept);*/
