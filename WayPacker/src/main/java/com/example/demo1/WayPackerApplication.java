package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class WayPackerApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(WayPackerApplication.class.getResource("hello-view.fxml"));
        // Оптимальный размер окна под мега-версию приложения
        Scene scene = new Scene(fxmlLoader.load(), 560, 740);

        stage.setTitle("WayPacker — Smart Travel Planner");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
