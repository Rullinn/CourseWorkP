package org.example.courseworkp;
import org.example.courseworkp.Tour;
import org.example.courseworkp.TourPackage;
import org.example.courseworkp.TourDB;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TourGUI extends Application {
    private final TourPackage tourPackage = new TourPackage();
    private final TourDB tourDB = new TourDB();
    private final ListView<String> tourListView = new ListView<>();
    private final ComboBox<String> typeFilter = new ComboBox<>();
    private final ComboBox<String> sortOptions = new ComboBox<>();
    private final TextField nameField = new TextField();
    private final TextField destinationField = new TextField();
    private final TextField priceField = new TextField();
    private final TextField daysField = new TextField();
    private final DatePicker startDatePicker = new DatePicker();
    private final ComboBox<String> typeCombo = new ComboBox<>();
    private final ComboBox<String> transportCombo = new ComboBox<>();
    private final ComboBox<String> foodCombo = new ComboBox<>();
    private final Button addButton = new Button("Add Tour");
    private final Button updateButton = new Button("Update Tour");
    private final Button deleteButton = new Button("Delete Tour");

    @Override
    public void start(Stage primaryStage) {
        try {
            // Загрузка данных из БД
            loadToursFromDB();

            // Настройка интерфейса
            setupUI(primaryStage);

        } catch (SQLException e) {
            showAlert("Database Error", "Could not load tours from database: " + e.getMessage());
        }
    }

    private void loadToursFromDB() throws SQLException {
        List<Tour> tours = tourDB.getAllTours();
        for (Tour tour : tours) {
            tourPackage.addTour(tour);
        }
        updateTourList();
    }

    private void setupUI(Stage stage) {
        // Настройка элементов управления
        setupComboBoxes();

        // Создание панели фильтрации и сортировки
        HBox filterSortPanel = new HBox(10);
        filterSortPanel.getChildren().addAll(
                new Label("Filter by type:"), typeFilter,
                new Label("Sort by:"), sortOptions
        );

        // Создание формы для ввода данных
        GridPane inputForm = new GridPane();
        inputForm.setHgap(10);
        inputForm.setVgap(10);
        inputForm.setPadding(new Insets(10));

        inputForm.add(new Label("Name:"), 0, 0);
        inputForm.add(nameField, 1, 0);
        inputForm.add(new Label("Destination:"), 0, 1);
        inputForm.add(destinationField, 1, 1);
        inputForm.add(new Label("Type:"), 0, 2);
        inputForm.add(typeCombo, 1, 2);
        inputForm.add(new Label("Transport:"), 0, 3);
        inputForm.add(transportCombo, 1, 3);
        inputForm.add(new Label("Food:"), 0, 4);
        inputForm.add(foodCombo, 1, 4);
        inputForm.add(new Label("Price:"), 0, 5);
        inputForm.add(priceField, 1, 5);
        inputForm.add(new Label("Days:"), 0, 6);
        inputForm.add(daysField, 1, 6);
        inputForm.add(new Label("Start Date:"), 0, 7);
        inputForm.add(startDatePicker, 1, 7);

        // Создание панели кнопок
        HBox buttonPanel = new HBox(10);
        buttonPanel.getChildren().addAll(addButton, updateButton, deleteButton);

        // Настройка обработчиков событий
        setupEventHandlers();

        // Создание основного макета
        BorderPane root = new BorderPane();
        root.setTop(filterSortPanel);
        root.setCenter(tourListView);
        root.setLeft(inputForm);
        root.setBottom(buttonPanel);

        // Настройка сцены и отображение
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Tour Management System");
        stage.setScene(scene);
        stage.show();
    }

    private void setupComboBoxes() {
        typeFilter.setItems(FXCollections.observableArrayList(
                "All", "vacation", "excursion", "treatment", "shopping", "cruise"));
        typeFilter.setValue("All");

        sortOptions.setItems(FXCollections.observableArrayList(
                "Price (low to high)", "Price (high to low)",
                "Days (short to long)", "Days (long to short)",
                "Date (earliest first)", "Date (latest first)"));
        sortOptions.setValue("Price (low to high)");

        typeCombo.setItems(FXCollections.observableArrayList(
                "vacation", "excursion", "treatment", "shopping", "cruise"));

        transportCombo.setItems(FXCollections.observableArrayList(
                "plane", "train", "bus", "ship"));

        foodCombo.setItems(FXCollections.observableArrayList(
                "none", "breakfast", "half_board", "full_board", "all_inclusive"));
    }

    private void setupEventHandlers() {
        // Фильтрация и сортировка
        typeFilter.setOnAction(e -> updateTourList());
        sortOptions.setOnAction(e -> updateTourList());

        // Добавление тура
        addButton.setOnAction(e -> {
            try {
                Tour tour = createTourFromInput();
                tourDB.saveTour(tour);
                tourPackage.addTour(tour);
                updateTourList();
                clearInputFields();
            } catch (SQLException ex) {
                showAlert("Database Error", "Could not save tour: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Please enter valid numbers for price and days");
            }
        });

        // Обновление тура
        updateButton.setOnAction(e -> {
            String selected = tourListView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Selection Error", "Please select a tour to update");
                return;
            }

            try {
                int id = extractIdFromListViewItem(selected);
                Tour tour = tourPackage.findTourById(id);
                if (tour != null) {
                    updateTourFromInput(tour);
                    tourDB.updateTour(tour);
                    updateTourList();
                    clearInputFields();
                }
            } catch (SQLException ex) {
                showAlert("Database Error", "Could not update tour: " + ex.getMessage());
            } catch (NumberFormatException ex) {
                showAlert("Input Error", "Please enter valid numbers for price and days");
            }
        });

        // Удаление тура
        deleteButton.setOnAction(e -> {
            String selected = tourListView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Selection Error", "Please select a tour to delete");
                return;
            }

            try {
                int id = extractIdFromListViewItem(selected);
                if (tourDB.deleteTour(id)) {
                    tourPackage.removeTour(id);
                    updateTourList();
                    clearInputFields();
                }
            } catch (SQLException ex) {
                showAlert("Database Error", "Could not delete tour: " + ex.getMessage());
            }
        });

        // Выбор тура для редактирования
        tourListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int id = extractIdFromListViewItem(newVal);
                Tour tour = tourPackage.findTourById(id);
                if (tour != null) {
                    fillInputFields(tour);
                }
            }
        });
    }

    private void updateTourList() {
        List<Tour> tours;

        // Фильтрация
        String type = typeFilter.getValue();
        if ("All".equals(type)) {
            tours = tourPackage.getAllTours();
        } else {
            tours = tourPackage.filterByType(type);
        }

        // Сортировка
        String sortOption = sortOptions.getValue();
        if (sortOption != null) {
            switch (sortOption) {
                case "Price (low to high)":
                    tours = tourPackage.sortByPrice(true);
                    break;
                case "Price (high to low)":
                    tours = tourPackage.sortByPrice(false);
                    break;
                case "Days (short to long)":
                    tours = tourPackage.sortByDays(true);
                    break;
                case "Days (long to short)":
                    tours = tourPackage.sortByDays(false);
                    break;
                case "Date (earliest first)":
                    tours = tourPackage.sortByStartDate(true);
                    break;
                case "Date (latest first)":
                    tours = tourPackage.sortByStartDate(false);
                    break;
            }
        }

        // Обновление списка
        tourListView.getItems().clear();
        for (Tour tour : tours) {
            tourListView.getItems().add(tour.getId() + ": " + tour.toString());
        }
    }

    private Tour createTourFromInput() throws NumberFormatException {
        String name = nameField.getText();
        String destination = destinationField.getText();
        String type = typeCombo.getValue();
        String transport = transportCombo.getValue();
        String food = foodCombo.getValue();
        double price = Double.parseDouble(priceField.getText());
        int days = Integer.parseInt(daysField.getText());
        LocalDate startDate = startDatePicker.getValue();

        return new Tour(0, name, type, transport, food, days, price, startDate, destination);
    }

    private void updateTourFromInput(Tour tour) throws NumberFormatException {
        tour.setName(nameField.getText());
        tour.setDestination(destinationField.getText());
        tour.setType(typeCombo.getValue());
        tour.setTransport(transportCombo.getValue());
        tour.setFood(foodCombo.getValue());
        tour.setPrice(Double.parseDouble(priceField.getText()));
        tour.setDays(Integer.parseInt(daysField.getText()));
        tour.setStartDate(startDatePicker.getValue());
    }

    private void fillInputFields(Tour tour) {
        nameField.setText(tour.getName());
        destinationField.setText(tour.getDestination());
        typeCombo.setValue(tour.getType());
        transportCombo.setValue(tour.getTransport());
        foodCombo.setValue(tour.getFood());
        priceField.setText(String.valueOf(tour.getPrice()));
        daysField.setText(String.valueOf(tour.getDays()));
        startDatePicker.setValue(tour.getStartDate());
    }

    private void clearInputFields() {
        nameField.clear();
        destinationField.clear();
        typeCombo.setValue(null);
        transportCombo.setValue(null);
        foodCombo.setValue(null);
        priceField.clear();
        daysField.clear();
        startDatePicker.setValue(null);
    }

    private int extractIdFromListViewItem(String item) {
        String idStr = item.substring(0, item.indexOf(':'));
        return Integer.parseInt(idStr);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}