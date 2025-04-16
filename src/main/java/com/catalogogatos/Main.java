package com.catalogogatos;

import com.catalogogatos.controller.MainController;
import com.catalogogatos.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        MainController controller = new MainController();
        MainView mainView = new MainView(controller);
        
        Scene scene = new Scene(mainView.getRoot(), 800, 600);
        
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        
        primaryStage.setTitle("Catálogo de Gatos UFPB");
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
