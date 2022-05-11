package hiber.dao;

import hiber.model.Car;
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
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(Car car) {
      TypedQuery<User> query = sessionFactory.getCurrentSession()
              .createQuery(
                      "select user from User user inner join user.car car where car.model=:carModel and car.series=:carSeries",
                      User.class
              )
              .setParameter("carModel", car.getModel())
              .setParameter("carSeries", car.getSeries());
      return query.getSingleResult();
   }
}
