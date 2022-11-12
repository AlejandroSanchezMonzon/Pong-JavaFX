package es.dam.pongjavafx;

import es.dam.pongjavafx.views.View;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws FileNotFoundException {
        Parent root = new View();
        Scene scene = new Scene(root, 1080, 720);
        stage.setTitle("Pong! - The Game.");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        FileInputStream input = new FileInputStream(dir + File.separator + "images" + File.separator + "ping-pong.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        stage.getIcons().add(imageView.getImage());
    }

    public static void main(String[] args) {
        launch();
    }
}