package com.gmail.ypon2003.marketplacebackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * @author uriiponomarenko 27.05.2024
 */
@Entity
@Data
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long person_id;

    @Column(name = "person_name")
    @Size(min = 1, max = 50)
    private String name;

    @Column(name = "person_last_name")
    @Size(min = 1, max = 50)
    private String lastName;

    @Column(name = "person_email")
    //@NotEmpty
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$")
    private String email;

    @Column(name = "person_phone_number")
    @Pattern(regexp = "^\\+(\\d{1,14})?[\\d\\s-]{7,14}$")
    private String phoneNumber;

    @Column(name = "person_password")
    //@NotNull
    @Size(min = 6, max = 100)
    private String password;

    @Column(name = "person_role")
    private String role;

    @ManyToMany
    @JoinTable(name = "user_favorite_ads",
            joinColumns = @JoinColumn(name = "favorite_id"),
    inverseJoinColumns = @JoinColumn(name = "favorite_ad_id"))
    private List<Product> favorites = new ArrayList<>();

    public void addToFavorites(Product product) {
        if(!favorites.contains(product)) {
            favorites.add(product);
        }
    }

    public void removeFromFavorites(Product product) {
        favorites.remove(product);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;//TODO dev future
    }
}
