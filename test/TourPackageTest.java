//package org.example;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//class TourPackageTest {
//    private TourPackage tourPackage;
//    private Tour tour1, tour2, tour3;
//
//    @BeforeEach
//    void setUp() {
//        tourPackage = new TourPackage();
//
//        tour1 = new Tour(1, "Beach Vacation", "vacation", "plane", "all_inclusive",
//                7, 1200.0, LocalDate.of(2023, 6, 15), "Maldives");
//
//        tour2 = new Tour(2, "European Tour", "excursion", "bus", "breakfast",
//                10, 1500.0, LocalDate.of(2023, 7, 1), "Europe");
//
//        tour3 = new Tour(3, "Spa Treatment", "treatment", "train", "half_board",
//                14, 2000.0, LocalDate.of(2023, 8, 10), "Czech Republic");
//
//        tourPackage.addTour(tour1);
//        tourPackage.addTour(tour2);
//        tourPackage.addTour(tour3);
//    }
//
//    @Test
//    void testAddTour() {
//        assertEquals(3, tourPackage.getAllTours().size());
//
//        Tour newTour = new Tour(4, "Shopping Tour", "shopping", "plane", "none",
//                5, 800.0, LocalDate.of(2023, 9, 1), "Paris");
//        tourPackage.addTour(newTour);
//
//        assertEquals(4, tourPackage.getAllTours().size());
//        assertTrue(tourPackage.getAllTours().contains(newTour));
//    }
//
//    @Test
//    void testRemoveTour() {
//        assertTrue(tourPackage.removeTour(2));
//        assertEquals(2, tourPackage.getAllTours().size());
//        assertFalse(tourPackage.getAllTours().contains(tour2));
//    }
//
//    @Test
//    void testFilterByType() {
//        List<Tour> vacationTours = tourPackage.filterByType("vacation");
//        assertEquals(1, vacationTours.size());
//        assertEquals(tour1, vacationTours.get(0));
//    }
//
//    @Test
//    void testSortByPriceAscending() {
//        List<Tour> sorted = tourPackage.sortByPrice(true);
//        assertEquals(tour1, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour3, sorted.get(2));
//    }
//
//    @Test
//    void testSortByPriceDescending() {
//        List<Tour> sorted = tourPackage.sortByPrice(false);
//        assertEquals(tour3, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour1, sorted.get(2));
//    }
//
//    @Test
//    void testSortByDaysAscending() {
//        List<Tour> sorted = tourPackage.sortByDays(true);
//        assertEquals(tour1, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour3, sorted.get(2));
//    }
//
//    @Test
//    void testSortByDaysDescending() {
//        List<Tour> sorted = tourPackage.sortByDays(false);
//        assertEquals(tour3, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour1, sorted.get(2));
//    }
//
//    @Test
//    void testSortByStartDateAscending() {
//        List<Tour> sorted = tourPackage.sortByStartDate(true);
//        assertEquals(tour1, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour3, sorted.get(2));
//    }
//
//    @Test
//    void testSortByStartDateDescending() {
//        List<Tour> sorted = tourPackage.sortByStartDate(false);
//        assertEquals(tour3, sorted.get(0));
//        assertEquals(tour2, sorted.get(1));
//        assertEquals(tour1, sorted.get(2));
//    }
//
//    @Test
//    void testFindTourById() {
//        assertEquals(tour1, tourPackage.findTourById(1));
//        assertEquals(tour2, tourPackage.findTourById(2));
//        assertNull(tourPackage.findTourById(999)); // Несуществующий ID
//    }
//}