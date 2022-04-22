package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;

/**
 * This class just uses multiple uniform generators and uses Y_i which is a linear combination of their respective Z_i
 * to generate the next random number
 */
public class LinCombCompositeGenerator implements UnifRandGenerator {
    private static final boolean DEBUG = false;
    private long c;
    private long[] delta;
    private UnifRandGenerator[] unifs;
    private long[] seed;
    private ArrayList<Long> y;
    private ArrayList<Double> u;
    private int curI;
    private long m;
    private int q;

    /**
     * This
     *
     * @param M
     * @param c
     * @throws Exception
     */
    public LinCombCompositeGenerator(long M, UnifRandGenerator[] unifs, long[] delta, long c) throws Exception {
        if (!(M > 0)) {
            throw new Exception("m must be larger than 0.");
        }
        this.c = c;
        this.delta = delta;
        this.m = M;
        this.unifs = unifs;
        this.q = unifs.length;
        this.seed = new long[q];
        for(int i = 0; i < q; i++) {
            seed[i] = unifs[i].getSeed();
        }
        y = new ArrayList<>();
        u = new ArrayList<>();
        curI = 0;
    }

    @Override
    public long getSeed() {
        return -1;
    }

    public long[] getSeedArray() {
        return seed;
    }

    /**
     * This method generates a random number from the U(0,1) distribution ( [0,1) interval)
     * The formula is Y_i = (delta_1*Z_1,i + delta_2*Z_2,i + ... + delta_q*Z_q,i + c) mod M
     * U_i = Y_i/M
     *
     * @return
     */
    @Override
    public double getRandomNumber() {
        long[] curZ = new long[q];
        for (int i = 0; i < q; i++) {
            // the getRandomNumber() method returns the U_i which is [0,1), but we need the integer Z_i which is [0,m)
            //the formula for the U_i = Z_i/m --> Z_i = U_i*m
            unifs[i].getRandomNumber();
            curZ[i] = unifs[i].getLastZ();
        }
        double curY = 0;
        for (int i = 0; i < q; i++) {
            curY += delta[i] * curZ[i];
        }
        curY += c;
        curY = curY % m;
        y.add((long) curY);
        double curU = curY / (double) m;
        u.add(curU);
        curI++;
        if (DEBUG) System.out.println("i: " + curI + " U_i: " + curU);
        return curU;
    }

    @Override
    public long getLastZ() {
        return y.get(curI);
    }

    /**
     * Returns the ith random number. If it hasn't been generated before, then it generates all of the missing random
     * numbers.
     *
     * @param i
     * @return
     */
    @Override
    public double getRandomNumber(int i) throws Exception {
        if (i > 0) {
            if (i >= curI) {
                while (curI <= i) {
                    getRandomNumber();
                }
            }
            System.out.println("Average value of the random numbers: " + AverageUtils.avgD(u));
            return u.get(i);
        }
        throw new Exception("i is out of the range of generated random numbers");
    }
}

