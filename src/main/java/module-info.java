module org.example.courseworkp {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.courseworkp to javafx.fxml;
    exports org.example.courseworkp;
}