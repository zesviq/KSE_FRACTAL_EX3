package org.kse_fractal.Z4;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppKSE4 extends Application {
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppKSE4.class.getResource("main_window.fxml"));
        scene = new Scene(fxmlLoader.load(), 900, 610);
        scene.getStylesheets().add(String.valueOf(AppKSE4.class.getResource("styles.css")));
        stage.setTitle("КСЕ: Задание 4");

        stage.setScene(scene);
        stage.show();
    }
}
