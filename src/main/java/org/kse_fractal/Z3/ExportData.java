package org.kse_fractal.Z3;

public class ExportData {
    public double G1;
    public double F_in;
    public double R1;
    public double M2;
    public double M3;
    public double FM;

    private double round(double t) { return (double) Math.round(t * 1000) / 1000; }

    public ExportData(Data data) {
        G1 = round(9.81 * data.mass);
        F_in = round(data.mass * data.acceleration);
        R1 = round(G1 - F_in);
        M2 = round(G1 * data.R2 / 1000);
        M3 = round(M2 * ( data.omega2 / data.omega3 ));
        FM = round(M3 / data.R3 * 1000);
    }
}
