package com.example.freight.v1.vehicleOffer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "loading_place")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoadingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loading_id")
    private Long loadingId;

    @Column(name = "loading_country")
    private String loadingCountry;

    @Column(name = "loading_city")
    private String loadingCity;

    @Column(name = "loading_postal_code")
    private String loadingPostalCode;

    @Column(name = "loading_start_date_and_time")
    private String loadingStartDateAndTime;

    @Column(name = "loading_end_date_and_time")
    private String loadingEndDateAndTime;

}
