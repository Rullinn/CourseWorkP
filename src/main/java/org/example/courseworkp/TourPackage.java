package org.example.courseworkp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TourPackage {
    private final List<Tour> tours;

    public TourPackage() {
        this.tours = new ArrayList<>();
    }

    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public boolean removeTour(int id) {
        return tours.removeIf(t -> t.getId() == id);
    }

    public List<Tour> filterByType(String type) {
        return tours.stream()
                .filter(t -> t.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public List<Tour> sortByPrice(boolean ascending) {
        return tours.stream()
                .sorted(ascending ?
                        Comparator.comparingDouble(Tour::getPrice) :
                        Comparator.comparingDouble(Tour::getPrice).reversed())
                .collect(Collectors.toList());
    }

    public List<Tour> sortByDays(boolean ascending) {
        return tours.stream()
                .sorted(ascending ?
                        Comparator.comparingInt(Tour::getDays) :
                        Comparator.comparingInt(Tour::getDays).reversed())
                .collect(Collectors.toList());
    }

    public List<Tour> sortByStartDate(boolean ascending) {
        return tours.stream()
                .sorted(ascending ?
                        Comparator.comparing(Tour::getStartDate) :
                        Comparator.comparing(Tour::getStartDate).reversed())
                .collect(Collectors.toList());
    }

    public List<Tour> getAllTours() {
        return new ArrayList<>(tours);
    }

    public Tour findTourById(int id) {
        return tours.stream()
                .filter(t -> t.getId() == id)
                .findFirst()
                .orElse(null);
    }
}