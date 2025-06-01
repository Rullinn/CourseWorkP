package org.example;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.ListViewMatchers.hasItems;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@ExtendWith(ApplicationExtension.class)
class TourGUITest {
    private TourGUI tourGUI;

    @Start
    void start(Stage stage) {
        tourGUI = new TourGUI();
        tourGUI.start(stage);
    }

    @Test
    void testInitialState(FxRobot robot) {
        // Проверяем, что список туров отображается
        verifyThat("#tourListView", hasItems(0));

        // Проверяем начальные значения комбобоксов
        ComboBox<String> typeFilter = robot.lookup("#typeFilter").queryComboBox();
        assertEquals("All", typeFilter.getValue());

        ComboBox<String> sortOptions = robot.lookup("#sortOptions").queryComboBox();
        assertEquals("Price (low to high)", sortOptions.getValue());
    }

    @Test
    void testAddTour(FxRobot robot) {
        // Заполняем форму
        robot.clickOn("#nameField").write("Test Tour");
        robot.clickOn("#destinationField").write("Test Destination");
        robot.clickOn("#typeCombo").clickOn("vacation");
        robot.clickOn("#transportCombo").clickOn("plane");
        robot.clickOn("#foodCombo").clickOn("all_inclusive");
        robot.clickOn("#priceField").write("1000");
        robot.clickOn("#daysField").write("7");
        robot.clickOn("#startDatePicker").write("2023-06-15");

        // Нажимаем кнопку добавления
        robot.clickOn("#addButton");

        // Проверяем, что тур добавился в список
        ListView<String> listView = robot.lookup("#tourListView").queryListView();
        assertEquals(1, listView.getItems().size());
        assertTrue(listView.getItems().get(0).contains("Test Tour"));

        // Проверяем, что поля очистились после добавления
        TextFlow nameField = robot.lookup("#nameField").queryTextFlow();
        verifyThat("#nameField", hasText(""));
    }

    @Test
    void testUpdateTour(FxRobot robot) {
        // Сначала добавляем тур для редактирования
        testAddTour(robot);

        // Выбираем тур в списке
        robot.clickOn("#tourListView").clickOn("Test Tour");

        // Изменяем данные
        robot.clickOn("#nameField").eraseText(8).write("Updated Tour");
        robot.clickOn("#priceField").eraseText(4).write("1200");

        // Нажимаем кнопку обновления
        robot.clickOn("#updateButton");

        // Проверяем изменения в списке
        ListView<String> listView = robot.lookup("#tourListView").queryListView();
        assertTrue(listView.getItems().get(0).contains("Updated Tour"));
    }

    @Test
    void testDeleteTour(FxRobot robot) {
        // Сначала добавляем тур
        testAddTour(robot);

        // Выбираем тур в списке
        robot.clickOn("#tourListView").clickOn("Test Tour");

        // Нажимаем кнопку удаления
        robot.clickOn("#deleteButton");

        // Проверяем, что список пуст
        ListView<String> listView = robot.lookup("#tourListView").queryListView();
        assertEquals(0, listView.getItems().size());
    }

    @Test
    void testFilterAndSort(FxRobot robot) {
        // Добавляем несколько туров разных типов
        robot.clickOn("#nameField").write("Vacation Tour");
        robot.clickOn("#destinationField").write("Beach");
        robot.clickOn("#typeCombo").clickOn("vacation");
        robot.clickOn("#transportCombo").clickOn("plane");
        robot.clickOn("#foodCombo").clickOn("all_inclusive");
        robot.clickOn("#priceField").write("1500");
        robot.clickOn("#daysField").write("7");
        robot.clickOn("#startDatePicker").write("2023-06-01");
        robot.clickOn("#addButton");

        robot.clickOn("#nameField").write("Excursion Tour");
        robot.clickOn("#destinationField").write("City");
        robot.clickOn("#typeCombo").clickOn("excursion");
        robot.clickOn("#transportCombo").clickOn("bus");
        robot.clickOn("#foodCombo").clickOn("breakfast");
        robot.clickOn("#priceField").write("1000");
        robot.clickOn("#daysField").write("5");
        robot.clickOn("#startDatePicker").write("2023-07-01");
        robot.clickOn("#addButton");

        // Фильтруем по типу
        robot.clickOn("#typeFilter").clickOn("vacation");
        ListView<String> listView = robot.lookup("#tourListView").queryListView();
        assertEquals(1, listView.getItems().size());
        assertTrue(listView.getItems().get(0).contains("Vacation Tour"));

        // Возвращаем все туры
        robot.clickOn("#typeFilter").clickOn("All");
        assertEquals(2, listView.getItems().size());

        // Сортируем по цене (по убыванию)
        robot.clickOn("#sortOptions").clickOn("Price (high to low)");
        assertTrue(listView.getItems().get(0).contains("Vacation Tour"));
    }
}