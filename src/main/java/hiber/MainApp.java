package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.NoResultException;
import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);
        User ilya = new User("Ilya", "Shaporto", "ZiK201091@ya.ru");
        User kristina = new User("Kristina", "Duleba", "duleba@eml.ru");
        User alla = new User("Alla", "Zhigulina", "alla2@yandex.ru");
        User roma = new User("Roma", "Ivanov", "romaIvanov@mail.io");

        Car zhiguli = new Car("Zhiguli", 252);
        Car bmw = new Car("BMW", 795);
        Car mercedes = new Car("Mercedes", 547);
        Car mitsubishi = new Car("Mitsubishi", 21014);

        userService.add(ilya.setCar(zhiguli).setUser(ilya));
        userService.add(kristina.setCar(bmw).setUser(kristina));
        userService.add(alla.setCar(mercedes).setUser(alla));
        userService.add(roma.setCar(mitsubishi).setUser(roma));

        // пользователи с машинами
        for (User user : userService.listUsers()) {
            System.out.println(user + " " + user.getCar());
        }

        // достать юзера, владеющего машиной по ее модели и серии
        System.out.println(userService.getUserByCar("BMW", 325));

        // нет такого юзера с такой машиной
        try {
            User notFoundUser = userService.getUserByCar("GAZ", 4211);
        } catch (NoResultException e) {
            System.out.println("User not found");
        }


        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println("Id = " + user.getId());
            System.out.println("First Name = " + user.getFirstName());
            System.out.println("Last Name = " + user.getLastName());
            System.out.println("Email = " + user.getEmail());
            System.out.println();
        }

        context.close();
    }
}
