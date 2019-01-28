package com.lpdm.mslocation.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="address",schema = "public")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "street_name")
    @NotNull(message = "le nom de rue ne peut être null")
    private String streetName;

    @Column(name = "street_number")
    @NotNull(message = "le numéro de rue ne peut être null")
    private String streetNumber;

    @Column
    private String complement;

    @Column
    @JsonIgnore
    private Integer cityId;

    @Transient
    @NotNull(message = "la ville ne peut être null")
    private City city;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", complement='" + complement + '\'' +
                ", cityId=" + cityId +
                ", city=" + city +
                '}';
    }
}
