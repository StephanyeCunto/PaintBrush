module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com to javafx.fxml;
    opens com.controller;
    
    exports com;
    exports com.controller to javafx.fxml;
}
