package io.codelex.flightplanner.Objects;

import javax.persistence.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "FLIGHT")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "airport_from")
    private Airport from;
    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(name = "airport_to")
    private Airport to;
    @NotBlank
    @NotNull
    @Column(name = "carrier")
    private String carrier;
    @NotNull
    @Column(name = "departureTime")
    private LocalDateTime departureTime;
    @NotNull
    @Column(name = "arrivalTime")
    private LocalDateTime arrivalTime;

    public Flight() {
    }

    public Flight(Integer id, Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        this.departureTime = LocalDateTime.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.arrivalTime = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Airport getFrom() {
        return from;
    }

    public void setFrom(Airport from) {
        this.from = from;
    }

    public Airport getTo() {
        return to;
    }

    public void setTo(Airport to) {
        this.to = to;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDepartureTime() {
        return departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = LocalDateTime.parse(departureTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getArrivalTime() {
        return arrivalTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = LocalDateTime.parse(arrivalTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", from=" + from +
                ", to=" + to +
                ", carrier='" + carrier + '\'' +
                ", departureTime='" + departureTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + '\'' +
                ", arrivalTime='" + arrivalTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + '\'' +
                '}';
    }

}