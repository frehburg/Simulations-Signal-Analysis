package RandomNumberGenerators;

/**
 * Classes that implement this interface generate random numbers from theinterval [0,1)
 */
public interface UnifRandGenerator {
    long getSeed();

    /**
     * This method generates a random number from the U(0,1) distribution ( [0,1) interval)
     * @return
     */
    double getRandomNumber();

    /**
     * Returns the ith random number. If it hasn't been generated before, then it generates all of the missing random
     * numbers.
     * @param i
     * @return
     */
    double getRandomNumber(int i) throws Exception;
}
