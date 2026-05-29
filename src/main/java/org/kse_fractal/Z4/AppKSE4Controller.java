
package org.kse_fractal.Z4;

import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kse_fractal.Z3.KSE3Controller;
import org.kse_fractal.Z3.ResultsWindowController;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class AppKSE4Controller {
    public Data data = new Data();

    @FXML ImageView imageView;

    @FXML HBox root;

    @FXML TextField field_R2, field_R3, field_R4, field_epsilon_3, field_epsilon;

    public void initialize() {
        imageView.setImage(new Image(Objects.requireNonNull(AppKSE4.class.getResourceAsStream("image.png"))));

        field_R2.setText(String.valueOf(data.R2));
        field_R3.setText(String.valueOf(data.R3));
        field_R4.setText(String.valueOf(data.R4));
        field_epsilon_3.setText(String.valueOf(data.epsilon_3));
    }

    public void RunCalculation()  throws IOException {
        try {
            Data.Results results = data.calculate_27(Double.parseDouble(field_epsilon.getText()));

            FXMLLoader loader = new FXMLLoader(KSE3Controller.class.getResource("results_window.fxml"));
            Parent root = loader.load();
            ResultsWindowController rc = loader.getController();

            Gson gsonFull = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                        BigDecimal bd = BigDecimal.valueOf(src).setScale(2, RoundingMode.HALF_UP);
                        return new JsonPrimitive(bd); // будет записано как 2.00 или 2 depending on BigDecimal
                    })
                    .registerTypeAdapter(Boolean.class, (JsonSerializer<Boolean>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(Double.valueOf(src != null && src ? 1.0 : 0.0))
                    )
                    .registerTypeAdapter(boolean.class, (JsonSerializer<Boolean>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(Double.valueOf(src ? 1.0 : 0.0))
                    )
                    .registerTypeAdapter(double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                        BigDecimal bd = BigDecimal.valueOf(src).setScale(2, RoundingMode.HALF_UP);
                        return new JsonPrimitive(bd);
                    }).create();

            Gson gsonSelected = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                        BigDecimal bd = BigDecimal.valueOf(src).setScale(2, RoundingMode.HALF_UP);
                        return new JsonPrimitive(bd); // будет записано как 2.00 или 2 depending on BigDecimal
                    })
                    .registerTypeAdapter(double.class, (JsonSerializer<Double>) (src, typeOfSrc, context) -> {
                        BigDecimal bd = BigDecimal.valueOf(src).setScale(2, RoundingMode.HALF_UP);
                        return new JsonPrimitive(bd);
                    })
                    .registerTypeAdapter(Boolean.class, (JsonSerializer<Boolean>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(Double.valueOf(src != null && src ? 1.0 : 0.0))
                    )
                    .registerTypeAdapter(boolean.class, (JsonSerializer<Boolean>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(Double.valueOf(src ? 1.0 : 0.0))
                    )
//                    .setPrettyPrinting()
                    .create();

            rc.sendResults(
                    JsonParser.parseString(gsonFull.toJson(results)).getAsJsonObject(),
                    JsonParser.parseString(gsonSelected.toJson(results)).getAsJsonObject(),
                    gsonFull.toJson(data.results)
            );

            Scene scene = new Scene(root);
            scene.getStylesheets().add(String.valueOf(AppKSE4.class.getResource("styles.css")));

            Stage stage = new Stage();
            stage.setTitle("Результаты расчёта");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); // блокирует родительское окно
            stage.show();

        } catch (NumberFormatException e) {
            KSE3Controller.createDataValidationError();
        }

    }

}
