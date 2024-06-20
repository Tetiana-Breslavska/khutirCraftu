package com.gmail.ypon2003.marketplacebackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

/**
 * @author uriiponomarenko 27.05.2024
 * *
 *  * This class represents an advertisement entity in the database.
 *
 */
@Entity
@Data
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(name = "product_name")
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "product_description")
    @Size(min = 1, max = 500)
    private String description;

    @Column(name = "product_price")
    @DecimalMin("0.01")
    private double price;

    @Column(name = "product_flag")
    private boolean flag;

    @Column(name = "product_created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Past
    private Date createdAt;

    @Column(name = "product_info_seller")
    private String infoSeller;

}
