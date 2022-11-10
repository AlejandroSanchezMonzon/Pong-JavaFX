package es.dam.pongjavafx.views;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.Random;

public class View extends StackPane {
    private final Label scoreP1;
    private final Label scoreP2;
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

    public View() {
        this.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, new CornerRadii(0), new Insets(0))));

        Label title = new Label("PONG! - THE GAME");
        title.setTextFill(Color.WHITE);
        title.setScaleX(1.5);
        title.setScaleY(1.5);
        title.setPadding(new Insets(5));
        title.setFont(Font.font("Century Gothic"));
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        this.scoreP1 = new Label("PLAYER 1");
        this.scoreP1.setTextFill(Color.WHITE);
        this.scoreP1.setScaleX(1.5);
        this.scoreP1.setScaleY(1.5);
        this.scoreP1.setPadding(new Insets(5));
        this.scoreP1.setFont(Font.font("Century Gothic"));
        this.scoreP1.translateXProperty().bind(this.widthProperty().divide(-3));
        this.scoreP1.translateYProperty().bind(this.heightProperty().divide(2.1));

        this.scoreP2 = new Label("PLAYER 2");
        this.scoreP2.setTextFill(Color.WHITE);
        this.scoreP2.setScaleX(1.5);
        this.scoreP2.setScaleY(1.5);
        this.scoreP2.setPadding(new Insets(5));
        this.scoreP2.setFont(Font.font("Century Gothic"));
        this.scoreP2.translateXProperty().bind(this.widthProperty().divide(3));
        this.scoreP2.translateYProperty().bind(this.heightProperty().divide(2.1));

        Rectangle lineaCentro = new Rectangle();
        lineaCentro.setFill(Color.BLACK);
        lineaCentro.heightProperty().bind(this.heightProperty().divide(1));
        lineaCentro.widthProperty().bind(this.widthProperty().divide(40));

        Circle circuloCentro = new Circle(30, Color.BLACK);

        this.bordeNorte = new Rectangle();
        this.bordeNorte.setFill(Color.BLACK);
        this.bordeNorte.heightProperty().bind(this.heightProperty().divide(25));
        this.bordeNorte.widthProperty().bind(this.widthProperty().divide(1));
        StackPane.setAlignment(bordeNorte, Pos.TOP_CENTER);

        this.bordeSur = new Rectangle();
        this.bordeSur.setFill(Color.BLACK);
        this.bordeSur.heightProperty().bind(this.heightProperty().divide(25));
        this.bordeSur.widthProperty().bind(this.widthProperty().divide(1));
        StackPane.setAlignment(bordeSur, Pos.BOTTOM_CENTER);

        this.bordeEste = new Rectangle();
        this.bordeEste.setFill(Color.BLACK);
        this.bordeEste.heightProperty().bind(this.heightProperty().divide(1));
        this.bordeEste.widthProperty().bind(this.widthProperty().divide(35));
        StackPane.setAlignment(bordeEste, Pos.CENTER_RIGHT);

        this.bordeOeste = new Rectangle();
        this.bordeOeste.setFill(Color.BLACK);
        this.bordeOeste.heightProperty().bind(this.heightProperty().divide(1));
        this.bordeOeste.widthProperty().bind(this.widthProperty().divide(35));
        StackPane.setAlignment(bordeOeste, Pos.CENTER_LEFT);

        this.player1 = new Rectangle();
        this.player1.setFill(Color.BLUE);
        this.player1.translateXProperty().bind(this.widthProperty().divide(-2.3));
        this.player1.heightProperty().bind(this.heightProperty().divide(4));
        this.player1.widthProperty().bind(this.widthProperty().divide(40));
        ;

        this.player2 = new Rectangle();
        this.player2.setFill(Color.RED);
        this.player2.translateXProperty().bind(this.widthProperty().divide(2.3));
        this.player2.heightProperty().bind(this.heightProperty().divide(4));
        this.player2.widthProperty().bind(this.widthProperty().divide(40));


        this.ball = new Circle(15, Color.WHITE);

        play();

        this.getChildren().addAll(bordeNorte, bordeSur, bordeEste, bordeOeste, lineaCentro, circuloCentro, title, scoreP1, scoreP2, player1, player2, ball);
    }

    private void play() {
        DoubleProperty x = new SimpleDoubleProperty();
        DoubleProperty y = new SimpleDoubleProperty();
        ball.translateXProperty().bind(x);
        ball.translateYProperty().bind(y);

        Timeline animation = new Timeline(
                new KeyFrame(Duration.seconds(0.017), t -> {
                    moveBall(x, y);
                    initControls(getScene());
                    checkGolpeo();
                    checkWalls(x, y);
                })
        );

        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
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
