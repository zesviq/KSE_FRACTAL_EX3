package org.kse_fractal.Z3;

import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kse_fractal.Z3.Variables.Data;

import java.io.*;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;

public class KSE3Controller {
    public Data data = new Data();

    @FXML private TextField field_time;
    @FXML private TextField field_mass;

    @FXML private TextField field_R2;
    @FXML private TextField field_r2;
    @FXML private TextField field_R3;
    @FXML private TextField field_r3;

    @FXML private ImageView imageView;


    public void initialize() throws IOException {
        imageView.setImage(new Image(Objects.requireNonNull(KSE3Application.class.getResourceAsStream("image.png"))));
        field_R2.setText(String.valueOf(data.R2));
        field_r2.setText(String.valueOf(data.r2));
        field_R3.setText(String.valueOf(data.R3));
        field_r3.setText(String.valueOf(data.r3));
        this.renderEx();
    }

    @FXML public CheckBox toggleEx3Checkbox;
    @FXML public Label exNum;
    @FXML public Label exDesc;

    @FXML public Label exTextLabel;

    @FXML public Text exTextEx2;
    @FXML public Text exTextEx3;

    @FXML private boolean isEx3 () {
        return toggleEx3Checkbox.isSelected();
    }

    @FXML public void renderEx () {
        if(!isEx3()) {
            System.out.println("Render ex 2");

            field_mass.setDisable(true);
            exNum.setText("Задание 2");
            exDesc.setText("Кинематика");
            exTextLabel.setText("Текст задачи №2.");

            exTextEx2.setVisible(true);
            exTextEx2.setManaged(true);
            exTextEx3.setVisible(false);
            exTextEx3.setManaged(false);


        } else {
            System.out.println("Render ex 3");

            field_mass.setDisable(false);
            exNum.setText("Задание 3");
            exDesc.setText("Динамика");
            exTextLabel.setText("Текст задачи №3.");

            exTextEx2.setVisible(false);
            exTextEx2.setManaged(false);
            exTextEx3.setVisible(true);
            exTextEx3.setManaged(true);
        }
    }

    @FXML protected void RunCalculation() throws IOException {
        try {
            data.time = Double.parseDouble(field_time.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("results_window.fxml"));
            Parent root = loader.load();
            ResultsWindowController rc = loader.getController();

            Gson gsonFull = new GsonBuilder().setPrettyPrinting().create();

            Gson gsonSelected = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
//                    .setPrettyPrinting()
                    .create();

            if(!isEx3()) {
                rc.sendResults(
                        JsonParser.parseString(gsonFull.toJson(data.calculateEx2())).getAsJsonObject(),
                        JsonParser.parseString(gsonSelected.toJson(data.calculateEx2())).getAsJsonObject(),
                        gsonFull.toJson(data.calculateEx2())
                );
            } else {
                data.mass = Double.parseDouble(field_mass.getText());

                JsonObject merged = new JsonObject();

                for (Map.Entry<String, JsonElement> e : JsonParser.parseString(gsonFull.toJson(data.calculateEx3())).getAsJsonObject().entrySet()) {
                    merged.add(e.getKey(), e.getValue());
                }

                for (Map.Entry<String, JsonElement> e : JsonParser.parseString(gsonFull.toJson(data.calculateEx2())).getAsJsonObject().entrySet()) {
                    merged.add(e.getKey(), e.getValue());
                }

                rc.sendResults(
                        merged,
                        JsonParser.parseString(gsonSelected.toJson(data.calculateEx3())).getAsJsonObject(),
                        gsonFull.toJson(data.calculateEx3())
                );
            }

            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(getClass().getResource("styles.css")));

            Stage stage = new Stage();
            stage.setTitle("Результаты расчёта");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // блокирует родительское окно
            stage.show();

        } catch (Exception e) {
            createDataValidationError();
        }
    }

    @FXML public void createDataValidationError() {
        Stage dialog = new Stage();;
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Диалог");
        dialog.setWidth(300);
        dialog.setHeight(120);
        dialog.setResizable(false);

        Label msg = new Label("Произошла ошибка получения данных из полей. Проверьте введённые данные.");
        msg.setWrapText(true);
        Button ok = new Button("OK");
        ok.setOnAction(ev -> dialog.close());

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().setAll(msg, ok);
        vbox.setPadding(new javafx.geometry.Insets(10));
        Scene dialogScene = new Scene(vbox);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

}
