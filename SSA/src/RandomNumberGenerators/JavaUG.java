package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;
import java.util.Random;

public class JavaUG implements UnifRandGenerator{
    private final long seed;
    private Random r;
    private ArrayList<Double> u;
    private int curI;

    public JavaUG(long seed) {
        r = new Random(seed);
        this.seed = seed;
        u = new ArrayList<>();
        curI = 0;
    }

    public JavaUG() {
        Random q = new Random();
        this.seed = q.nextLong();
        r = new Random(seed);
        u = new ArrayList<>();
        curI = 0;
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
        double nextU = r.nextDouble();
        u.add(nextU);
        System.out.println("i: " + curI + " U_i: " + nextU);
        curI++;
        return nextU;
    }

    @Override
    /**
     * This RNG does not work with integers
     */
    public long getLastZ() {
        return -1;
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
