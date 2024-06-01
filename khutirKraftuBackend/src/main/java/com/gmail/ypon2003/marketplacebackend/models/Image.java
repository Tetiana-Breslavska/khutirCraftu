package com.gmail.ypon2003.marketplacebackend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author uriiponomarenko 28.05.2024
 */
@Entity
@Data
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
}
