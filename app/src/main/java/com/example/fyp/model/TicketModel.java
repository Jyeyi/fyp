package com.example.fyp.model;

public class TicketModel {
    private String ticketID;
    private String busPlateNumber;
    private String toLocation;
    private String fromLocation;
    private String departureTime;
    private String departureDate;
    private String arrivedTime;
    private String arrivedDate;
    private String companyName;
    private String ticketPrice;
    private String stage;


    public TicketModel() {
    }

    public TicketModel(String ticketID, String busPlateNumber, String toLocation, String fromLocation, String departureTime, String departureDate, String arrivedTime, String arrivedDate, String companyName, String ticketPrice, String stage) {
        this.ticketID = ticketID;
        this.busPlateNumber = busPlateNumber;
        this.toLocation = toLocation;
        this.fromLocation = fromLocation;
        this.departureTime = departureTime;
        this.departureDate = departureDate;
        this.arrivedTime = arrivedTime;
        this.arrivedDate = arrivedDate;
        this.companyName = companyName;
        this.ticketPrice = ticketPrice;
        this.stage = stage;
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

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivedTime() {
        return arrivedTime;
    }

    public void setArrivedTime(String arrivedTime) {
        this.arrivedTime = arrivedTime;
    }

    public String getArrivedDate() {
        return arrivedDate;
    }

    public void setArrivedDate(String arrivedDate) {
        this.arrivedDate = arrivedDate;
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

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}