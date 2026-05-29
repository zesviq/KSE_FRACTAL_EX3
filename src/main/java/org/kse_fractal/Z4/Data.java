package org.kse_fractal.Z4;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Data {
    // Константы
    @SerializedName("Сопротивление R2 (Ом)")
    public final double R2 = 5;
    @SerializedName("Сопротивление R3 (Ом)")
    public final double R3 = 6;
    @SerializedName("Сопротивление R4 (Ом)")
    public final double R4 = 7;
    @SerializedName("Электродвижущая сила, epsilon_3 (В)")
    public final double epsilon_3 = 11;

    @SerializedName("Ток в ветви с гальванометром G [I(G)] (В)")
    public final double Ig = 0;

    public Results results;

    // То, что может быть использовано при счёте.

    public static class Results {
        @Expose
        @SerializedName("Сила тока I_1 (А)")
        public double I1;
        @SerializedName("Сила тока I_2 (А)")
        @Expose
        public double I2;
        @SerializedName("Сила тока I_3 (А)")
        @Expose
        public double I3;
        @SerializedName("Сила тока I_4 (А)")
        @Expose
        public double I4;

        @Expose
        @SerializedName("Сила тока I (А)")
        public double I;

        @Expose
        @SerializedName("Напряжение в ветви AB (В)")
        public double U_ab;
        @Expose
        @SerializedName("Напряжение в ветви DA (В)")
        public double U_da;
        @Expose
        @SerializedName("Напряжение в ветви CD (В)")
        public double U_cd;

        @Expose
        @SerializedName("Сопротивление R1 (Ом)")
        public double R1;

        // Проверки

        @SerializedName("Проверка формул R1")
        public boolean R1_test;

        @SerializedName("Проверка формул I4")
        public boolean I4_test;

        @SerializedName("Проверка валидности данных")
        public boolean MainTest;

        @Expose
        @SerializedName("Проверки")
        public boolean AllTests;
    }

    public double round(double t) {
        BigDecimal f = BigDecimal.valueOf(t);
        BigDecimal rounded = f.setScale(2, RoundingMode.HALF_UP); // 2.35
        return (double) rounded.doubleValue();
    }

    public Results calculate_27(double epsilon) {
        Results R = new Results();

        R.I3 = round((epsilon_3 + epsilon) / (R3 + R4));
        R.I4 = round(R.I3);

        R.I2 = round(R.I4 * R4 / R2);
        R.I1 = R.I2;

        R.I = R.I1 + R.I3;

        double I4_formula_test = R.I - R.I2;
        R.I4_test = R.I4 == I4_formula_test;

        R.R1 = (R.I3 * R3 - epsilon_3) / R.I1;

        double R1_formula_test = (epsilon - R.I2 * R2 ) / R.I1;
        R.R1_test = (R1_formula_test == R.R1);

        R.MainTest = R.I1 * R.R1 - R.I3 * R3 - R.I4 * R4 + R.I2 * R2 == -epsilon_3;

        R.AllTests = R.MainTest && R.R1_test && R.I4_test;

        R.U_ab = R.I1 * R.R1;
        R.U_cd = R.I4 * R4;
        R.U_da = R.I2 * R2;

        return R;
    }
}
