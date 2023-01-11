package io.codelex.flightplanner.Objects;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FlightsRequest {

    private String from;
    private String to;
    private LocalDateTime departureDate;

    public FlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = LocalDateTime.parse(departureDate + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    @Override
    public String toString() {
        return "FlightsRequest{" +
                "from=" + from +
                ", to=" + to +
                ", departureDate='" + departureDate + '\'' +
                '}';
    }
}

