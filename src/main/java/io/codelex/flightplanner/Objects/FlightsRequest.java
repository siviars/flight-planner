package io.codelex.flightplanner.Objects;

public class FlightsRequest {

    private String from;
    private String to;
    private String departureDate;

    public FlightsRequest(String from, String to, String departureDate) {
        this.from = from;
        this.to = to;
        this.departureDate = departureDate;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
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

