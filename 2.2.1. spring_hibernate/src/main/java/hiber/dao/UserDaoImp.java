package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
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
    public User getUser(String model, int series) {

        String hqlGetUser = "from User u LEFT OUTER JOIN FETCH u.car " +
                "WHERE u.car.series = :series and u.car.model = :model";

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(hqlGetUser, User.class)
                    .setParameter("series", series)
                    .setParameter("model", model).getSingleResult();
        }

    }

}
