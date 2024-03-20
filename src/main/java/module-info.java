module com.me.arrowgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.google.gson;
    requires gson.extras;

    opens com.me.arrowgame to javafx.fxml, com.google.gson, gson.extras;
    exports com.me.arrowgame;
}