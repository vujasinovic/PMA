package com.example.transportivo.db.offers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.transportivo.model.Offer.Fields.capacity;
import static com.example.transportivo.model.Offer.Fields.comment;
import static com.example.transportivo.model.Offer.Fields.dateTimeArrival;
import static com.example.transportivo.model.Offer.Fields.dateTimeDeparture;
import static com.example.transportivo.model.Offer.Fields.description;
import static com.example.transportivo.model.Offer.Fields.latitude;
import static com.example.transportivo.model.Offer.Fields.locationFrom;
import static com.example.transportivo.model.Offer.Fields.locationTo;
import static com.example.transportivo.model.Offer.Fields.longitude;
import static com.example.transportivo.model.Offer.Fields.offerStatus;
import static com.example.transportivo.model.Offer.Fields.price;
import static com.example.transportivo.model.Offer.Fields.rating;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OffersTableHelper {
    public static final String TABLE_NAME = "Offers";
    private static final String TYPE_TEXT = " TEXT, ";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + locationFrom + TYPE_TEXT
            + locationTo + TYPE_TEXT
            + offerStatus + TYPE_TEXT
            + description + TYPE_TEXT
            + price + TYPE_TEXT
            + capacity + TYPE_TEXT
            + dateTimeArrival + " DATETIME, "
            + latitude + TYPE_TEXT
            + longitude + TYPE_TEXT
            + comment + TYPE_TEXT
            + rating + TYPE_TEXT
            + dateTimeDeparture + " DATETIME);";
}
