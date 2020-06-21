package com.example.transportivo.globals;

public class ReservationCallableFunctions implements FirebaseCallable {
    @Override
    public String getAll() {
        return "getReservations";
    }

    @Override
    public String getOne() {
        return "getReservation";
    }

    @Override
    public String create() {
        return "createReservation";
    }

    @Override
    public String update() {
        return "updateReservation";
    }
}
