package Utils;

import Old.JBoolRandGenerator;
import Old.RandGenerator;

import java.util.ArrayList;

import static Utils.AverageUtils.avgD;
import static Utils.RoundUtils.round;

public class TestRandom {
    public static void main(String[] args) {
        RandGenerator r = new JBoolRandGenerator();
        testInt(10, 1000, r);
    }

    public static void testInt(int amtBins, int amtTestRuns, RandGenerator r) {
        int amtSuperTestRuns = 1000;
        ArrayList<Double> sqrts = new ArrayList<>();
        double expectedPerBin = (double) amtTestRuns / (double) amtBins;
        for(int j = 0; j < amtSuperTestRuns; j++) {

            ArrayList<Integer> al = new ArrayList<>(10);
            for(int i = 0; i < 10; i++) {
                al.add(0);
            }
            int min = 0, max = amtBins - 1;
            for(int i = 0; i < amtTestRuns; i++) {
                int randInt = r.getInt(min,max);
                al.set(randInt, al.get(randInt)+1);
            }
            double sumOfSquaredError = 0;

            for(int i : al) {
                sumOfSquaredError += Math.pow((expectedPerBin - i),2);
            }
            //System.out.println("Sum of squared error "+sumOfSquaredError);
            double sqrt = Math.sqrt(sumOfSquaredError);
            sqrts.add(sqrt);
            //System.out.println(j+": "+ sqrt);
        }

        double avgSqrts = avgD(sqrts);
        double relativeAvgError = avgSqrts / expectedPerBin;
        double relativeAvgErrPercent = relativeAvgError * 100;
        relativeAvgErrPercent = round(relativeAvgErrPercent, 3);
        System.out.println(relativeAvgErrPercent+"% Error on average");
    }
}
