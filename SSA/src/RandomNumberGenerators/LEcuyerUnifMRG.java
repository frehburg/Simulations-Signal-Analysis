package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;

public class LEcuyerUnifMRG implements UnifRandGenerator{
    private static final boolean DEBUG = false;
    private final UnifRandGenerator[] mrgs;
    private final long[] m;
    private final long[][] a;
    private final long c;
    private final long[][] seed;
    private final ArrayList<Long> y;
    private final ArrayList<Double> u;
    private final long[] delta;
    private int curI;
    private long M;
    private int q;

    public static LEcuyerUnifMRG PRETTY_GOOD;

    static {
        try {
            PRETTY_GOOD = new LEcuyerUnifMRG((long) Math.pow(2,19),new long[]{(long) Math.pow(2,16), (long) Math.pow(2,19)}, new long[][]{{1,3},{69,420}},new long[]{1,1},0,new long[][]{{1, 123},{187,9}});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This
     * @param M
     * @param m
     * @param a
     * @param c
     * @param seed
     * @throws Exception
     */
    public LEcuyerUnifMRG(long M, long[] m, long[][] a, long[] delta, long c, long[][] seed) throws Exception {
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
        //initialize the MRGs
        mrgs = new MultipleRecuriveCongruentialUG[q];
        for(int i = 0; i < q; i++) {
            mrgs[i] = new MultipleRecuriveCongruentialUG(m[i],a[i],0,seed[i]);
        }
        y = new ArrayList<>();
        u = new ArrayList<>();
        curI = 0;
    }

    @Override
    public long getSeed() {
        return -1;
    }

    public long[][] getSeedArray() {
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
            mrgs[i].getRandomNumber();
            curZ[i] = mrgs[i].getLastZ();
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
