package com.example.transportivo.globals;

public class NotificationTokenCallableFunctions implements FirebaseCallable {
    @Override
    public String getAll() {
        return "getNotificationTokens";
    }

    @Override
    public String getOne() {
        return "getNotificationToken";
    }

    @Override
    public String create() {
        return "createNotificationToken";
    }

    @Override
    public String update() {
        return "updateNotificationToken";
    }
}
