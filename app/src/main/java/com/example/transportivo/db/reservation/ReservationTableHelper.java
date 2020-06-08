package com.example.transportivo.db.reservation;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.transportivo.model.Reservation.Fields.comment;
import static com.example.transportivo.model.Reservation.Fields.rating;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationTableHelper {
    public static final String TABLE_NAME = "Reservation";
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + comment + " TEXT,"
            + rating + " NUMBER,"
            + "offer_id INTEGER,"
            + "FOREIGN KEY (offer_id) REFERENCES Offers (id));";
}
