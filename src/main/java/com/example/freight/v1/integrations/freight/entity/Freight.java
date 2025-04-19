package com.example.freight.v1.integrations.freight.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "freight")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Freight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "weight")
    private String weight;

    @Column(name = "length")
    private String length;

    @Column(name = "volume")
    private String volume;

    @Column(name = "description")
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loading_place_id", referencedColumnName = "id")
    private FreightLoadingPlace loadingPlace;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "unloading_place_id", referencedColumnName = "id")
    private FreightUnloadingPlace unloadingPlace;

    @OneToMany(
            mappedBy = "freight",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonManagedReference
    private List<FreightCategory> selectedCategories = new ArrayList<>();

    @OneToMany(
            mappedBy = "freight",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonManagedReference
    private List<FreightVehicle> selectedVehicles = new ArrayList<>();

    @Column(name = "is_full_truck")
    private Boolean isFullTruck;

    @Column(name = "transeu_offer_id")
    private String transeuOfferId;

    @Column(name = "create_date")
    private String createdBy;

    @Column(name = "update_date")
    private String updatedBy;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "publish_date_time")
    private String publishDateTime;
}
