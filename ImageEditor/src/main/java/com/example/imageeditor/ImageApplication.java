package com.example.imageeditor;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * An image editing program where image files can be uploaded to view and be
 * edited by adjusting properties such as hue, brightness and saturation.
 *
 * @author Stephen Power
 */
public class ImageApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stage.setTitle("Image Editor");
            stage.setResizable(false);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}