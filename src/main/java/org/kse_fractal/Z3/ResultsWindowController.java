package org.kse_fractal.Z3;

import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsWindowController {

    @FXML ListView results_container;
    @FXML ListView starts_container;

    private Data data;

    public void sendResults(Data data) {
        this.data = data;
        Gson gson = new Gson();
        this.data.exportData = new ExportData(data);
        exportAllResults();
        renderDataTable();
    }

    public String rndr(String key, String value, String valyuta) {
        return key + ": " + value + " " + valyuta;
    }
    
    public void renderDataTable() {

        starts_container.getItems().setAll(
                rndr("Масса груза", String.valueOf(data.mass), "(кг)"),
                rndr("Ускорение", String.valueOf(data.acceleration), "(м/с)^2"),
                rndr("Момент времени", String.valueOf(data.time), "(с)"),
                rndr("Радиус R2", String.valueOf(data.R2), "(мм)"),
                rndr("Радиус r2", String.valueOf(data.r2), "(мм)"),
                rndr("Радиус R3", String.valueOf(data.R3), "(мм)"),
                rndr("Радиус r3", String.valueOf(data.R3), "(мм)"),
                rndr("Угловая скорость блоков ω2", String.valueOf(data.omega2), "(с)^-1"),
                rndr("Угловая скорость блоков ω3", String.valueOf(data.omega3), "(с)^-1")

        );

        results_container.getItems().setAll(
                rndr("Сила тяжести груза", String.valueOf(data.exportData.G1), "(Н)"),
                rndr("Сила инерции", String.valueOf(data.exportData.F_in), "(Н)"),
                rndr("Внешняя сила, необх. для прекращения движения", String.valueOf(data.exportData.R1), "(Н)"),
                rndr("Момент 2", String.valueOf(data.exportData.M2), "(Н · м)"),
                rndr("Момент 3", String.valueOf(data.exportData.M3), "(Н · м)"),
                rndr("Силы в точке M большого цилиндра блока 3", String.valueOf(data.exportData.FM), "(Н)")
        );
    }

    public void exportAllResults() {
        Gson gson = new Gson();
        System.out.println(gson.toJson(this.data));
    }

    public String exportTextData() {
        StringBuilder sb = new StringBuilder();

        sb.append("Дано:\n");

        starts_container.getItems().stream().sorted().forEach(
                item -> sb.append("\n" + item.toString())
        );

        sb.append("\n\nИтог:\n");

        results_container.getItems().stream().sorted().forEach(
            item -> sb.append("\n" + item.toString())
        );

        return sb.toString();
    }

    @FXML private HBox root_box;

    public void exportTXT() {
        try {
            String expData = this.exportTextData();

            FileChooser fileChooser = new FileChooser();

            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TXT", "*.txt");
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
