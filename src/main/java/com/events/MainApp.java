package com.events;

import com.events.controller.EventController;
import com.events.view.EventView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        EventController controller = new EventController();
        EventView root = new EventView(controller);

        Scene scene = new Scene(root, 900, 600);
        
        primaryStage.setTitle("Event Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
