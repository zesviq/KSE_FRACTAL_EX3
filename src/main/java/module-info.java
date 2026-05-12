module org.example.kse_maven_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens org.kse_fractal.Z3 to javafx.fxml;
    exports org.kse_fractal.Z3;
}