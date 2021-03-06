package com.example.transportivo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@With
public class Offer implements Serializable {
    private String id;
    private String owner;
    private String client;
    private String locationFrom;
    private String locationTo;
    private String description;
    private String dateTimeDeparture;
    private String dateTimeArrival;
    private OfferStatus offerStatus;
    private String price;
    private String capacity;
    private Double latitude;
    private Double longitude;
    private float rating;
    private String comment;
}
