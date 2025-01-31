module com {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com to javafx.fxml;
    opens com.view;
    
    exports com;
    exports com.view to javafx.fxml;
}
