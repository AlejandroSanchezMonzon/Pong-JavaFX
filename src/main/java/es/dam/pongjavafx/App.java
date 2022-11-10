package es.dam.pongjavafx;

import es.dam.pongjavafx.views.View;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = new View();
        Scene scene = new Scene(root, 1080, 720);
        stage.setTitle("Pong! - The Game.");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}