package org.kse_fractal.Z3;

public class Data {
    public double time;
    public double mass;
    public double acceleration;

    public double R2;
    public double r2;
    public double R3;
    public double r3;

    public double omega2;
    public double omega3;

    public ExportData exportData;

    public Data( double time, double mass, double acceleration, double R2, double r2, double R3, double r3, double omega2, double omega3 ) {
        this.time = time;
        this.mass = mass;
        this.acceleration = acceleration;

        this.R2 = R2;
        this.r2 = r2;
        this.R3 = R3;
        this.r3 = r3;

        this.omega2 = omega2;
        this.omega3 = omega3;
    } public Data() {}
}