package org.kse_fractal.Z3;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class KSE3Controller {
    @FXML private Label welcomeText;

    @FXML private TextField password;

    @FXML private VBox container;

    @FXML private HBox root;

    @FXML private TextField field_time;
    @FXML private TextField field_mass;
    @FXML private TextField field_acceleration;
    @FXML private TextField field_R2;
    @FXML private TextField field_r2;
    @FXML private TextField field_R3;
    @FXML private TextField field_r3;
    @FXML private TextField field_omega2;
    @FXML private TextField field_omega3;

    @FXML private ImageView imageView;


    @FXML protected void RunCalculation() throws IOException {
        try {
            Data data = this.getDataValues();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("results_window.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);

            scene.getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));

            ResultsWindowController rc = loader.getController();
            rc.sendResults(data);

            Stage stage = new Stage();
            stage.setTitle("Результаты расчёта");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // блокирует родительское окно (опционально)
            stage.show();
        } catch (Exception e) {  }
    }

    @FXML protected void ImportValuesFromFile() {
        try {
            Gson gson = new Gson();
            FileChooser chooser = new FileChooser();

            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

            File file = chooser.showOpenDialog(root.getScene().getWindow());

            if (file != null) {
                try (FileReader reader = new FileReader(file)) {
                    Data data = gson.fromJson(reader, Data.class);
                    this.setDataValues(data);
                } catch (IOException e) { e.printStackTrace(); }
            }
        } catch (Exception e) {

        }
    }

    @FXML protected void ExportValuesToFile() {
        try {
            Gson gson = new Gson();
            Data data = getDataValues();

            System.out.println(gson.toJson(data));
            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("JSON files", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(root.getScene().getWindow());

            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) { gson.toJson(data, writer); }
                catch (IOException e) { e.printStackTrace(); }
            }
        } catch (Exception e) {

        }
    }

    public void initialize() throws IOException {
        imageView.setImage(new Image(Objects.requireNonNull(KSE3Application.class.getResourceAsStream("image.png"))));
        field_mass.getText();
        this.setDefaults();
    }

    @FXML public void setDefaults() throws IOException {
        Data defaults = new Data(12, 20, 0.188, 90, 50, 130, 95, 25.067, 13.193);
        setDataValues(defaults);
    }

    @FXML public void setDataValues(Data data) throws IOException {
        if (data.exportData != null) {
            this.RunCalculation();
        }
        try {
            field_time.setText(String.valueOf(data.time));
            field_mass.setText(String.valueOf(data.mass));
            field_acceleration.setText(String.valueOf(data.acceleration));
            field_R2.setText(String.valueOf(data.R2));
            field_r2.setText(String.valueOf(data.r2));
            field_R3.setText(String.valueOf(data.R3));
            field_r3.setText(String.valueOf(data.r3));
            field_omega2.setText(String.valueOf(data.omega2));
            field_omega3.setText(String.valueOf(data.omega3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML public Data getDataValues() throws IOException {
        try {
            Data data = new Data();

            data.time = Double.parseDouble(field_time.getText());
            data.acceleration = Double.parseDouble(field_acceleration.getText());
            data.mass = Double.parseDouble(field_mass.getText());

            data.R2 = Double.parseDouble(field_R2.getText());
            data.r2 = Double.parseDouble(field_r2.getText());
            data.R3 = Double.parseDouble(field_R3.getText());
            data.r3 = Double.parseDouble(field_r3.getText());

            data.omega2 = Double.parseDouble(field_omega2.getText());
            data.omega3 = Double.parseDouble(field_omega3.getText());

            return data;
        } catch (Exception e) {
            Stage dialog = new Stage();;
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Диалог");
            dialog.setWidth(300);
            dialog.setHeight(140);

//            Label msg = new Label("Произошла ошибка получения данных из полей. Проверьте введённые данные.");
//            msg.setWrapText(true);
//            Button ok = new Button("OK");
//            ok.setOnAction(ev -> dialog.close());
//
//            VBox vbox = new VBox();
//            vbox.setSpacing(10);
//            vbox.getChildren().setAll(msg, ok);
//            vbox.setPadding(new javafx.geometry.Insets(10));
//            Scene dialogScene = new Scene(vbox);
            FXMLLoader fxmlLoader = new FXMLLoader(KSE3Application.class.getResource("main_window.fxml"));
            Scene dialogScene = new Scene(fxmlLoader.load(), 900, 21);
            dialog.setScene(dialogScene);
            dialog.showAndWait();

            throw new RuntimeException(e);
        }
    }

}
