package com.example.transportivo.model;

import java.time.LocalDateTime;

public class Offer {
    private String locationFrom;
    private String locationTo;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private OfferStatus offerStatus;
}
