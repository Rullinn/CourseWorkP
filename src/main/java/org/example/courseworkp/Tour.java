package org.example.courseworkp;

import java.time.LocalDate;

public class Tour {
    private int id;
    private String name;
    private String type; // vacation, excursion, treatment, shopping, cruise
    private String transport; // plane, train, bus, ship
    private String food; // none, breakfast, half_board, full_board, all_inclusive
    private int days;
    private double price;
    private LocalDate startDate;
    private String destination;

    public Tour(int id, String name, String type, String transport, String food,
                int days, double price, LocalDate startDate, String destination) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.transport = transport;
        this.food = food;
        this.days = days;
        this.price = price;
        this.startDate = startDate;
        this.destination = destination;
    }

    // Геттери і сеттери
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getTransport() { return transport; }
    public void setTransport(String transport) { this.transport = transport; }
    public String getFood() { return food; }
    public void setFood(String food) { this.food = food; }
    public int getDays() { return days; }
    public void setDays(int days) { this.days = days; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    @Override
    public String toString() {
        return name + " (" + destination + ") - " + type + ", " + days + " days, " + price + " USD";
    }
}
