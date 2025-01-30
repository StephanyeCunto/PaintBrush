package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) throws IOException {
        stage.setTitle("Paint Brush");
        scene = new Scene(loadFXML("paintbrush"));
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

   public static void main(String[] args) {
        launch();
    }
}