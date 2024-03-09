module com.me.arrowgame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens com.me.arrowgame to javafx.fxml;
    exports com.me.arrowgame;
}