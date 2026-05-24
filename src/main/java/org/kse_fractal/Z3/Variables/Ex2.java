package org.kse_fractal.Z3.Variables;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ex2 {
    @Expose
    @SerializedName("Расстояние, которое проходит груз 1 за сек (мм)")
    public double S1; //

    @SerializedName("Скорость любой точки груза 1 (м/c)")
    public transient double V1; //
    @SerializedName("Тангенсальное ускорение любой точки груза 1 (м/c)")
    public transient double At1; //
    
    @SerializedName("Скорость точкаи A (м/c)")
    public double Va;
    @SerializedName("Тангенсальное ускорение точки A (м/c^2)")
    public double Ata; // ускорение во второй задаче
    @SerializedName("Угловая скорость точки A (м/c)")
    public double omega2;
    @SerializedName("Угловое ускорение точки A (м/c^2)")
    public double epsilon2;

    
    @SerializedName("Скорость точкаи B (м/c)")
    public double Vb;
    
    @SerializedName("Тангенсальное ускорение точки B (м/c^2)")
    public double Atb;
    
    @SerializedName("Угловая скорость точки B (м/c)")
    public double omega3;
    
    @SerializedName("Угловое ускорение точки B (м/c^2)")
    public double epsilon3;
    
    // общее
    @Expose
    @SerializedName("Скорость (мм/с)")
    public double VM;
    @Expose
    @SerializedName("Тангенсальное ускорение (м/c^2)")
    public double AtM; // тангенсальное ускорение
    @Expose
    @SerializedName("Нормальное ускорение (мм/с^2)")
    public double AnM; // нормальное ускорение
    @Expose
    @SerializedName("Полное ускорение (мм/с^2)")
    public double Am; // полное ускорение

    public double round(double t) { return (double) Math.round(t * 1000) / 1000; }

    public transient final double y1_t2 = 94;
    public final double y1 (double time) { // уравнение
        return 35 + y1_t2 * Math.pow(time, 2);
    }

    public Ex2(double time, double R2, double r2, double R3, double r3) {
        S1 = y1(time);
        V1 = round(y1_t2 * 2 * time * 0.001);
        Va = V1;
        At1 = round(y1_t2 * 2 * 0.001);
        Ata = At1;

        omega2 = round(Va / (R2 * 0.001));
        epsilon2 = round(Ata / (R2 * 0.001));

        Vb = round(omega2 * r2 * 0.001);
        Atb = round(epsilon2 * r2 * 0.001);

        omega3 = round(Vb / (r3 * 0.001));
        epsilon3 = round(Atb / (r3 * 0.001));

        VM = round(omega3 * R3);
        AtM = round(epsilon3 * R3 * 0.001);
        AnM = round(Math.pow((VM * 0.001), 2) / R3 * 1000 * 1000 );
        Am = round(Math.pow( ( Math.pow(AnM, 2) + Math.pow(AtM, 2) ) , 0.5));
    }
}
