package me.codetalk.demo;

import me.codetalk.demo.model.User;
import me.codetalk.demo.service.UserService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by guobiao.xu on 2018/7/6.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "me.codetalk.demo.service"
})
@MapperScan(basePackages = {
        "me.codetalk.demo.mapper"
})
public class DemoMain implements CommandLineRunner {

    @Autowired
    private UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DemoMain.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = createUser("Ted", 30),
                user2 = createUser("Guru", 42),
                user3 = createUser("Kolyn", 26);

        new Thread(new UserCreateTask(user1)).start();
//        new Thread(new UserCreateTask(user2)).start();
//        new Thread(new UserCreateTask(user3)).start();
    }

    private User createUser(String name, int age) {
        User user = new User();
        user.setAge(age);
        user.setName(name);

        return user;
    }

    private class UserCreateTask implements Runnable {

        private User user;

        UserCreateTask(User user) {
            this.user = user;
        }

        @Override
        public void run() {
            userService.createUser(user);
        }

    }

}

