module com.example.imageeditor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.imageeditor to javafx.fxml;
    exports com.example.imageeditor;
}