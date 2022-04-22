package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;

/**
 * This class implements L'Ecuyers idea of a composite generator with q different LCGs
 * each of them has a multiplier a_i, a modulus m_i, the increment c_i = 0 and a seed_i
 * Then each of the 1<=k<=q lcgs generates a sequence of integers {Z_k,i} which will be used in
 * The formula is Y_i = (delta_1*Z_1,i + delta_2*Z_2,i + ... + delta_q*Z_q,i + c) mod M
 * U_i = Y_i/M
 *
 * In short experiments only found that it works well with at least one good lcg
 */
public class LEcuyerUnifGeneratorLCGS implements UnifRandGenerator{
    private static final boolean DEBUG = false;
    private final UnifRandGenerator[] lcgs;
    private final long[] m;
    private final long[] a;
    private final long c;
    private final long[] seed;
    private final ArrayList<Long> y;
    private final ArrayList<Double> u;
    private final long[] delta;
    private int curI;
    private long M;
    private int q;

    /**
     * This
     * @param M
     * @param m
     * @param a
     * @param c
     * @param seed
     * @throws Exception
     */
    public LEcuyerUnifGeneratorLCGS(long M, long[] m, long[] a, long[] delta, long c, long[] seed) throws Exception {
        if(!(M > 0)) {
            throw new Exception("m must be larger than 0.");
        }
        if(!(m.length == a.length && m.length == seed.length && m.length == delta.length)) {
            throw new Exception("All parameter arrays must be of the same length q");
        }
        this.m = m;
        this.a = a;
        this.c = c;
        this.delta = delta;
        this.M = M;
        this.q = m.length;
        this.seed = seed;
        //initialize the LCGs
        lcgs = new LCGUnifGenerator[q];
        for(int i = 0; i < q; i++) {
            lcgs[i] = new LCGUnifGenerator(m[i],a[i],0,seed[i]);
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
     * @return
     */
    @Override
    public double getRandomNumber() {
        long[] curZ = new long[q];
        for(int i = 0; i < q; i++) {
            // the getRandomNumber() method returns the U_i which is [0,1), but we need the integer Z_i which is [0,m)
            //the formula for the U_i = Z_i/m --> Z_i = U_i*m
            curZ[i] = (long) (lcgs[i].getRandomNumber() * (double) m[i]);
        }
        double curY = 0;
        for(int i = 0; i < q; i++) {
            curY += delta[i]*curZ[i];
        }
        curY += c;
        curY = curY % M;
        y.add((long) curY);
        double curU = curY / (double) M;
        u.add(curU);
        curI++;
        if(DEBUG) System.out.println("i: " + curI + " U_i: " + curU);
        return curU;
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
        if(i > 0) {
            if(i >= curI) {
                while(curI <= i) {
                    getRandomNumber();
                }
            }
            System.out.println("Average value of the random numbers: "+ AverageUtils.avgD(u));
            return u.get(i);
        }
        throw new Exception("i is out of the range of generated random numbers");
    }
}
