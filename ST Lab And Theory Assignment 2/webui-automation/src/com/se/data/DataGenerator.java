package com.se.data;

import com.github.javafaker.Faker;
import com.se.models.User;

public class DataGenerator {

    public static User getSampleUser(){
        var user=new User();
        user.firstName = Faker.instance().name().firstName();
        user.lastName = Faker.instance().name().lastName();
        user.username = Faker.instance().name().username();
        user.password = Faker.instance().internet().password();
        user.confirmPassword = user.password;
        user.email = Faker.instance().internet().emailAddress();

        return user;
    }
}
