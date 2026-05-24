package org.kse_fractal.Z3.Variables;

public class Data {
    public double time; // получается для второй задачи
    public double mass; // получается для третьей задачи

    public final double R2 = 90;
    public final double r2 = 50;
    public final double R3 = 130;
    public final double r3 = 95;

    public Ex2 calculateEx2() {
        return new Ex2(time, R2, r2, R3, r3);
    }

    public Ex3 calculateEx3() {
        return new Ex3(mass, this.calculateEx2(), R2, R3);
    }

}