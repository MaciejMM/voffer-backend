package com.example.freight.v1.integrations.offer.entity;

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

@Table(name = "offer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "teleroute_offer_id")
    private String telerouteOfferId;

    @Column(name = "teleroute_external_id")
    private String telerouteExternalId;

    @Column(name = "timocon_offer_id")
    private String timoconOfferId;

    @Column(name = "transeu_offer_id")
    private String transeuOfferId;

    @Column(name = "publish_date_time")
    private String publishDateTime;

    @JoinColumn(name = "loading_place", referencedColumnName = "loading_id")
    @OneToOne(cascade = CascadeType.ALL)
    private LoadingPlace loadingPlace;

    @OneToMany(
            mappedBy = "offer",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonManagedReference
    private List<UnloadingPlace> unloadingPlace = new ArrayList<>();

    @Column(name = "description")
    private String description;

    @Column(name = "loading_type")
    private String loadingType;

    @Column(name = "loading_weight")
    private String loadingWeight;

    @Column(name = "loading_length")
    private String loadingLength;

    @Column(name = "loading_volume")
    private String loadingVolume;

    @Column(name = "loading_body_type")
    private String loadingBodyType;

    @Column
    private String goodsType;

    @Column
    private String publishSelected;


}
