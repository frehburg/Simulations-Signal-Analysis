package RandomNumberGenerators;

import Utils.AverageUtils;
import Utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class TauswortheUnifGenerator implements UnifRandGenerator{

    private static final boolean DEBUG = false;
    private final long seed;
    private final int q;
    private byte[] c;
    private ArrayList<Byte> bits;
    private ArrayList<Double> u;
    private int curI;
    private int curB;

    /**
     * Known to have statistical deficiencies.
     * This is the general constructor of the Tausworthe Generator.
     * The activeBits array must be of length exactly q, where c[q-1] must be true.
     * In the formula b_i = (c_1*b_(i_1) + c_2*b_(i-2) + ... + c_q*b_(i-q))%2 the c[] represents the c vector
     * Therefore if c[i-1] = true then c_i = 1 and vice versa.
     *
     *
     * It is to be noted, that the seed should be a number filling up q bits, but it does not need to
     * @param seed
     * @param q
     * @param c
     */
    public TauswortheUnifGenerator(long seed, int q, boolean[] c) {
        this.seed = seed;
        this.q = q;
        this.c = new byte[q];
        for(int i = 0; i < q; i++) {
            this.c[i] = (byte) (c[i] ? 1 : 0);
        }
        init();
    }

    /**
     * This is the specific constructor of the Tausworthe Generator.
     * It is determined by the formula b_i = (b_(i-r) + b_(i-q)) % 2
     * where 0<r<q. Therefore c = (0, 0, ..., (rth bit)1,...,(qth bit)1)
     * @param seed
     * @param r
     * @param q
     */
    public TauswortheUnifGenerator(long seed, int r, int q) {
        this.seed = seed;
        this.q = q;
        this.c = new byte[q];
        for(int i = 0; i < q; i++) {
            c[i] = 0;
        }
        c[r-1] = 1;
        c[q-1] = 1;
        init();
    }

    private void init() {
        curI = 0;
        curB = q - 1;
        u = new ArrayList<>();
        bits = new ArrayList<>();

        //convert seed into bits and then put them in as the first q bits
        String seedBits = Long.toBinaryString(seed);
        while(seedBits.length() < q) {
            seedBits = "0"+seedBits;
        }
        System.out.println("Seed: " + seed + ", as bits: " + seedBits);
        char[] seedBitsArr = seedBits.toCharArray();
        for(char c : seedBitsArr) {
            byte cur = Byte.parseByte(c+"");
            bits.add(cur);
        }
        System.out.println("First q bits: " + bits);
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
        //repeat this q times
        for(int k = 0; k < q; k++) {
            //0. get the last q bits
            List<Byte> lastQBitsList = bits.subList((curB - (q - 1)), curB + 1);
            Byte[] lastQBits = lastQBitsList.toArray(new Byte[0]);
            //1. generate a new bit
            byte nextBit = 0;
            for (int i = 0; i < q; i++) {
                nextBit += c[i] * lastQBits[i];
            }
            nextBit = (byte) (nextBit % 2);

            bits.add(nextBit);
            if(DEBUG)System.out.println("    " + nextBit);
            curB++;
        }
        //2. now convert the new last q bits into the new random number
        List<Byte> lastQBitsList = bits.subList((curB - (q - 1)), curB + 1);
        Byte[] lastQBits = lastQBitsList.toArray(new Byte[0]);
        long w = 0;
        for(int i = 0; i < q; i++) {
            w += lastQBits[i] * Math.pow(2,i);
        }
        double nextU = (double) w / Math.pow(2,q);
        u.add(nextU);
        if(DEBUG)System.out.println("i: " + curI + " u_i: " + nextU + " W_i: " + w + " 2^q = " + Math.pow(2,q));
        curI++;
        return nextU;
    }

    /**
     * Returns the ith random number. If it hasn't been generated before, then it generates all of the missing random
     * numbers.
     *
     * @param i
     * @return
     */
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
