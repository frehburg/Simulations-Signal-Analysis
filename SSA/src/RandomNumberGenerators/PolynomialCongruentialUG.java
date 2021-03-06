package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements polynomial congruential generators
 *
 * The formula is Z_i = (a_1*Z_(i-1)^(q-1) + a_2*Z_(i-2)^(q-2)+ ... + a_q*Z_(i-q) + c) % m
 *
 * Seems to be very weak, especially for higher degree polynomials. In the quadratic case,
 * no big improvements can be found after m = 2^9
 */
public class PolynomialCongruentialUG implements UnifRandGenerator{

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

    public static PolynomialCongruentialUG QUADRATIC;

    static {
        try {
            QUADRATIC = new PolynomialCongruentialUG((long) (Math.pow(2,9)),123,456,0,1,1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
     * The formula is Z_i = (a_1*Z_(i-1)^(q-1) + a_2*Z_(i-2)^(q-2)+ ... + a_q*Z_(i-q) + c) % m
     *
     * @param m
     * @param a
     * @param c
     * @param seed
     * @throws Exception
     */
    public PolynomialCongruentialUG(long m, long[] a, long c, long seed[]) throws Exception {
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

    /**
     * This is the constructor for the quadratic  congruential generator
     *
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
     * The formula is Z_i = (a_1*Z_(i-1)^(q-1) + a_2*Z_(i-2)^(q-2)+ ... + a_q*Z_(i-q) + c) % m
     *
     * @param m
     * @param a1
     * @param a2
     * @param c
     * @param seed1
     * @param seed2
     * @throws Exception
     */
    public PolynomialCongruentialUG(long m, long a1, long a2, long c, long seed1, long seed2) throws Exception {
        a = new long[]{a1, a2};
        seed = new long[]{seed1, seed2};
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
        this.c = c;
        if(c == 0) {
            System.out.println("Since c == 0, this is a multiplicative LCG");
        } else if (c > 0) {
            System.out.println("Since c == 0, this is a mixed LCG");
        }
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
     * The formula is Z_i = (a_1*Z_(i-1)^(q-1) + a_2*Z_(i-2)^(q-2)+ ... + a_q*Z_(i-q) + c) % m
     *
     * U_i = Z_i / m
     *
     * @return
     */
    @Override
    public double getRandomNumber() {
        //1. get last q Z_i
        List<Long> lastZList = z.subList((int)(curI - (q - 1)), (int)curI + 1);
        Long[] lastZ = lastZList.toArray(new Long[0]);
        long nextZ = 0;
        for(int i = 0; i < q; i++) {
            nextZ += a[i]*Math.pow(lastZ[i],(q - i - 1));
        }
        nextZ += c;
        nextZ = nextZ % m;
        z.add(nextZ);

        double nextU = (double) nextZ / (double) m;
        u.add(nextU);
        curI++;
        if(DEBUG)System.out.println("i: " + curI + " u_i: " + nextU);
        return nextU;
    }

    @Override
    public long getLastZ() {
        return z.get((int) curI);
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
