import BuffonsNeedle.BuffonsNeedle;
import Utils.AverageUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ArrayList<Double> piEstimates = new ArrayList<>();
        for(int i = 0; i < 1000000; i++) {
            double tmp = BuffonsNeedle.buffonsNeedle(1000000, 244, 67);
            piEstimates.add(tmp);
            List<Double> sub = piEstimates.subList(0,i);
            System.out.println(AverageUtils.avgD(sub));
        }
        double piEstimate = AverageUtils.avgD(piEstimates);
        System.out.println("PI ESTIMATE: " + piEstimate);
        System.out.println("PI:          " + Math.PI);
        System.out.println("Error: " + Math.abs(Math.PI-piEstimate));

    }

}
