package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class MainApp {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        // ПОЧЕМУ В МЕТОДАХ СЕРВИСА СЕССИЯ ОТКРЫВАЕТСЯ, НО НЕ ЗАКРЫВАЕТСЯ?
        User user1 = new User("User1", "Lastname1", "user1@mail.ru");
        user1.setHisCar(new Car("Lada", 111111));
        userService.add(user1);
        User user2 = new User("User2", "Lastname2", "user2@mail.ru");
        user2.setHisCar(new Car("Tesla", 222222));
        userService.add(user2);
        User user3 = new User("User3", "Lastname3", "user3@mail.ru");
        user3.setHisCar(new Car("Toyota", 333333));
        userService.add(user3);
        User user4 = new User("User4", "Lastname4", "user4@mail.ru");
        user4.setHisCar(new Car("KIA", 444444));
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

        System.out.println((userService.byCar("Tesla", 222222).toString()));

        context.close();
    }
}