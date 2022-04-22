package RandomNumberGenerators;

import Utils.AverageUtils;

import java.util.ArrayList;

/**
 * Intuitively the midsquare method seems to provide a good scrambling of one
 * number to obtain the next, and so we might think that such a haphazard rule would
 * provide a fairly good way of generating random numbers. In fact, it does not work
 * very well at all. One serious problem (among others) is that it has a strong tendency
 * to degenerate fairly rapidly to zero, where it will stay forever. (
 */
public class MidSquareUG implements UnifRandGenerator{

    private final int seed;
    private ArrayList<Integer> z;
    private ArrayList<Double> u;
    private ArrayList<Integer> zSquared;
    private int curI;

    /**
     * The seed MUST be a 4 digit positive integer
     * @param seed
     */
    public MidSquareUG(int seed) throws Exception {
        if(seed < 0) {
            throw new Exception("Seed cannot be negative");
        }
        if(seed == 0) {
            throw new Exception("Seed cannot be equal to  zero");
        }
        if(seed > 9999) {
            throw new Exception("Seed cannot have more than four digits");
        }
        this.seed = seed;
        z = new ArrayList<>();
        z.add(seed);
        u = new ArrayList<>();
        u.add(0.0);
        zSquared = new ArrayList<>();
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
        //1. square the current z
        int curZ = z.get(curI);
        int curZSquaredInt = (int) Math.pow(curZ,2);
        zSquared.add(curZSquaredInt);
        //2. If necessary, append zeros to the left to make it exactly eight digits
        String curZSquared = ""+ curZSquaredInt;
        while(curZSquared.length() < 8) {
            curZSquared = "0"+curZSquared;
        }
        //3. Take the middle four digits as the next four digit number
        String nextZString = curZSquared.substring(2,6);
        int nextZ = Integer.parseInt(nextZString);
        z.add(nextZ);
        double nextU = (double) nextZ / 10000.0;
        u.add(nextU);
        curI++;
        System.out.println("i: "+ (curI-1) + " Z_i: " + curZ + " U_i: " + u.get(curI - 1) + " Z_i^2: " + curZSquared);
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
