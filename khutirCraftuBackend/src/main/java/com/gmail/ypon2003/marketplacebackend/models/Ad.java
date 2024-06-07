package com.gmail.ypon2003.marketplacebackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * @author uriiponomarenko 27.05.2024
 */
@Entity
@Data
@Table(name = "ad")
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private Long ad_id;

    @Column(name = "ad_name")
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "ad_description")
    @Size(min = 1, max = 500)
    private String description;

    @Column(name = "ad_price")
    @DecimalMin("0.01")
    private double price;

    @Column(name = "ad_flag")
    private boolean flag;

    @Column(name = "ad_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Past
    private Date createdAt;

    @Column(name = "ad_info_seller")
    private String infoSeller;

}
