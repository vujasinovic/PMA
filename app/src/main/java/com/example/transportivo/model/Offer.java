package com.example.transportivo.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements Serializable {
    private String locationFrom;
    private String locationTo;
    private LocalDateTime dateTimeDeparture;
    private LocalDateTime dateTimeArrival;
    private OfferStatus offerStatus;
}
