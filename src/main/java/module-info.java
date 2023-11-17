module com.example.demo2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.example.demo2 to javafx.fxml;
    exports com.example.demo2;
}