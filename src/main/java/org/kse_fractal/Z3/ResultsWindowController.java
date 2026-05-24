package org.kse_fractal.Z3;

import com.google.gson.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import org.kse_fractal.Z3.Variables.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ResultsWindowController {

    @FXML ListView container;

    JsonObject full_json;
    JsonObject simple_json;
    String for_export_json;

    public void sendResults(JsonObject full_json, JsonObject simple_json, String for_export_json) {
        this.full_json = full_json;
        this.simple_json = simple_json;
        this.for_export_json = for_export_json;

        renderDataTableContainer();
    }

    @FXML private CheckBox allBox;

    @FXML private boolean isFull() {
        return allBox.isSelected();
    }

    public void renderDataTableContainer() {
        container.getItems().clear();

        JsonObject t;
        if(isFull()) t = this.full_json; else t = this.simple_json;

        for (Map.Entry<String, JsonElement> entry : t.entrySet()) {
            try {
                String key = entry.getKey();
                double value = entry.getValue().getAsDouble();
                container.getItems().add(key + ": " + value);
            } catch (RuntimeException e) {}
        }
    }


    @FXML private HBox root_box;

    public void exportJson() {
        try {
            String expData = this.for_export_json;

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("JSON", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(root_box.getScene().getWindow());

            if (file != null) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(expData);
                }
                catch (IOException e) { e.printStackTrace(); }
            }
        } catch (Exception e) {

        }
    }

}

