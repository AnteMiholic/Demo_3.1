module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    opens com.example.demo2 to javafx.fxml;
    opens main to javafx.fxml;
    exports main;
    exports com.example.demo2;
    exports hr.controllers.controllers;
    opens hr.controllers.controllers;
}