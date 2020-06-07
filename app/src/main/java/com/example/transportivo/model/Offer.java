package com.example.transportivo.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Offer implements Serializable {
    private String locationFrom;
    private String locationTo;
    private String description;
    private String dateTimeDeparture;
    private String dateTimeArrival;
    private OfferStatus offerStatus;
}
