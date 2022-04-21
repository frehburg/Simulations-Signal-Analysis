package EmpiricalTestForRandomNumberGenerators;

import RandomNumberGenerators.UnifRandGenerator;
import Utils.AverageUtils;
import Utils.Interval;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Tests random number generators for uniformity
 * returns whether the random nubers are uniformly distributed
 * Assumes independence in the u
 */
public class ChiSquareTest {
    public static boolean chiSquareTest(UnifRandGenerator r, int k, long n, double alpha) {
        //1. generate n random numbers from r
        ArrayList<Double> u = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            u.add(r.getRandomNumber());
        }
        //2. divide [0,1) into k subintervals (bins)
        double delta = 1.0/ (double) k;//length of each subinterval;
        ArrayList<Interval> bins = new ArrayList<>();
        for(int i = 0; i < k; i++) {
            bins.add(new Interval(i*delta,(i+1)*delta,true, false));
        }
        //3. sort the random numbers into the bins
        Integer[] f = new Integer[k]; // let f_j be the number of the Ui’s that are in the jth subinterval
        for(int i = 0; i < k; i++) {
            f[i] = 0;
        }
        //sort all random numbers into the right bins
        for(double randomNumber : u) {
            //find right bin
            int rightBin = -1;
            for(int j = 0; j < k; j++) {
                Interval bin = bins.get(j);
                if(bin.isIn(randomNumber)) {
                    rightBin = j;
                }
            }
            //increment
            f[rightBin]++;
        }
        System.out.println("Average number in bin: " + AverageUtils.avgI(Arrays.asList(f)) + " expected value: " + (double)n/(double) k);
        //4. calculate chiSquared value
        double chiSquared = 0;
        for(int j = 0; j < k; j++) {
            chiSquared += Math.pow((f[j] - ((double)n/(double) k)),2);
        }
        chiSquared = ((double)k/(double) n) * chiSquared;

        // 5. reject if chisquared > chisquared_(k-1),(1-alpha)
        // chisquared_(k-1),(1-alpha) is the upper 1 - alpha critical point of the chi-square distribution with k - 1 df
        ChiSquaredDistribution csd = new ChiSquaredDistribution(k - 1);
        double upperAlphaPercentile = csd.inverseCumulativeProbability(1-alpha); // gets the upper alpha percentile
        boolean reject;
        if(chiSquared > upperAlphaPercentile) {
            reject = true;
            System.out.println(
            "The test came to a chi squared value of: " + chiSquared + ", which is bigger than the upper alpha " +
            "\npercentile: " + upperAlphaPercentile + ", therefore we reject the null hypothesis, that the U_i " +
                    "\nare IID uniformly distributed U(0,1) random variables at the level of alpha "+ alpha +".");
        } else {
            reject = false;
            System.out.println(
                    "The test came to a chi squared value of: " + chiSquared + ", which is smaller than the upper alpha " +
                            "\npercentile: " + upperAlphaPercentile + ", therefore we accept the null hypothesis, that the U_i " +
                            "\nare IID uniformly distributed U(0,1) random variables at the level of alpha "+ alpha +".");
        }


        return !reject;
    }
}
