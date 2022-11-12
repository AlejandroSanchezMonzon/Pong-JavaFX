module es.dam.pongjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens es.dam.pongjavafx to javafx.fxml;
    exports es.dam.pongjavafx;
}