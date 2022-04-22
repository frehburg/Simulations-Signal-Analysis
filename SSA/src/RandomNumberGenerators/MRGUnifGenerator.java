package RandomNumberGenerators;

import java.util.ArrayList;
import java.util.List;

public class MRGUnifGenerator implements UnifRandGenerator{

    private boolean DEBUG = false;
    private final long m;
    private final long[] a;
    private final long c;
    private final long[] seed;
    private long curI;
    private ArrayList<Long> z;
    private ArrayList<Double> u;
    private boolean hasFullPeriod;
    private long periodLength;
    private int q;

    /**
     * m: the modulus
     * a: a vector of multipliers
     * c: the increment
     * Z_0: the seed
     * are all non-negative integers
     * Further:
     * 0 < m
     * for each a_i < m
     * c < m
     * Z_0 < m
     *
     * @param m
     * @param a
     * @param c
     * @param seed
     * @throws Exception
     */
    public MRGUnifGenerator(long m, long[] a, long c, long seed[]) throws Exception {
        if(!(m > 0)) {
            throw new Exception("m must be larger than 0.");
        }
        if(c < 0) {
            throw new Exception("AThe c parameter must be a non-negative integer.");
        }
        if(!(c < m)) {
            throw new Exception("The c parameter must be smaller than m.");
        }
        for(long aI : a) {
            if(aI < 0 || !(aI < m)) {
                throw new Exception("All a_i in the a vector must be bigger than zero and smaller than m: 0<a_i<m");
            }
        }
        for(long seedI : seed) {
            if(seedI < 0 || !(seedI<m)) {
                throw new Exception("All seed_i in the seed vector must be bigger than zero and smaller than m: 0<seed_i<m");
            }
        }
        this.m = m;
        this.a = a;
        this.c = c;
        if(c == 0) {
            System.out.println("Since c == 0, this is a multiplicative LCG");
        } else if (c > 0) {
            System.out.println("Since c == 0, this is a mixed LCG");
        }
        this.seed = seed;
        z = new ArrayList<>();
        u = new ArrayList<>();
        for(long seedI : seed) {
            z.add(seedI);
            u.add((double) seedI/ (double) m);
        }
        this.q = seed.length;
        curI = q - 1;
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
     *
     * The formula is Z_i = a_1*Z_(i-1) +
     *
     * @return
     */
    @Override
    public double getRandomNumber() {
        //1. get last q Z_i
        long[] lastZ = new long[q];
        List<Byte> lastQBitsList = bits.subList((curB - (q - 1)), curB + 1);
        return 0;
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
        return 0;
    }
}
