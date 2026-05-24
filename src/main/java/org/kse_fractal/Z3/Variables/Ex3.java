package org.kse_fractal.Z3.Variables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Until;

public class Ex3 {
    @Expose
    @SerializedName("Сила тяжести, действующая на груз (Н)")
    public double G1;
    @SerializedName("Модуль силы инерции (Н)")
    public double F_in;

    @SerializedName("Сила, необходимая для прекращения движения груза (Н)")
    public double R1;

    @SerializedName("Момент 2 (Н * м)")
    public double M2;
    @SerializedName("Момент 3 (Н * м)")
    public double M3;
    @Expose
    @SerializedName("Силы в точке M большого цилиндра блока 3 (Н)")
    public double FM;

    public Ex2 ex2;

    public double round(double t) { return (double) Math.round(t * 1000) / 1000; }
    
    public Ex3 (double mass, Ex2 ex2, double R2, double R3) {
        this.ex2 = ex2;

        G1 = round(9.81 * mass);
        F_in = round(mass * ex2.Ata);
        R1 = round(G1 - F_in);
        M2 = round(G1 * R2 / 1000);
        M3 = round(M2 * ( ex2.omega2 / ex2.omega3 ));
        FM = round(M3 / R3 * 1000);
    }
}
