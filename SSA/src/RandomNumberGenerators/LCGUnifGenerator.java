package RandomNumberGenerators;

import Utils.AllDivisors;
import Utils.AverageUtils;
import Utils.GreatestCommonDivisor;
import Utils.IsPrime;

import java.util.ArrayList;

/**
 * CORE DIFFERENCE TO NORMAL LCG: U_i = Z_i/ (m-1) instead of /m
 * Because otherwise you can never get the number 1
 * This class implements Linear congruent generators
 *
 * WHICH ARE A SEQUENCE OF INTEGERS Z_1, Z_2, ..., defined by the recursive formula:
 * Z_i = (a* Z_(i-1) + c) % m
 * where
 * m: the modulus ( a good choice of m is 2^b, where b is the amount of bits in a word of the computer compiler, for most that is 2^31)
 * a: the multiplier
 * c: the increment
 * Z_0: the seed
 * are all non-negative integers
 * Further:
 * 0 < m
 * a < m
 * c < m
 * Z_0 < m
 *
 * This can generate Z_i in the interval [0, (m-1)]; 0 <= Z_i <= m-1
 *
 * To get numbers from U(0,1), simply do U_i = Z_i/m
 */
public class LCGUnifGenerator implements UnifRandGenerator{

    private final long m;
    private final long a;
    private final long c;
    private final long seed;
    private long curI;
    private ArrayList<Long> z;
    private ArrayList<Double> u;
    private boolean hasFullPeriod;
    private long periodLength;
    //---------Examples
    /**
     * Prime modulus multiplicative (c = 0) LCG
     * shown to have very undesirable statistical properties
     */
    public static LCGUnifGenerator RANDU;
    /**
     * Prime modulus multiplicative (c = 0) LCG with optimal m = 2^31 - 1
     * with a_1 = 7^5 found by Lewis, Goodman, and Miller, but not very optimal
     */
    public static LCGUnifGenerator PMMLCG_1;
    /**
     * Prime modulus multiplicative (c = 0) LCG with optimal m = 2^31 - 1
     * a_2 = 630,360,016 suggested by Payne, Rabung, and Bogyo
     * has better statistical performance than a_1
     * "may provide acceptable results for some applications, particularly if the required number
     * of random numbers is not too large"
     */
    public static LCGUnifGenerator PMMLCG_2;

    static {
        try {
            RANDU = new LCGUnifGenerator((long) Math.pow(2,31), (long) (Math.pow(2,16)+3),0,1);
            PMMLCG_1 = new LCGUnifGenerator((long) Math.pow(2,31) - 1, (long) Math.pow(7,5),0,1);
            PMMLCG_2 = new LCGUnifGenerator((long) Math.pow(2,31) - 1, 630360016,0,237);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * m: the modulus
     * a: the multiplier
     * c: the increment
     * Z_0: the seed
     * are all non-negative integers
     * Further:
     * 0 < m
     * a < m
     * c < m
     * Z_0 < m
     *
     * @param m
     * @param a
     * @param c
     * @param seed
     * @throws Exception
     */
    public LCGUnifGenerator(long m, long a, long c, long seed) throws Exception {
        if(!(m > 0)) {
            throw new Exception("m must be larger than 0.");
        }
        if(a < 0 || c < 0 || seed < 0) {
            throw new Exception("All parameters, but m, must be non-negative integers.");
        }
        if(!(a < m) || !(c < m) || !(seed < m)) {
            throw new Exception("All other parameters (a, c & the seed Z_0) must be smaller than m.");
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
        z.add(seed);
        u = new ArrayList<>();
        u.add((double)seed/(double)m);
        curI = 0;

        hasFullPeriod = hasFullPeriod();
        if(hasFullPeriod) {
            System.out.println("According to the Hull & Dobell Theorem of 1962, this LCG has full period.");
            periodLength = m;
            System.out.println("Therefore, this LCG has a period of length " + periodLength + ".");
        } else {
            System.out.println("According to the Hull & Dobell Theorem of 1962, this LCG does not have full period.");
            periodLength = -1;
        }

        System.out.println("i: " + curI + " Z_i: " + seed + " U_i: " + u.get(0));
    }

    @Override
    public long getSeed() {
        return seed;
    }

    /**
     * This method generates a random number from the U(0,1) disttribution
     *
     * @return
     */
    @Override
    public double getRandomNumber() {
        long nextZ = (a*z.get((int) curI) + c) % m;
        z.add(nextZ);
        double nextU = (double) nextZ / (double) m;
        u.add(nextU);
        curI++;
        System.out.println("i: " + curI + " Z_i: " + nextZ + " U_i: " + nextU);
        if(periodLength == -1 && z.get((int) curI) == seed) { // then we have completed a full period
            periodLength = curI;
            System.out.println("This LCG has a period of length " + periodLength + ".");
        }
        return nextU;
    }

    /**
     * Returns the ith random number
     * @param i
     * @return
     * @throws Exception
     */
    public double getRandomNumber(int i) throws Exception {
        if(i > 0) {
            if(i >= curI) {
                while(curI < i) {
                    getRandomNumber();
                }
            }
            System.out.println("This LCG has a period of length " + periodLength + ".");
            System.out.println("Average value of the random numbers: "+AverageUtils.avgD(u));
            return u.get(i);
        }
        throw new Exception("i is out of the range of generated random numbers");
    }

    /**
     * Theorem, proven by Hull and Dobell (1962)
     * THEOREM 7.1. The LCG defined in Eq. (7.1) has full period if and only if the following three conditions hold:
     * (a) The only positive integer that (exactly) divides both m and c is 1.
     * (b) If q is a prime number (divisible by only itself and 1) that divides m, then q divides a - 1.
     * (c) If 4 divides m, then 4 divides a - 1.
     * @return
     */
    public boolean hasFullPeriod() {
        // (a)
        //this is only possible if c > 0
        if(c == 0) {
            return false;
        }
        int gcdMC = GreatestCommonDivisor.gcdBySteinsAlgorithm((int) m, (int) c); // greatest common divisor of m and c
        if(gcdMC != 1) {
            return false;
        }

        // (b)
        // find all divisors of m
        ArrayList<Integer> divisorsM = AllDivisors.getDivisors((int) m);
        //find a divisor that is prime
        int primeDivisor = -1; // -1 means no prime divisor found
        for(int divisor : divisorsM) {
            if(IsPrime.checkForPrime(divisor)) {
                primeDivisor = divisor;
                break;
            }
        }
        //check if we found a prime divisor, if so it must also divide (a-1)
        if(primeDivisor != -1) {
            // g % h == 0 means that g is divisible by h, therefore if it isn't 0, it is not divisible by h
            if((a - 1) % primeDivisor != 0) {
                return false;
            }
        }

        // (c)
        if(m % 4 == 0) {
            if((a - 1) % 4 != 0) {
                return false;
            }
        }
        //all conditions fulfilled
        return true;
    }
}
