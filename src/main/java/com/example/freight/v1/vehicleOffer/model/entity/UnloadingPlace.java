package com.example.freight.v1.vehicleOffer.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "unloading_place")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnloadingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unloading_place_id_seq")
    @SequenceGenerator(name = "unloading_place_id_seq", sequenceName = "unloading_place_id_seq", allocationSize = 1)
    private Long unloadingId;

    @Column(name = "unloading_country")
    private String unloadingCountry;

    @Column(name = "unloading_city")
    private String unloadingCity;

    @Column(name = "unloading_postal_code")
    private String unloadingPostalCode;

    @Column(name = "unloading_start_date_and_time")
    private String unloadingStartDateAndTime;

    @Column(name = "unloading_end_date_and_time")
    private String unloadingEndDateAndTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offerId")
    @JsonIgnore
    private Offer offer;
}
