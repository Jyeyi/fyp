package com.example.fyp.model;

public class TicketModel {
    private String ticketID;
    private String busPlateNumber;
    private String toLocation;
    private String fromLocation;
    private String departureTime;
    private String arrivedTime;
    private String companyName;
    private String ticketPrice;
    private Boolean adultTicket;

    public TicketModel(String ticketID, String busPlateNumber, String toLocation, String fromLocation, String departureTime, String arrivedTime, String companyName, String ticketPrice, Boolean adultTicket) {
        this.ticketID = ticketID;
        this.busPlateNumber = busPlateNumber;
        this.toLocation = toLocation;
        this.fromLocation = fromLocation;
        this.departureTime = departureTime;
        this.arrivedTime = arrivedTime;
        this.companyName = companyName;
        this.ticketPrice = ticketPrice;
        this.adultTicket = adultTicket;
    }

    public TicketModel() {
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getBusPlateNumber() {
        return busPlateNumber;
    }

    public void setBusPlateNumber(String busPlateNumber) {
        this.busPlateNumber = busPlateNumber;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Boolean getAdultTicket() {
        return adultTicket;
    }

    public void setAdultTicket(Boolean adultTicket) {
        this.adultTicket = adultTicket;
    }
}