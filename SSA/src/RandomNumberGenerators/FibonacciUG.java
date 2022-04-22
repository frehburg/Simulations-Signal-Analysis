package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;

/**
 * This generator tends to have a period in excess of m but is completely unacceptable
 * from a statistical standpoint
 */
public class FibonacciUG implements UnifRandGenerator{

    private final long seed;
    private final long m;
    private int curI;
    private ArrayList<Long> z;
    private ArrayList<Double> u;

    public FibonacciUG(long m, long seed) throws Exception {
        if(m <= 0 || seed < 0) {
            throw new Exception("Invalid parameters: M must be at least 1 and the seed must be at least 0");
        }
        this.seed = seed;
        this.m = m;
        curI = 0;
        z = new ArrayList<>();
        z.add(seed);
        u = new ArrayList<>();
        u.add((double)seed/(double) m);
        System.out.println("i: "+ curI + " Z_i: " + seed + " U_i: " + u.get(0));
    }

    @Override
    public long getSeed() {
        return seed;
    }

    /**
     * This method generates a random number from the U(0,1) distribution ( [0,1) interval)
     *
     * @return
     */
    @Override
    public double getRandomNumber() {
        curI++;
        long curIm1 = curI - 1;
        long zm1 = z.get((int) curIm1);
        long curIm2 = curI - 2;
        long zm2;
        if(curIm2 < 0) {
            zm2 = 0;
        } else {
            zm2 = z.get((int) curIm2);
        }
        long nextZ = (zm1 + zm2) % m;
        z.add(nextZ);
        double nextU = (double) nextZ / (double) m;
        u.add(nextU);
        System.out.println("i: "+ curI + " Z_i: " + nextZ + " U_i: " + nextU);
        return nextU;
    }

    @Override
    public long getLastZ() {
        return z.get(curI);
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
            System.out.println("Average value of the random numbers: "+ AverageUtils.avgD(u));
            return u.get(i);
        }
        throw new Exception("i is out of the range of generated random numbers");
    }
}
