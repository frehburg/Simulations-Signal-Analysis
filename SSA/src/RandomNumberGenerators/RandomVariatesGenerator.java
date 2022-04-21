package RandomNumberGenerators;

public interface RandomVariatesGenerator {
    UnifRandGenerator getUniformGenerator();
    /**
     * Min incluse, max exclusive
     */
    int getInt(int min, int max);
    /**
     * Min incluse, max exclusive
     */
    double getDouble(double min, double max);
    boolean getBoolean();

}
