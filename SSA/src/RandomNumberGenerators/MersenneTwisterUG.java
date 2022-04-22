package RandomNumberGenerators;

public class MersenneTwisterUG implements UnifRandGenerator{
    //TODO
    @Override
    public long getSeed() {
        return 0;
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

    @Override
    public long getLastZ() {
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
