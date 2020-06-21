package com.example.transportivo.globals;

public class OffersCallableFunctions implements FirebaseCallable {
    @Override
    public String getAll() {
        return "getOffers";
    }

    @Override
    public String getOne() {
        return "getOffer";
    }

    @Override
    public String create() {
        return "createOffer";
    }

    @Override
    public String update() {
        return "updateOffer";
    }
}
