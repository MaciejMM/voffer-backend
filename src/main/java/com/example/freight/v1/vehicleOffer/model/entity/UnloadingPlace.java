package com.example.freight.v1.vehicleOffer.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "unloading_place")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnloadingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "unloading_id")
    private Long unloadingId;

    @Column(name = "unloading_country")
    private String unloadingCountry;

    @Column(name = "unloading_city")
    private String unloadingCity;

    @Column(name = "unloading_postal_code")
    private String unloadingPostalCode;

    @Column(name = "unloading_date_and_time")
    private String unloadingDateAndTime;


}
