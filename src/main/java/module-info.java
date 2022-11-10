module es.dam.pongjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens es.dam.pongjavafx to javafx.fxml;
    exports es.dam.pongjavafx;
}