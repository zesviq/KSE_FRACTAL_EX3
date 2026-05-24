package org.kse_fractal.Z3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KSE3Application extends Application {
    Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KSE3Application.class.getResource("main_window.fxml"));
        scene = new Scene(fxmlLoader.load(), 900, 610);
        scene.getStylesheets().add(String.valueOf(KSE3Application.class.getResource("styles.css")));
        stage.setTitle("КСЕ: Задание 2+3: Кинематика и динамика");

        stage.setScene(scene);
        stage.show();
    }
}
