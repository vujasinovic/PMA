package com.example.transportivo.globals;

public class NotificationCallableFunctions implements FirebaseCallable {
    @Override
    public String getAll() {
        return "getNotifications";
    }

    @Override
    public String getOne() {
        return "getNotification";
    }

    @Override
    public String create() {
        return "createNotification";
    }

    @Override
    public String update() {
        return "updateNotification";
    }
}
