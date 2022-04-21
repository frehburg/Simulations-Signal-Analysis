package RandomNumberGenerators;

import java.util.ArrayList;

public class TauswortheUnifGenerator implements UnifRandGenerator{

    private final long seed;
    private final int q;
    private boolean[] c;
    private ArrayList<Byte> bits;

    /**
     * This is the general constructor of the Tausworthe Generator.
     * The activeBits array must be of length exactly q, where c[q-1] must be true.
     * In the formula b_i = (c_1*b_(i_1) + c_2*b_(i-2) + ... + c_q*b_(i-q))%2 the c[] represents the c vector
     * Therefore if c[i-1] = true then c_i = 1 and vice versa.
     *
     * It is to be noted, that the seed should be a number filling up q bits, but it does not need to
     * @param seed
     * @param q
     * @param c
     */
    public TauswortheUnifGenerator(long seed, int q, boolean[] c) {
        this.seed = seed;
        this.q = q;
        this.c = c;
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
        this.c = new boolean[q];
        for(int i = 0; i < q; i++) {
            c[i] = false;
        }
        c[r-1] = true;
        c[q-1] = true;
        init();
    }

    private void init() {
        bits = new ArrayList<>();

        //convert seed into bits and then put them in as the first q bits
        String seedBits = Long.toBinaryString(seed);
        while(seedBits.length() < q) {
            seedBits = "0"+seedBits;
        }
        System.out.println("Seed: " + seed + ", as bits: " + seedBits);


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
