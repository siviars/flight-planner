package io.codelex.flightplanner.Objects;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "AIRPORT")
public class Airport {

    @NotBlank
    @NotNull
    @Column(name = "country")
    private String country;
    @NotBlank
    @NotNull
    @Column(name = "city")
    private String city;
    @Id
    @NotBlank
    @NotNull
    @Column(name = "airport")
    private String airport;

    public Airport() {
    }

    public Airport(String country, String city, String airport) {
        this.country = country;
        this.city = city;
        this.airport = airport;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "airport='" + airport + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
