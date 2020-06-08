package com.example.transportivo.db.offers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.transportivo.model.Offer.Fields.capacity;
import static com.example.transportivo.model.Offer.Fields.dateTimeArrival;
import static com.example.transportivo.model.Offer.Fields.dateTimeDeparture;
import static com.example.transportivo.model.Offer.Fields.description;
import static com.example.transportivo.model.Offer.Fields.locationFrom;
import static com.example.transportivo.model.Offer.Fields.locationTo;
import static com.example.transportivo.model.Offer.Fields.offerStatus;
import static com.example.transportivo.model.Offer.Fields.price;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OffersTableHelper {
    public static final String TABLE_NAME = "Offers";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + locationFrom + " TEXT, "
            + locationTo + " TEXT, "
            + offerStatus + " TEXT, "
            + description + " TEXT, "
            + price + " TEXT, "
            + capacity + " TEXT, "
            + dateTimeArrival + " DATETIME, "
            + dateTimeDeparture + " DATETIME);";
}
