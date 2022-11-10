package es.dam.pongjavafx.controllers;

import es.dam.pongjavafx.views.View;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class ViewController {
    private final Label scoreP1 = new Label();
    private final Label scoreP2 = new Label();
    private final Rectangle bordeNorte;
    private final Rectangle bordeSur;
    private final Rectangle bordeEste;
    private final Rectangle bordeOeste;
    private final Rectangle player1;
    private final Rectangle player2;
    private final Circle ball;
    private Double moveX = randomX();
    private Double moveY = randomY();
    private int pointsP1 = 0;
    private int pointsP2 = 0;
    private final View view;

    public ViewController(Rectangle bordeNorte, Rectangle bordeSur, Rectangle bordeEste, Rectangle bordeOeste, Rectangle player1, Rectangle player2, Circle ball, View view) {
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
        ball.translateXProperty().bind(x);
        ball.translateYProperty().bind(y);

        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(0.017), t -> {
                    moveBall(x, y);
                    initControls(getScene(view));
                    checkGolpeo();
                    checkWalls(x, y);
                })
        );

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    private Scene getScene(View view) {
        return view.getScene();
    }

    private void moveBall(DoubleProperty x, DoubleProperty y) {
        x.setValue(x.getValue() + moveX);
        y.setValue(y.getValue() + moveY);
    }

    public void initControls(Scene scene) {
        scene.setOnKeyPressed(event -> {
            double moveYPlayers = 8.0;
            if (event.getCode() == KeyCode.Q) {
                if (!player1.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
                    player1.setTranslateY(player1.getTranslateY() - moveYPlayers);
                }
            }
            if (event.getCode() == KeyCode.A) {
                if (!player1.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
                    player1.setTranslateY(player1.getTranslateY() + moveYPlayers);
                }
            }
            if (event.getCode() == KeyCode.P) {
                if (!player2.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
                    player2.setTranslateY(player2.getTranslateY() - moveYPlayers);
                }
            }
            if (event.getCode() == KeyCode.L) {
                if (!player2.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
                    player2.setTranslateY(player2.getTranslateY() + moveYPlayers);
                }
            }
        });
    }

    private void checkGolpeo() {
        if (ball.getBoundsInParent().intersects(player2.getBoundsInParent())) {
            moveX = -4.0;
            moveY = randomY();
        } else if (ball.getBoundsInParent().intersects(player1.getBoundsInParent())) {
            moveX = 4.0;
            moveY = randomY();
        }
    }

    private void checkWalls(DoubleProperty x, DoubleProperty y) {
        if (ball.getBoundsInParent().intersects(bordeNorte.getBoundsInParent())) {
            moveY = 4.0;
        }

        if (ball.getBoundsInParent().intersects(bordeSur.getBoundsInParent())) {
            moveY = -4.0;
        }

        if (ball.getBoundsInParent().intersects(bordeEste.getBoundsInParent())) {
            resetPositions(x, y);
            updateScore(1);
        }

        if (ball.getBoundsInParent().intersects(bordeOeste.getBoundsInParent())) {
            resetPositions(x, y);
            updateScore(2);
        }
    }

    private void updateScore(int winner) {
        if (winner == 1) {
            pointsP1++;
            scoreP1.setText("PLAYER 1: " + pointsP1);
        } else if (winner == 2) {
            pointsP2++;
            scoreP2.setText("PLAYER 2: " + pointsP2);
        }
    }

    private void resetPositions(DoubleProperty x, DoubleProperty y) {
        this.player1.translateYProperty().setValue(0);
        this.player2.translateYProperty().setValue(0);

        x.setValue(0);
        y.setValue(0);
        this.ball.translateXProperty().bind(x);
        this.ball.translateYProperty().bind(y);
    }

    private Double randomX() {
        Random condition = new Random();
        if (condition.nextBoolean()) {
            moveX = 4.0;
        } else {
            moveX = -4.0;
        }

        return moveX;
    }

    private Double randomY() {
        Random condition = new Random();
        if (condition.nextBoolean()) {
            moveY = 4.0;
        } else {
            moveY = -4.0;
        }

        return moveY;
    }
}
