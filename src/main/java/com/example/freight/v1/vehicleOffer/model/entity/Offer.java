package com.example.freight.v1.vehicleOffer.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "offer")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name="transeu_offer_id")
    private String transeuOfferId;

    @Column(name = "publish_date_time")
    private String publishDateTime;

    @JoinColumn(name = "loading_place", referencedColumnName = "loading_id")
    @OneToOne(cascade = CascadeType.ALL)
    private LoadingPlace loadingPlace;

    @JoinColumn(name = "unloading_place", referencedColumnName = "unloading_id")
    @OneToOne(cascade = CascadeType.ALL)
    private UnloadingPlace unloadingPlace;

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
