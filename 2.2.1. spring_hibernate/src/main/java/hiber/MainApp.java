package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        User user1 = new User("User1", "Lastname1", "user1@mail.ru", new Car("Lada", 111111));
        userService.add(user1);
        User user2 = new User("User2", "Lastname2", "user2@mail.ru", new Car("Tesla", 222222));
        userService.add(user2);
        User user3 = new User("User3", "Lastname3", "user3@mail.ru", new Car("Toyota", 333333));
        userService.add(user3);
        User user4 = new User("User4", "Lastname4", "user4@mail.ru", new Car("KIA", 444444));
        userService.add(user4);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println("His car = " + user.getHisCar().toString());
            System.out.println();
        }

//        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        Car car = session.get(Car.class, Long.valueOf(3));
//        User user = session.get(User.class, Long.valueOf(3));
//        user.getHisCar().setSeries(123098);
//        session.getTransaction().commit();
//
//        System.out.println((userService.byCar("Toyota", 123098).toString()));

        context.close();
    }
}