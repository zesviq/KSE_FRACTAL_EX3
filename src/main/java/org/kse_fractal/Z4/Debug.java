package org.kse_fractal.Z4;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Debug {
    public static void main(String[] args) {
        Data data = new Data();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("Continue testing!");
        System.out.println(gson.toJson(data));
    }
}
