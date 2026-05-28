package org.kse_fractal.Z4;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

        @SerializedName("Электродвижущая сила, epsilon (Ом)")
        public double epsilon;

        public double V_a;
        public double V_b;

        public double V_d = 0; // п. 4.5.

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

    }

    public double round(double t) { return (double) Math.round(t * 1000) / 1000; }

    public Results calculate_27(double epsilon) {
        Results R = new Results();

        R.epsilon = epsilon;

        R.V_a = round( - ( R4 * (epsilon + epsilon_3) / (R3 + R4) ) );
        R.V_b = R.V_d - epsilon;

        R.R1 = round(-(R2 * -(-R.V_a-(-R.V_b))) / R.V_a); // у. 4.8

        R.I1 = round( (R.V_a - R.V_b ) / R.R1 );
        R.I2 = R.I1; // ур. 4.13
        R.I3 = round( (R.V_a + epsilon + epsilon_3) / R3 );
        R.I4 = R.I3;

        R.I = round( R.I1 + R.I3 ); // 4.16

        R.U_ab = round(R.I1 * R.R1);
        R.U_da = round(R.I2 * R2);
        R.U_cd = round(R.I4 * R4);

        return R;
    }
}
