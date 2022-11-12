package es.dam.pongjavafx.controllers;

import es.dam.pongjavafx.views.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ViewController {
    private final Rectangle bordeNorte;
    private final Rectangle bordeSur;
    private final Rectangle bordeEste;
    private final Rectangle bordeOeste;
    private final Rectangle player1;
    private final Rectangle player2;
    private final Circle ball;
    private Double moveX = randomX();
    private Double moveY = randomY();
    private Double movePlayer1 = 0.00;
    private Double movePlayer2 = 0.00;
    private int pointsP1 = 0;
    private int pointsP2 = 0;
    private final View view;
    Timeline animation;
    String dir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources";

    public ViewController(Rectangle bordeNorte,
                          Rectangle bordeSur,
                          Rectangle bordeEste,
                          Rectangle bordeOeste,
                          Rectangle player1,
                          Rectangle player2,
                          Circle ball,
                          View view) {
        this.bordeNorte = bordeNorte;
        this.bordeSur = bordeSur;
        this.bordeEste = bordeEste;
        this.bordeOeste = bordeOeste;
        this.player1 = player1;
        this.player2 = player2;
        this.ball = ball;
        this.view = view;
    }

    public void play() {
        DoubleProperty x = new SimpleDoubleProperty();
        DoubleProperty y = new SimpleDoubleProperty();
        DoubleProperty yPlayer1 = new SimpleDoubleProperty();
        DoubleProperty yPlayer2 = new SimpleDoubleProperty();

        ball.translateXProperty().bind(x);
        ball.translateYProperty().bind(y);

        player1.translateYProperty().bind(yPlayer1);
        player2.translateYProperty().bind(yPlayer2);

        animation = new Timeline(
                new KeyFrame(Duration.seconds(0.017), t -> {
                    moveBall(x, y);
                    movePlayers(yPlayer1, yPlayer2);
                    initPlayers(view.getScene());
                    checkGolpeo();
                    try {
                        checkWalls(x, y, yPlayer1, yPlayer2);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                })
        );

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private void moveBall(DoubleProperty x, DoubleProperty y) {
        x.setValue(x.getValue() + moveX);
        y.setValue(y.getValue() + moveY);
    }

    private void movePlayers(DoubleProperty yPlayer1, DoubleProperty yPlayer2) {
        yPlayer1.setValue(yPlayer1.getValue() + movePlayer1);
        yPlayer2.setValue(yPlayer2.getValue() + movePlayer2);
    }

    public void initPlayers(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q:
                    if (!player1.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
                        movePlayer1 = -4.00;
                    }
                    break;
                case A:
                    if (!player1.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
                        movePlayer1 = 4.00;
                    }
                    break;
                case P:
                    if (!player2.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
                        movePlayer2 = -4.00;
                    }
                    break;
                case L:
                    if (!player2.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
                        movePlayer2 = 4.00;
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case Q, A -> movePlayer1 = 0.0;
                case P, L -> movePlayer2 = 0.0;
            }
        });
    }

    private void checkGolpeo() {
        if (ball.getBoundsInParent().intersects(player2.getBoundsInParent())) {
            reproduceGolpeo();
            moveX = -4.0;
            moveY = randomY();
        } else if (ball.getBoundsInParent().intersects(player1.getBoundsInParent())) {
            reproduceGolpeo();
            moveX = 4.0;
            moveY = randomY();
        }
    }

    private void checkWalls(DoubleProperty x, DoubleProperty y, DoubleProperty yPlayer1, DoubleProperty yPlayer2) throws FileNotFoundException {
        if (ball.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
            moveY = 4.0;
        }

        if (ball.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
            moveY = -4.0;
        }

        if (ball.getBoundsInParent().intersects(bordeEste.getBoundsInParent())) {
            updateScore(1);
            resetPositions(x, y, yPlayer1, yPlayer2);
        }

        if (ball.getBoundsInParent().intersects(bordeOeste.getBoundsInParent())) {
            updateScore(2);
            resetPositions(x, y, yPlayer1, yPlayer2);
        }
    }

    private void updateScore(int winner) throws FileNotFoundException {
        if (winner == 1) {
            pointsP1++;
            view.scoreP1.setText("PLAYER 1: " + pointsP1);

            reproduceGoal();
            waitAnimation();
            checkPoints();
        } else if (winner == 2) {
            pointsP2++;
            view.scoreP2.setText("PLAYER 2: " + pointsP2);

            reproduceGoal();
            waitAnimation();
            checkPoints();
        }
    }

    private void reproduceGoal() {
        String archivoGoal = dir + File.separator + "audios" + File.separator + "score.mp3";
        File archivo = new File(archivoGoal);
        Media audio = new Media(archivo.toURI().toString());

        MediaPlayer player = new MediaPlayer(audio);

        animation.stop();
        player.play();
        animation.play();
    }

    private void reproduceGolpeo() {
        String archivoGolpeo = dir + File.separator + "audios" + File.separator + "golpeo.mp3";
        File archivo = new File(archivoGolpeo);
        Media audio = new Media(archivo.toURI().toString());

        MediaPlayer player = new MediaPlayer(audio);

        player.play();
    }

    private void waitAnimation() {
        animation.stop();
        animation.setDelay(Duration.millis(1000));

        animation.play();
    }

    private void checkPoints() throws FileNotFoundException {
        Alert alert;

        if (pointsP1 == 7) {
            alert = new Alert(Alert.AlertType.INFORMATION);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            FileInputStream input = new FileInputStream(dir + File.separator + "images" + File.separator + "win.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            stage.getIcons().add(imageView.getImage());

            alert.setTitle("VICTORIA");
            alert.setHeaderText("EL JUGADOR AZUL HA GANADO");
            alert.setContentText("Reinicie el juego para volver a jugar...");
            alert.show();

            view.gameOverView.setVisible(true);
            animation.stop();

        } else if (pointsP2 == 7) {
            alert = new Alert(Alert.AlertType.INFORMATION);

            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            FileInputStream input = new FileInputStream(dir + File.separator + "images" + File.separator + "win.png");
            Image image = new Image(input);
            ImageView imageView = new ImageView(image);
            stage.getIcons().add(imageView.getImage());

            alert.setTitle("VICTORIA");
            alert.setHeaderText("EL JUGADOR ROJO HA GANADO");
            alert.setContentText("Reinicie el juego para volver a jugar...");
            alert.show();

            view.gameOverView.setVisible(true);
            animation.stop();
        }
    }

    private void resetPositions(DoubleProperty x, DoubleProperty y, DoubleProperty yPlayer1, DoubleProperty yPlayer2) {
        x.setValue(0);
        y.setValue(0);
        yPlayer1.setValue(0);
        yPlayer2.setValue(0);

        this.ball.translateXProperty().bind(x);
        this.ball.translateYProperty().bind(y);

        this.player1.translateYProperty().bind(yPlayer1);
        this.player2.translateYProperty().bind(yPlayer2);

        moveX = randomX();
    }

    private Double randomX() {
        int condition = (int) (Math.random() * 10 + 1);
        if (condition <= 5) {
            moveX = 4.0;
        } else {
            moveX = -4.0;
        }

        return moveX;
    }

    private Double randomY() {
        int condition = (int) (Math.random() * 10 + 1);
        if (condition <= 5) {
            moveY = 4.0;
        } else {
            moveY = -4.0;
        }

        return moveY;
    }
}
