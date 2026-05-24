module org.example.kse_maven_test {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.google.gson;


    opens org.kse_fractal.Z3 to javafx.fxml;
    exports org.kse_fractal.Z3;
    exports org.kse_fractal.Z3.Variables;
    opens org.kse_fractal.Z3.Variables to javafx.fxml;
}