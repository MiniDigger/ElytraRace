package me.MiniDigger.ElytraRace;

public class ElytraRaceUtil {

    public static boolean almostEqual(double a, double b, double eps){
        return Math.abs(a-b)<eps;
    }
}
