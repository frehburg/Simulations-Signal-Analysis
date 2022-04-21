package RandomNumberGenerators;

public class GeneralRandVariatesGenerator implements RandomVariatesGenerator{

    private final UnifRandGenerator unif;

    public GeneralRandVariatesGenerator(UnifRandGenerator unif) {
        this.unif = unif;
    }

    @Override
    public UnifRandGenerator getUniformGenerator() {
        return unif;
    }

    @Override
    /**
     * Min incluse, max exclusive
     */
    public int getInt(int min, int max) {
        return (int) getDouble((double) min, (double) max);
    }

    @Override
    /**
     * Min incluse, max exclusive
     */
    public double getDouble(double min, double max) {
        return (unif.getRandomNumber() * (max - min)) + min;
    }

    @Override
    public boolean getBoolean() {
        double rand = unif.getRandomNumber();
        return rand >= 0.5 ? true : false;
    }
}
