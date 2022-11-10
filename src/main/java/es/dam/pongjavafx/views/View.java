package es.dam.pongjavafx.views;

import es.dam.pongjavafx.controllers.ViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class View extends StackPane {
    public View() {
        this.setBackground(new Background(new BackgroundFill(Color.FORESTGREEN, new CornerRadii(0), new Insets(0))));

        Label title = new Label("PONG! - THE GAME");
        title.setTextFill(Color.WHITE);
        title.setScaleX(1.5);
        title.setScaleY(1.5);
        title.setPadding(new Insets(5));
        title.setFont(Font.font("Century Gothic"));
        StackPane.setAlignment(title, Pos.TOP_CENTER);

        Label scoreP1 = new Label("PLAYER 1");
        scoreP1.setTextFill(Color.WHITE);
        scoreP1.setScaleX(1.5);
        scoreP1.setScaleY(1.5);
        scoreP1.setPadding(new Insets(5));
        scoreP1.setFont(Font.font("Century Gothic"));
        scoreP1.translateXProperty().bind(this.widthProperty().divide(-3));
        scoreP1.translateYProperty().bind(this.heightProperty().divide(2.1));

        Label scoreP2 = new Label("PLAYER 2");
        scoreP2.setTextFill(Color.WHITE);
        scoreP2.setScaleX(1.5);
        scoreP2.setScaleY(1.5);
        scoreP2.setPadding(new Insets(5));
        scoreP2.setFont(Font.font("Century Gothic"));
        scoreP2.translateXProperty().bind(this.widthProperty().divide(3));
        scoreP2.translateYProperty().bind(this.heightProperty().divide(2.1));

        Rectangle lineaCentro = new Rectangle();
        lineaCentro.setFill(Color.BLACK);
        lineaCentro.heightProperty().bind(this.heightProperty().divide(1));
        lineaCentro.widthProperty().bind(this.widthProperty().divide(40));

        Circle circuloCentro = new Circle(30, Color.BLACK);

        Rectangle bordeNorte = new Rectangle();
        bordeNorte.setFill(Color.BLACK);
        bordeNorte.heightProperty().bind(this.heightProperty().divide(25));
        bordeNorte.widthProperty().bind(this.widthProperty().divide(1));
        StackPane.setAlignment(bordeNorte, Pos.TOP_CENTER);

        Rectangle bordeSur = new Rectangle();
        bordeSur.setFill(Color.BLACK);
        bordeSur.heightProperty().bind(this.heightProperty().divide(25));
        bordeSur.widthProperty().bind(this.widthProperty().divide(1));
        StackPane.setAlignment(bordeSur, Pos.BOTTOM_CENTER);

        Rectangle bordeEste = new Rectangle();
        bordeEste.setFill(Color.BLACK);
        bordeEste.heightProperty().bind(this.heightProperty().divide(1));
        bordeEste.widthProperty().bind(this.widthProperty().divide(35));
        StackPane.setAlignment(bordeEste, Pos.CENTER_RIGHT);

        Rectangle bordeOeste = new Rectangle();
        bordeOeste.setFill(Color.BLACK);
        bordeOeste.heightProperty().bind(this.heightProperty().divide(1));
        bordeOeste.widthProperty().bind(this.widthProperty().divide(35));
        StackPane.setAlignment(bordeOeste, Pos.CENTER_LEFT);

        Rectangle player1 = new Rectangle();
        player1.setFill(Color.BLUE);
        player1.translateXProperty().bind(this.widthProperty().divide(-2.3));
        player1.heightProperty().bind(this.heightProperty().divide(4));
        player1.widthProperty().bind(this.widthProperty().divide(40));

        Rectangle player2 = new Rectangle();
        player2.setFill(Color.RED);
        player2.translateXProperty().bind(this.widthProperty().divide(2.3));
        player2.heightProperty().bind(this.heightProperty().divide(4));
        player2.widthProperty().bind(this.widthProperty().divide(40));

        Circle ball = new Circle(15, Color.WHITE);
        ViewController controller = new ViewController(bordeNorte, bordeSur, bordeEste, bordeOeste, player1, player2, ball, this);
        controller.play();

        this.getChildren().addAll(bordeNorte, bordeSur, bordeEste, bordeOeste, lineaCentro, circuloCentro, title, scoreP1, scoreP2, player1, player2, ball);
    }
}