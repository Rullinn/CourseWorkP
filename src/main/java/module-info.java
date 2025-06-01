module org.example.courseworkp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.zaxxer.hikari;
    requires java.sql;


    opens org.example.courseworkp to javafx.fxml;
    exports org.example.courseworkp;
}