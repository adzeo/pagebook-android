package com.example.pagebook.models.user;

public class UserBuilder {
    private static User instance;

    private UserBuilder() {
        //private constructor
    }

    public static User getInstance() {
        if(instance == null) {
            synchronized (UserBuilder.class) {
                if(instance == null) {
                    instance = new User();
                }
            }
        }
        return instance;
    }

    public static void destroy() {
        instance = null;
    }
}
